package pl.bmstefanski.tools.io;

import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.impl.configuration.Messages;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class MessageFile {

    public static void saveMessages() {
        final FileConfiguration data = Files.getMessageFileConfiguration();

        for (Field fld : Messages.class.getFields()) {
            if (!data.isSet(fld.getName())) {
                try {
                    data.set(fld.getName(), fld.get(fld.getName()));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }

        try {
            data.save(Files.getMessageFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadMessages()
    {
        try {
            final FileConfiguration data = Files.getMessageFileConfiguration();

            for (final Field field : Messages.class.getFields()) {
                if (data.isSet(field.getName())) {
                    if (List.class.isAssignableFrom(field.getType())) field.set(null,
                            data.getStringList(field.getName().replace("\\n", "\n")));
                    else {
                        field.set(null, data.get(field.getName()));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
