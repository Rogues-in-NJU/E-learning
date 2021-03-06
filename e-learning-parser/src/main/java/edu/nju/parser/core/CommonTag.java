package edu.nju.parser.core;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CommonTag implements Tag {

    private List<List<String>> originCommonTags;

    public CommonTag() {
        originCommonTags = new LinkedList<>();
        InputStream mathInputStream = CommonTag.class.getResourceAsStream("/common.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(mathInputStream);
        try {
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                if (line.contains(",")) {
                    originCommonTags.add(Arrays.stream(line.split(",")).collect(Collectors.toList()));
                } else {
                    originCommonTags.add(Collections.singletonList(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getTags(String content) {
        Set<String> tags = new HashSet<>();
        for (List<String> tl: originCommonTags) {
            boolean existed = false;
            for (String s: tl) {
                if (content.contains(s)) {
                    existed = true;
                    break;
                }
            }
            if (existed) {
                tags.add(tl.get(0));
            }
        }
        return tags;
    }

}
