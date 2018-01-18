package pl.bmstefanski.tools.runnable;

import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.storage.AbstractStorage;
import pl.bmstefanski.tools.type.StatementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LoadDataTask extends AbstractStorage implements Runnable {

    private final User user;

    public LoadDataTask(Storage storage, User user) {
        super(storage);

        this.user = user;
    }

    @Override
    public void run() {
        getStorage().connect();

        try {
            PreparedStatement preparedStatement = getStorage().getPreparedStatement(StatementType.LOAD_PLAYER);
            preparedStatement.setString(1, user.getUUID().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setUUID(UUID.fromString(resultSet.getString("uuid")));
                user.setName(resultSet.getString("name"));
                user.setIp(resultSet.getString("ip"));
            }

            resultSet.close();
            preparedStatement.close();

            System.out.println("Loaded from database " + user.getName() + " | " + user.getIp());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
