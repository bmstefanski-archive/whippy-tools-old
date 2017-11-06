package pl.bmstefanski.tools.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;

public class GamemodeUtils {

    private static GameMode getGameMode(String string) {
        for (GameMode mode : GameMode.values()) {
            if (mode.name().toLowerCase().contains(string.toLowerCase())) {
                return mode;
            }
        }
        return null;
    }

    @Deprecated
    public static GameMode parseGameMode(String string) {
        if (StringUtils.isNumeric(string)) {
            return GameMode.getByValue(Integer.valueOf(string));
        } else return getGameMode(string);
    }

}
