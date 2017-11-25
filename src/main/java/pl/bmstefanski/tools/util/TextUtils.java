package pl.bmstefanski.tools.util;

import java.util.List;

public class TextUtils {

    private final static StringBuilder STRING_BUILDER = new StringBuilder();

    public static String listToString(List<String> list) {
        STRING_BUILDER.setLength(0);
        String result = null;

        for (String string : list) {
            STRING_BUILDER.append(string + "\n");
        }

        return MessageUtils.fixColor(STRING_BUILDER.toString());
    }
}
