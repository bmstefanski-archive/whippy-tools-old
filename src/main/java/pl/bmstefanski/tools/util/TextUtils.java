package pl.bmstefanski.tools.util;

import java.util.List;

public class TextUtils {

    private final static StringBuilder stringBuilder = new StringBuilder();

    public static String listToString(List<String> list) {
        stringBuilder.setLength(0);
        String result = null;

        for (String string : list) {
            stringBuilder.append(string + "\n");
        }

        return MessageUtils.fixColor(stringBuilder.toString());
    }
}
