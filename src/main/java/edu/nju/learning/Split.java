package edu.nju.learning;

import org.docx4j.dml.picture.Pic;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.math.CTOMath;
import org.docx4j.math.CTOMathPara;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.relationships.Relationships;
import org.docx4j.vml.CTImageData;
import org.docx4j.vml.CTShape;
import org.docx4j.wml.*;
import org.w3c.dom.Node;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static edu.nju.learning.Omml2LatexUtil.convertMML2Latex;
import static edu.nju.learning.Omml2LatexUtil.convertOMML2MML;


public class Split {
    private static String inputFilePath = "demo.docx";
    private static String outPath = "target/";
    private static int time = 0;
    private static String title;
    private static List<String> topic = new ArrayList();


    public static void main(String[] args) throws Exception {
        List<Line> res = null;
        try {
            res = parserDocx(inputFilePath);
            for (Line l : res) {
                System.out.println(l.getContent());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Line> parserDocx(String inputfilepath) throws Exception {
        List<Line> lss = null;
        File file = new File(inputFilePath);
        WordprocessingMLPackage wordprocessingMLPackage = null;
        try {
            wordprocessingMLPackage = WordprocessingMLPackage.load(new FileInputStream(file));   //找不到
        } catch (Exception e) {
            if (e instanceof Docx4JException) {
                System.out.println("目标文件类型不是.docx文件");
                throw e;
            }
            if (e instanceof FileNotFoundException) {
                System.out.println("目标文件不存在");
                throw e;
            }
        }
        MainDocumentPart mainDocumentPart = wordprocessingMLPackage.getMainDocumentPart();
        Binder<Node> binder = mainDocumentPart.getBinder();
        List<Object> bodyChildren = mainDocumentPart.getContent();
        lss = walkJAXBElements(inputfilepath, bodyChildren, binder);
        return lss;
    }

    static ArrayList<Line> walkJAXBElements(String inputpath, List<Object> bodyChildren, Binder<Node> binder) throws Exception {
        ArrayList<Line> lss = new ArrayList<Line>();
        for (Object o : bodyChildren) {
            int aaa = 1;
            aaa++;
            if (o instanceof JAXBElement) {
                System.out.println("JAXBElement:" + o.getClass().getName());
                if (((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.wml.Tbl")) {
                    Tbl table = (Tbl) ((JAXBElement) o).getValue();
                    List<Object> content = table.getContent();
                    for (Object a : content) {
                        Tr Tr = (org.docx4j.wml.Tr) a;
                        List<Object> content1 = Tr.getContent();
                        System.out.println();
                        for (Object c : content1) {
                            JAXBElement o1 = (JAXBElement) c;
                            Tc value = (Tc) ((JAXBElement) o1).getValue();
                            List<Object> content2 = value.getContent();
                            System.out.print(content2.get(0) + "			");
                        }
                    }
                }
                Object o1 = ((JAXBElement) o).getValue();
            } else if (o instanceof P) {
                System.out.println("Paragraph object:------------------------------------ ");
                String paragraph = walkList(((P) o).getContent(), binder);
                if (time == 0) {
                    title = paragraph;
                    time++;
                }
                System.out.println("paragraph:" + paragraph);
                System.out.println("paragraph is content?" + ExerciseUtil.isContent(paragraph));
                Line l = new Line();
                l.content = paragraph;
                lss.add(l);
            }
        }
        return lss;
    }

    static String walkList(List children, Binder<Node> binder) throws Exception {
        String line = "";
        StringBuffer sb = new StringBuffer("");
        for (Object o : children) {
            if (o instanceof JAXBElement) {
                if (((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.wml.Text")) {
                    Text t = (Text) ((JAXBElement) o).getValue();
                    line = line + t.getValue();
                } else if (((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.wml.Drawing") || ((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.wml.CTObject")) {  //如果是图
                    boolean saveImages = true;
                    WordprocessingMLPackage wordMLPackage =
                            WordprocessingMLPackage.load(new File(inputFilePath));
                    MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
                    RelationshipsPart relsPart = documentPart.getRelationshipsPart();
                    Relationships rels = relsPart.getRelationships();
                    List<Relationship> relsList = rels.getRelationship();
                    String id = "";
                    JAXBElement o1 = (JAXBElement) o;
                    if (o1.getDeclaredType().getName().equals("org.docx4j.wml.CTObject")) {
                        CTObject value = (CTObject) o1.getValue();
                        List<Object> anyAndAny = value.getAnyAndAny();
                        for (Object o2 : anyAndAny) {
                            JAXBElement o3 = (JAXBElement) o2;
                            if (o3.getDeclaredType().getName().equals("org.docx4j.vml.CTShape")) {
                                CTShape value2 = (CTShape) o3.getValue();
                                List<JAXBElement<?>> handles = value2.getPathOrFormulasOrHandles();
                                for (JAXBElement jm : handles) {
                                    if (jm.getDeclaredType().getName().equals("org.docx4j.vml.CTImageData")) {
                                        CTImageData c = (CTImageData) jm.getValue();
                                        id = c.getId();
                                    }
                                }
                            }
                        }
                    }

                    if (o1.getDeclaredType().getName().equals("org.docx4j.wml.Drawing")) {
                        Drawing drawing = (Drawing) (o1.getValue());
                        List<Object> anchorOrInline = drawing.getAnchorOrInline();
                        for (Object jm : anchorOrInline) {
                            if (jm instanceof Inline) {
                                List<Object> any = ((Inline) jm).getGraphic().getGraphicData().getAny();
                                for (Object ob : any) {
                                    if (ob instanceof JAXBElement) {
                                        JAXBElement ob1 = (JAXBElement) ob;
                                        if (ob1.getDeclaredType().getName().equals("org.docx4j.dml.picture.Pic")) {
                                            Object value = ob1.getValue();
                                            Pic pic = (Pic) (value);
                                            id = pic.getBlipFill().getBlip().getEmbed();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < relsList.size(); i++) {
                        Relationship r = relsList.get(i);
                        /*System.out.println(r.getTargetMode());*/
                        /*http://schemas.openxmlformats.org/officeDocument/2006/relationships/image*/
                        if (((r.getType().equals(Namespaces.IMAGE))) && (r.getTargetMode() == null
                                || r.getTargetMode().equalsIgnoreCase("internal"))) {
                            /*System.out.println("target: " + target);*/
                            if (id.equals(r.getId())) {
                                String target = r.getTarget();
                                File f = new File(outPath + target);
                                if (f.exists()) {
                                    /*System.out.println("Overwriting existing object: " + f.getPath() );*/
                                } else if (!f.getParentFile().exists()) {
                                    System.out.println("creating " + f.getParentFile().getAbsolutePath());
                                    f.getParentFile().mkdirs();
                                }
                                Part p = relsPart.getPart(r);
                                FileOutputStream fos = new FileOutputStream(f);
                                BinaryPart p1 = (BinaryPart) p;
                                p1.writeDataToOutputStream(fos);
                                fos.close();
                                if (target.indexOf(".wmf") > 0) {
                                    //String convert = ImageConvert.convert(outPath + target);
                                    f.delete();
                                    // line = line + "<p><img src=\"" +convert + "\" alt=\"image_gs\" />";
                                    break;
                                } else {
                                    line = line + "<p><img src=\"" + f.getAbsolutePath() + "\" alt=\"image_gs\" />";
                                    break;
                                }
                            }
                        }
                    }
                    //class org.docx4j.math.CTOMathPara
                }else if (((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.math.CTOMath")){
                    CTOMath ctoMath = (CTOMath) ((JAXBElement) o).getValue();
                    List<Object> egoMathElements = ctoMath.getEGOMathElements();
                    for (Object object:egoMathElements) {
                        if (object instanceof JAXBElement){
                            Node xmlNode = binder.getXMLNode(object);
                            String omml = NodeUtil.NodetoString(xmlNode);
                            String mml = convertOMML2MML(omml);
                            String latex = convertMML2Latex(mml);
                            line = line + latex;
                        }
                    }
                }else if (((JAXBElement) o).getDeclaredType().getName().equals("org.docx4j.math.CTOMathPara")){
                    CTOMathPara ctoMathPara = (CTOMathPara) ((JAXBElement) o).getValue();
                    List<CTOMath> oMaths = ctoMathPara.getOMath();
                    for (CTOMath ctom:oMaths) {
                        List<Object> egoMathElements = ctom.getEGOMathElements();
                        for (Object object:egoMathElements) {
                            if (object instanceof JAXBElement){
                                Node xmlNode = binder.getXMLNode(object);
                                String omml = NodeUtil.NodetoString(xmlNode);
                                String mml = convertOMML2MML(omml);
                                String latex = convertMML2Latex(mml);
                                line = line + latex;
                            }
                        }
                    }
                }
            } else if (o instanceof Node) {
                System.out.println(" IGNORED " + ((Node) o).getNodeName());
            } else if (o instanceof R) {
                RPr rPr = ((R) o).getRPr();
                //FONT SIZE Attribute
            /*	HpsMeasure sz = rPr.getSz();
				BigInteger size= BigInteger.valueOf(14);
				if(sz!=null) {
					size = sz.getVal();
				}*/
                U u = null;
                if (rPr != null) {
                    u = rPr.getU();
                }
                R run = (R) o;        //具体段落内容
                String tmpStr = walkList(run.getContent(), binder);        //段落文字 查询runcontent 的 value值
                if (u != null) {
                    for (int i = 0; i < tmpStr.length(); i++) {
                        line = line + "_" + tmpStr.charAt(i);
                    }
                } else {
                    line = line + tmpStr;
                }
            } else {
                System.out.println(" IGNORED " + o.getClass().getName());
            }
        }
        return line;
    }

    private static class Line {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}


