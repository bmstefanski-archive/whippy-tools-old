package pl.bmstefanski.tools.util;

import pl.bmstefanski.tools.impl.configuration.Messages;

public class BooleanUtils {

    public static String parse(boolean bool) {
        return bool ? Messages.BOOLEAN_ON : Messages.BOOLEAN_OFF;
    }

}
