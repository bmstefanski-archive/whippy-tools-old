package pl.bmstefanski.tools.api.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

    void openConnection() throws SQLException;

    boolean checkConnection() throws SQLException;

    boolean closeConnection() throws SQLException;

    Connection getConnection();
}
