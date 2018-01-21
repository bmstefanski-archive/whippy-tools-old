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
