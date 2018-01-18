package pl.bmstefanski.tools.basic.manager;

import pl.bmstefanski.tools.api.basic.Ban;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanManager {

    private static final List<Ban> BANS = new ArrayList<>();

    public static Ban getBan(UUID punished) {
        for (Ban ban : BANS) {
            if (ban.getPunished().equals(punished)) return ban;
        }

        return null;
    }

    public static List<Ban> getBans() {
        return BANS;
    }
}
