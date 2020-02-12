package edu.nju.parser.core.plugin;

import edu.nju.parser.core.DocxConverterConfig;

public interface PostConvertPlugin extends Plugin {

    void postConvert(DocxConverterConfig config);

}
