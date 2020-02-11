/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package edu.nju.parser.util;

import net.arnx.wmf2svg.gdi.svg.SvgGdi;
import net.arnx.wmf2svg.gdi.wmf.WmfParser;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *
 * </pre>
 *
 */
public class ImageConvert {


    public static byte[] convert(byte[] bytes) {
        try {
            return svgToJpg(wmfToSvg(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将svg转化为JPG
     *
     * @param bytes
     */
    public static byte[] svgToJpg(byte[] bytes) {
        ByteArrayInputStream svgInputStream = null;
        ByteArrayOutputStream jpg = null;
        try {
            JPEGTranscoder it = new JPEGTranscoder();
            it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1f));
            it.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(100));
            jpg = new ByteArrayOutputStream();
            svgInputStream = new ByteArrayInputStream(bytes);
            it.transcode( new TranscoderInput(svgInputStream), new TranscoderOutput(jpg));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (svgInputStream != null) {
                    svgInputStream.close();
                }
                if (jpg != null) {
                    jpg.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jpg.toByteArray();
    }

    /**
     * 将wmf转换为svg
     * bytes
     */
    public static byte[] wmfToSvg(byte[] bytes) {
        boolean compatible = false;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new ByteArrayInputStream(bytes);
            WmfParser parser = new WmfParser();
            final SvgGdi gdi = new SvgGdi(compatible);
            parser.parse(in, gdi);
            out = new ByteArrayOutputStream();
            return output(gdi.getDocument(), out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private static byte[] output(Document doc, ByteArrayOutputStream out) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
                "-//W3C//DTD SVG 1.0//EN");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
        transformer.transform(new DOMSource(doc), new StreamResult(out));
        return out.toByteArray();
    }
}
