package edu.nju.parser.util;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.vml.CTImageData;
import org.docx4j.vml.CTShape;
import org.docx4j.wml.CTObject;
import org.docx4j.wml.P;
import org.docx4j.wml.Pict;
import org.docx4j.wml.R;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public class XWPFUtils {

    //替换所有w:object
    public static List<String> convertImages(String path) throws Docx4JException, JAXBException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new File(path));
        MainDocumentPart mainDocument = template.getMainDocumentPart();
        List<Object> bodyNodes = mainDocument.getJAXBNodesViaXPath("//w:object", false);

        for (Object o : bodyNodes) {
            Object o2 = XmlUtils.unwrap(o);
            if (o2 instanceof CTObject) {
                CTObject c = (CTObject) o2;
                for (Object o3 : c.getAnyAndAny()) {
                    Object o4 = XmlUtils.unwrap(o3);
                    if (o4 instanceof CTShape) {
                        CTShape s = (CTShape) o4;
                        List<JAXBElement<?>> js = s.getEGShapeElements();
                        for (JAXBElement<?> j : js) {
                            Object o5 = j.getValue();
                            if (o5 instanceof CTImageData) {
                                CTImageData imageData = (CTImageData) o5;
                                Relationship rel = template.getMainDocumentPart().getRelationshipsPart(false)
                                        .getRelationshipByID(imageData.getId());
                                org.docx4j.wml.Pict p = createImageInline(rel, s.getStyle(), s.getTitle());
                                Object po = c.getParent();
                                if (po instanceof R) {
                                    R r = (R) po;
                                    int i = r.getContent().indexOf(o);
                                    r.getContent().add(i, p);
                                    r.getContent().remove(o);
                                } else if (po instanceof P) {
                                    P pp = (P) po;
                                    int i = pp.getContent().indexOf(o);
                                    pp.getContent().add(i, p);
                                    pp.getContent().remove(o);
                                }
                            }
                        }
                    }
                }
            }

        }
        template.save(new File(path));
        return null;
    }
    //
    // public static org.docx4j.wml.P newImage(Relationship rel, String style, String title) throws Exception {
    //
    //     Pict pict = createImageInline( rel, style, title);
    //
    //     // Now add the inline in w:p/w:r/w:drawing
    //     org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
    //     org.docx4j.wml.P  p = factory.createP();
    //     org.docx4j.wml.R  run = factory.createR();
    //     p.getContent().add(run);
    //     run.getContent().add(pict);
    //
    //     return p;
    //
    // }

    public static org.docx4j.wml.Pict createImageInline(Relationship rel, String style, String title) throws JAXBException {
        String ml =
                "<w:pict " + namespaces + ">"
                        + "<v:shape id=\"_x0000_i1025\" type=\"#_x0000_t75\" style=\"" + style + "\">"
                        + "  <v:imagedata r:id=\"" + rel.getId() + "\" o:title=\"" + title + "\"/>"
                        + "</v:shape>"
                        + "</w:pict>";

        Pict pict = (Pict) org.docx4j.XmlUtils.unmarshalString(ml);
        return pict;
    }

    final static String namespaces = " xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
            + "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\"";

}
