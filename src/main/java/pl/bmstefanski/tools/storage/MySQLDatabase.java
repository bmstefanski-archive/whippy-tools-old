/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.storage;

import pl.bmstefanski.tools.util.reflect.Reflections;

import java.sql.*;

public class MySQLDatabase extends AbstractDatabase {

    private final String username;
    private final String database;
    private final String password;
    private final String hostname;
    private final int port;

    public MySQLDatabase(String username, String database, String password, String hostname, int port) {
        this.username = username;
        this.database = database;
        this.password = password;
        this.hostname = hostname;
        this.port = port;

        if (!setUp()) {
            throw new RuntimeException("Database is not setup!");
        }
    }

    @Override
    public void openConnection() throws SQLException {
        if (checkConnection()) {
            return;
        }

        Reflections.getClass("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true", username, password);
    }

    @Override
    public boolean connect() {
        try {
            if (checkConnection()) {
                return true;
            }

            openConnection();
            setUpTables();
            prepareStatements();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    @Override
    public int returnKey(Statement statement) {
        try {
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            int id = resultSet.getInt(1);

            if (id == 0) {
                throw new RuntimeException("Could not get generated keys");
            }

            return id;
        } catch (SQLException ex) {
            throw new RuntimeException("Could not get generated keys", ex);
        }
    }

    @Override
    public boolean isReturnGeneratedKeys() {
        return true;
    }
}

