package pl.bmstefanski.tools.storage.resource;

import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.storage.AbstractStorage;
import pl.bmstefanski.tools.type.PreparedStatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserResourceManager extends AbstractStorage {

    public UserResourceManager(Storage storage) {
        super(storage);
    }

    public void load(User user) {

        getStorage().connect();

        try {
            PreparedStatement preparedStatement = getStorage().getPreparedStatement(PreparedStatements.LOAD_PLAYER.name());
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

    public void save(User user) {
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
