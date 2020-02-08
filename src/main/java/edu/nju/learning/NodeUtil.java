package edu.nju.learning;

import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author long
 * @version $Id: TestUtil.java, v 1.0 2017年11月30日 17:58. long Exp $
 */
public class NodeUtil {

    /**
     * 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。
     *
     * @param node
     *            DOM Node 对象。
     * @return a XML String from node
     */
    public static String NodetoString(Node node) {
        Transformer transformer = null;
        String result = null;
        if (node == null) {
            throw new IllegalArgumentException();
        }
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (transformer != null) {
            try {
                StringWriter sw = new StringWriter();
                transformer
                        .transform(new DOMSource(node), new StreamResult(sw));
                return sw.toString();
            } catch (TransformerException te) {
                throw new RuntimeException(te.getMessage());
            }
        }
        return result;
    }
}
