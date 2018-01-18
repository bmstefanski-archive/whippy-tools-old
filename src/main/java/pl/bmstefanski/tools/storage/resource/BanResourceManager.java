package pl.bmstefanski.tools.storage.resource;

import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.BanImpl;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.storage.AbstractStorage;
import pl.bmstefanski.tools.type.StatementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BanResourceManager extends AbstractStorage {

    private final List<Ban> banList = BanManager.getBans();

    public BanResourceManager(Storage storage) {
        super(storage);
    }

    public void load() {

        getStorage().connect();

        try {
            PreparedStatement preparedStatement = getStorage().getPreparedStatement(StatementType.LOAD_BANS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID punished = UUID.fromString(resultSet.getString("punished"));
                UUID punisher = UUID.fromString(resultSet.getString("punisher"));

                Ban ban = new BanImpl(punished, punisher);

                ban.setReason(resultSet.getString("reason"));
                ban.setTime(resultSet.getLong("until"));

                banList.add(ban);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void save() {

        getStorage().connect();

        try {
            for (Ban ban : banList) {
                PreparedStatement preparedStatement = getStorage().getPreparedStatement(StatementType.SAVE_BANS);

                preparedStatement.setString(1, ban.getReason());
                preparedStatement.setLong(2, ban.getTime());
                preparedStatement.setString(3, ban.getPunished().toString());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void add(Ban ban) {

        getStorage().connect();

        try {
            PreparedStatement preparedStatement = getStorage().getPreparedStatement(StatementType.ADD_BAN);

            preparedStatement.setString(1, ban.getPunisher().toString());
            preparedStatement.setString(2, ban.getPunished().toString());
            preparedStatement.setLong(3, ban.getTime());
            preparedStatement.setString(4, ban.getReason());

            preparedStatement.executeUpdate();

            banList.add(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Ban ban) {

        getStorage().connect();

        try {
            PreparedStatement preparedStatement = getStorage().getPreparedStatement(StatementType.REMOVE_BAN);
            preparedStatement.setString(1, ban.getPunished().toString());

            preparedStatement.executeUpdate();

            banList.remove(ban);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }




}
