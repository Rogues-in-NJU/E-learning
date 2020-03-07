package edu.nju.parser.util;

/**
 * Created by alfred on 2020/3/5.
 */
public class Doc2HtmlJacobUtil {
    /**
     * convert doc to html
     *
     * @param src    file to convert
     * @param target converted file
     */
    public static void docToHtml(String src, String target) {
        convert(src, target, 8);
    }

    private static boolean convert(String inputFile, String saveFile, int format) {
        // ActiveXComponent app = null;
        // try {
        //     app = new ActiveXComponent("Word.Application");
        //     app.setProperty("Visible", false);
        //     Dispatch docs = app.getProperty("Documents").toDispatch();
        //     Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true)
        //             .toDispatch();
        //     Dispatch.call(doc, "SaveAs2", saveFile, format);
        //     Dispatch.call(doc, "Close", false);
        // } catch (Exception e) {
        //     return false;
        // } finally {
        //     if (app != null) {
        //         app.invoke("Quit", 0);
        //     }
        // }
        return true;
    }

    public static void main(String[] args) {
        String src = "C:\\Users\\Administrator\\Desktop\\test\\初三自主招生教学案07：整式.doc";
        docToHtml(src, "C:\\Users\\Administrator\\Desktop\\test\\初三自主招生教学案07：整式.html");
    }
}

