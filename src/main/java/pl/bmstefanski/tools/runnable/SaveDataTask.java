package pl.bmstefanski.tools.runnable;

import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.storage.AbstractStorage;
import pl.bmstefanski.tools.type.PreparedStatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveDataTask extends AbstractStorage implements Runnable {

    private final User user;

    public SaveDataTask(Storage storage, User user) {
        super(storage);

        this.user = user;
    }

    @Override
    public void run() {
        try {
            getStorage().connect();

            PreparedStatement preparedStatement = getStorage().getPreparedStatement(PreparedStatements.SAVE_PLAYER.name());

            preparedStatement.setString(1, user.getUUID().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getIp());
            preparedStatement.setString(4, user.getUUID().toString());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getIp());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Saved to database " + user.getName() + " | " + user.getIp());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
