package pl.bmstefanski.tools.storage.configuration;

import org.diorite.config.Config;

import java.util.Arrays;
import java.util.List;

public interface Messages extends Config {

    default String getBooleanOn() {
        return "&awlaczone";
    }

    default String getBooleanOff() {
        return "&cwylaczone";
    }

    default String getNoPermissions() {
        return "&cNie masz uprawnien do wykonania tej komendy! &7(%permission%)";
    }

    default String getOnlyPlayer() {
        return "&cTa komende moze wykonac tylko gracz!";
    }

    default String getUnknownCommand() {
        return "&cNie znaleziono takiej komendy! &7(%command%)";
    }

    default String getListFull() {
        return "&7Aktualnie na serwerze: &e%online%";
    }

    default String getListBasic() {
        return "&7Aktualnie na serwerze jest &e%online%&7/&e%max% &7graczy!";
    }

    default String getPlayerNotFound() {
        return "&cNie znaleziono gracza o nicku &7%player%&c!";
    }

    default String getGamemodeSuccess() {
        return "&7Zmieniono tryb gry na &e%gamemode%&7!";
    }

    default String getGamemodeSuccessOther() {
        return "&7Zmieniono tryb gry na &e%gamemode% &7dla &e%player%&7!";
    }

    default String getSuccessfullyReloaded() {
        return "&aPlugin zostal ponowanie zaladowany w &7%time% &asekund!";
    }

    default String getSuccessfullyDisabled() {
        return "&cPomyslnie wylaczono plugin!";
    }

    default String getHealed() {
        return "&7Zostales uleczony!";
    }

    default String getHealedOther() {
        return "&7Uleczono gracza o nicku &e%player%&7!";
    }

    default String getFed() {
        return "&7Zostales nakarmiony!";
    }

    default String getFedOther() {
        return "&7Nakarmiono gracza o nicku &e%player%&7!";
    }

    default String getFlySwitched() {
        return "&7Latanie zostalo %state%&7!";
    }

    default String getFlySwitchedOther() {
        return "&7Latanie zostalo %state% &7dla gracza &e%player%&7!";
    }

    default String getSetspawnSuccess() {
        return "&7Pomyslnie utworzono spawn na koordynatach: &e%x%&7, &e%y%&7, &e%z%&7, &e%world%&7!";
    }

    default String getSpawnFailed() {
        return "&cSpawn nie jest utworzony! Uzyj komendy &7/setspawn&c, aby utworzyc";
    }

    default String getTeleportCancelled() {
        return "&cTeleportacja zostala przerwana!";
    }

    default String getTeleportCurrentlyTeleporting() {
        return "&cJuz sie teleportujesz!";
    }

    default String getTeleportCounting() {
        return "&cZostaniesz teleportowany za &7%count% sekund&c!";
    }

    default String getGodSwitched() {
        return "&7God zostal %state%&7!";
    }

    default String getGodSwitchedOther() {
        return "&7God zostal %state% &7dla &e%player%&7!";
    }

    default String getTeleportSuccess() {
        return "&7Pomyslnie teleportowano!";
    }

    default String getBroadcastFormat() {
        return "&8[&3OGLOSZENIE&8] &7%message%";
    }

    default String getClear() {
        return "&7Wyczyszczono twoj ekwipunek!";
    }

    default String getClearOther() {
        return "&7Wyczyszczono ekwipunek gracza &e%player%&7!";
    }

    default List<String> getWhois() {
        return Arrays.asList(
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
    }

    default String getGamemodeFail() {
        return "&cMusisz wybrac tryb!";
    }

    default List<String> getBanFormat() {
        return Arrays.asList(
                "&8&m---------------------------------------------------&r",
                "  &cZostales zbanowany przez: &e%punisher% &cdo &e%until%",
                "  &7Odbanuj sie na: &ewww.dzienkiStary.pl",
                "  &7Powod: %reason%",
                "&8&m---------------------------------------------------"
        );
    }

    default String getAlreadyBanned() {
        return "&cGracz &7%player% &cjest juz zbanowany!";
    }

    default String getDefaultReason() {
        return "&cAdmin ma zawsze racje!";
    }

    default String getPermanentBan() {
        return "&cna zawsze";
    }

    default String getNotBanned() {
        return "&cGracz &7%player% &cnie jest zbanowany.";
    }

    default String getSuccessfullyUnbanned() {
        return "&cPomyslnie odbanowano gracza &7%player%";
    }

    default String getSuccessfullyBanned() {
        return "&cPomyslnie zbanowano gracza &7%player%";
    }
}
