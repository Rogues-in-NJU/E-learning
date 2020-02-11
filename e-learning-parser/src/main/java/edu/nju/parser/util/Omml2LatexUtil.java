package edu.nju.parser.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <pre>
 *
 * </pre>
 *
 */
public class Omml2LatexUtil {
    public static String xslConvert(String s, String xslpath, URIResolver uriResolver){
        TransformerFactory tFac = TransformerFactory.newInstance();
        if(uriResolver != null) {
            tFac.setURIResolver(uriResolver);
        }
        InputStream resourceAsStream = Omml2LatexUtil.class.getResourceAsStream(xslpath);
        StreamSource xslSource = new StreamSource(resourceAsStream);
        StringWriter writer = new StringWriter();
        try {
            Transformer t = tFac.newTransformer(xslSource);
            Source source = new StreamSource(new StringReader(s));
            Result result = new StreamResult(writer);
            t.transform(source, result);
        } catch (TransformerException e) {

        }
        return writer.getBuffer().toString();
    }

    /**
     * <p>Description: 将mathml转为latx </p>
     * @param mml
     * @return
     */
    public static String convertMML2Latex(String mml){
        mml = mml.substring(mml.indexOf("?>")+2, mml.length()); //去掉xml的头节点
        URIResolver r = new URIResolver(){  //设置xls依赖文件的路径
            public Source resolve(String href, String base) throws TransformerException {
                InputStream inputStream = Omml2LatexUtil.class.getResourceAsStream("/conventer/" + href);
                return new StreamSource(inputStream);
            }
        };
        String latex = xslConvert(mml, "/conventer/mmltex.xsl", r);
        if(latex != null && latex.length() > 1){
            latex = latex.substring(1, latex.length() - 1);
        }
        return latex;
    }

    /**
     * <p>Description: office mathml转为mml </p>
     * @param xml
     * @return
     */
    public static String convertOMML2MML(String xml){
        String result = xslConvert(xml, "/conventer/OMML2MML.xsl", null);
        return result;
    }
}
