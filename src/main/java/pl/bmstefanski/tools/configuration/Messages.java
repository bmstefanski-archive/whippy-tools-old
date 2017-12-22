package pl.bmstefanski.tools.configuration;

import java.util.Arrays;
import java.util.List;

public class Messages {

    public static String BOOLEAN_ON = "&awlaczone";
    public static String BOOLEAN_OFF = "&cwylaczone";

    public static String NO_PERMISSIONS = "&cNie masz uprawnien do wykonania tej komendy! &7(%permission%)";
    public static String ONLY_PLAYER = "&cTa komende moze wykonac tylko gracz!";
    public static String UNKNOWN_COMMAND = "&cNie znaleziono takiej komendy! &7(%command%)";
    public static String LIST_FULL = "&7Aktualnie na serwerze: &e%online%";
    public static String LIST_BASIC = "&7Aktualnie na serwerze jest &e%online%&7/&e%max% &7graczy!";
    public static String PLAYER_NOT_FOUND = "&cNie znaleziono gracza o nicku &7%player%&c!";
    public static String GAMEMODE_SUCCESS = "&7Zmieniono tryb gry na &e%gamemode%&7!";
    public static String GAMEMODE_SUCCESS_OTHER = "&7Zmieniono tryb gry na &e%gamemode% &7dla &e%player%&7!";
    public static String SUCCESSFULLY_RELOADED = "&aPlugin zostal ponowanie zaladowany w &7%time% &asekund!";
    public static String SUCCESSFULLY_DISABLED = "&cPomyslnie wylaczono plugin!";
    public static String HEALED = "&7Zostales uleczony!";
    public static String HEALED_OTHER = "&7Uleczono gracza o nicku &e%player%&7!";
    public static String FED = "&7Zostales nakarmiony!";
    public static String FED_OTHER = "&7Nakarmiono gracza o nicku &e%player%&7!";
    public static String FLY_SWITCHED = "&7Latanie zostalo %state%&7!";
    public static String FLY_SWITCHED_OTHER = "&7Latanie zostalo %state% &7dla gracza &e%player%&7!";
    public static String SETSPAWN_SUCCESS = "&7Pomyslnie utworzono spawn na koordynatach: &e%x%&7, &e%y%&7, &e%z%&7, &e%world%&7!";
    public static String SPAWN_FAILED =  "&cSpawn nie jest utworzony! Uzyj komendy &7/setspawn&c, aby utworzyc";
    public static String TELEPORT_CANCELLED = "&cTeleportacja zostala przerwana!";
    public static String TELEPORT_CURRENTLY_TELEPORTING = "&cJuz sie teleportujesz!";
    public static String TELEPORT_COUNTING = "&cZostaniesz teleportowany za &7%count% sekund&c!";
    public static String GOD_SWITCHED = "&7God zostal %state%&7!";
    public static String GOD_SWITCHED_OTHER = "&7God zostal %state% &7dla &e%player%&7!";
    public static String TELEPORT_SUCCESS = "&7Pomyslnie teleportowano!";
    public static String BROADCAST_FORMAT = "&8[&3OGLOSZENIE&8] &7%message%";
    public static String CLEAR = "&7Wyczyszczono twoj ekwipunek!";
    public static String CLEAR_OTHER = "&7Wyczyszczono ekwipunek gracza &e%player%&7!";
    public static List<String> WHOIS = Arrays.asList(
            "&8&m-----------------------------------------&r",
            "  &7Nickname: &e%nickname%",
            "  &7UUID: &e%uuid%",
            "  &7IP: &e%ip%",
            "  &7Zarejestrowany: &e%registered%",
            "  &7Ostatnio widziany: &e%last%",
            "  &7Lokacja: &e%location%",
            "  &7HP: &e%hp%",
            "  &7Glod: &e%hunger%",
            "  &7Tryb gry: &e%gamemode%",
            "  &7God: %god%",
            "  &7Fly: %fly%",
            "&8&m-----------------------------------------");
    public static String GAMEMODE_FAIL = "&cMusisz wybrac tryb!";
    public static List<String> BAN_FORMAT = Arrays.asList(
            "&8&m---------------------------------------------------&r",
            "  &cZostales zbanowany przez: &e%punisher% &cdo &e%until%",
            "  &7Odbanuj sie na: &ewww.dzienkiStary.pl",
            "  &7Powod: %reason%",
            "&8&m---------------------------------------------------"
    );
    public static String ALREADY_BANNED = "&cGracz &7%player% &cjest juz zbanowany!";
    public static String DEFAULT_REASON = "&cAdmin ma zawsze racje!";
    public static String PERMANENT_BAN = "&cna zawsze";
    public static String NOT_BANNED = "&cGracz &7%player% &cnie jest zbanowany.";
    public static String SUCCESSFULLY_UNBANNED = "&cPomyslnie odbanowano gracza &7%player%";
    public static String SUCCESSFULLY_BANNED = "&cPomyslnie zbanowano gracza &7%player%";
}
