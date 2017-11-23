package pl.bmstefanski.tools.io;

import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.configuration.Messages;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class MessageFile {

    private static Files files = new Files();

    public static void saveMessages() {
        final FileConfiguration data = files.getMessageFileConfiguration();

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
            data.save(files.getMessageFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadMessages()
    {
        try {
            final FileConfiguration data = files.getMessageFileConfiguration();

            for (final Field field : Messages.class.getFields()) {
                if (data.isSet(field.getName())) {
                    if (List.class.isAssignableFrom(field.getType())) field.set(null,
                            data.getStringList(field.getName().replace("\\n", "\n")));
                    else {
                        field.set(null, data.get(field.getName()));
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }
}
