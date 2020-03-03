package edu.nju.parser.util;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.freehep.graphicsbase.util.export.ExportFileType;
import org.freehep.graphicsio.ImageConstants;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFPanel;
import org.freehep.graphicsio.emf.EMFRenderer;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class ImgUtil {

    public static void wmf2Png(String path) {
        WmfUtil.convert(path);
    }

    public static void emf2Png(String path) {
        String svgFile = null;
        String emfFile = path;
        String pngFile = replace(path, "emf", "png");
        try {
            List<?> exportFileTypes = ExportFileType.getExportFileTypes(ImageConstants.SVG);
            if (exportFileTypes == null || exportFileTypes.size() == 0) {
                System.out.println("svg library is not available. check your classpath!");
                return;
            }

            ExportFileType exportFileType = (ExportFileType) exportFileTypes.get(0);

            // read the EMF file
            EMFRenderer emfRenderer = new EMFRenderer(
                    new EMFInputStream(
                            new FileInputStream(emfFile)));

            // create the destFileName,
            // replace or add the extension to the destFileName
            if (svgFile == null || svgFile.length() == 0) {
                // index of the beginning of the extension
                int lastPointIndex = emfFile.lastIndexOf(".");

                // to be sure that the point separates an extension
                // and is not part of a directory name
                int lastSeparator1Index = emfFile.lastIndexOf("/");
                int lastSeparator2Index = emfFile.lastIndexOf("\\");

                if (lastSeparator1Index > lastPointIndex ||
                        lastSeparator2Index > lastPointIndex) {
                    svgFile = emfFile + ".";
                } else if (lastPointIndex > -1) {
                    svgFile = emfFile.substring(
                            0, lastPointIndex + 1);
                }

                // add the extension
                svgFile += "svg";
            }

            Properties p = new Properties();

            EMFPanel emfPanel = new EMFPanel();
            emfPanel.setRenderer(emfRenderer);

            exportFileType.exportToFile(
                    new File(svgFile),
                    emfPanel,
                    emfPanel,
                    p,
                    "Freehep EMF converter");

            svgToPng(svgFile, pngFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String svgToPng(String src, String dest) {
        FileOutputStream jpgOut = null;
        FileInputStream svgStream = null;
        ByteArrayOutputStream svgOut = null;
        ByteArrayInputStream svgInputStream = null;
        ByteArrayOutputStream jpg = null;
        try {
            // 获取到svg文件
            File svg = new File(src);
            svgStream = new FileInputStream(svg);
            svgOut = new ByteArrayOutputStream();
            // 获取到svg的stream
            int noOfByteRead = 0;
            while ((noOfByteRead = svgStream.read()) != -1) {
                svgOut.write(noOfByteRead);
            }
            PNGTranscoder it = new PNGTranscoder();
            // it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(0.9f));
            // it.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(100));
            jpg = new ByteArrayOutputStream();
            svgInputStream = new ByteArrayInputStream(svgOut.toByteArray());
            it.transcode(new TranscoderInput(svgInputStream),
                    new TranscoderOutput(jpg));
            jpgOut = new FileOutputStream(dest);
            jpgOut.write(jpg.toByteArray());
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
                if (svgStream != null) {
                    svgStream.close();
                }
                if (svgOut != null) {
                    svgOut.close();
                }
                if (jpgOut != null) {
                    jpgOut.flush();
                    jpgOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dest;
    }

    public static String replace(String original, String find, String replace) {
        if (original == null || find == null || replace == null) {
            return original;
        }
        int findLen = find.length();
        int originalLen = original.length();
        if (originalLen == 0 || findLen == 0) {
            return original;
        }
        StringBuffer sb = new StringBuffer();
        int begin = 0; //下次检索开始的位置
        int i = original.indexOf(find); //找到的子串位置
        while (i != -1) {
            sb.append(original.substring(begin, i));
            sb.append(replace);
            begin = i + findLen;
            i = original.indexOf(find, begin);
        }
        if (begin < originalLen) {
            sb.append(original.substring(begin));
        }
        return sb.toString();
    }

}
