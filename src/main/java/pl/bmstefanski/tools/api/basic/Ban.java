package pl.bmstefanski.tools.api.basic;

import java.util.UUID;

public interface Ban {

    UUID getPunished();

    UUID getPunisher();

    String getReason();

    long getTime();

    void setReason(String reason);

    void setTime(long time);

}
