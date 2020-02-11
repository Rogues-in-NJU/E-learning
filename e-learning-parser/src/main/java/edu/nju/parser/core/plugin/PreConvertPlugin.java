package edu.nju.parser.core.plugin;

import edu.nju.parser.core.DocxConverterConfig;

public interface PreConvertPlugin extends Plugin {

    void preConvert(DocxConverterConfig config);

}
