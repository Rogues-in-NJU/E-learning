package edu.nju.parser.util;


/**
 * Created by alfred on 2020/3/5.
 */
public class Doc2HtmlSpireUtil {
    public static void main(String[] args) {
        //TODO test doc2image with spire
        //create a Document object
        Document doc = new Document();
        //load a Word file
        doc.loadFromFile("C:\\Users\\Administrator\\Desktop\\sample.docx");
        //save the first page to a BufferedImage
        BufferedImage image = doc.saveToImages(0, ImageType.Bitmap);
        //write the image data to a .png file
        File file = new File("output/ToPNG.png");
        ImageIO.write(image, "PNG", file);
    }

}

