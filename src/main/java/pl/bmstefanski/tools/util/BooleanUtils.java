package pl.bmstefanski.tools.util;

import pl.bmstefanski.tools.configuration.Messages;

public class BooleanUtils {

    public static String parse(boolean bool) {
        return bool ? Messages.BOOLEAN_ON : Messages.BOOLEAN_OFF;
    }

}
