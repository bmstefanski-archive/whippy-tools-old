package pl.bmstefanski.tools.util;

import pl.bmstefanski.tools.Tools;

public class BooleanUtils {

    public static String parse(boolean bool) {
        return bool ? Tools.getInstance().getMessages().getBooleanOn() : Tools.getInstance().getMessages().getBooleanOff();
    }

}
