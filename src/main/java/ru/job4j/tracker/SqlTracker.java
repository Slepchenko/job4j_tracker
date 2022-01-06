package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker<E> implements Store, AutoCloseable {

    private Connection cn;

    private PreparedStatement preparedStatement;

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) throws Exception {
        Timestamp timestamp = Timestamp.valueOf(item.getCreated());
        connection(String.format("insert into items(name, created) values('%s', '%s');",
                item.getName(),
                timestamp));
        return item;
    }

    @Override
    public boolean replace(int id, Item item) throws Exception {
        Timestamp timestamp = Timestamp.valueOf(item.getCreated());
        if (isExistIndex(id)) {
            connection(String.format("update items set name = '%s', created = '%s' where id = '%s'",
                    item.getName(),
                    timestamp,
                    id));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        if (isExistIndex(id)) {
            connection(String.format("delete from items where id = %s",
                    id));
            return true;
        }
        return false;
    }

    @Override
    public List<Item> findAll() throws Exception {
        preparedStatement = cn.prepareStatement("select id, name, created from items");
        return foundItems();
    }

    @Override
    public List<Item> findByName(String key) throws SQLException {
        preparedStatement = cn.prepareStatement(String.format(
                "select id, name, created from items where name = '%s'",
                key));
        return foundItems();
    }

    @Override
    public Item findById(int id) throws SQLException {
        preparedStatement = cn.prepareStatement(String.format(
                "select name, created from items where id = '%s'",
                id));
        ResultSet resultSet = preparedStatement.executeQuery();
        Item item = null;
        if (isExistIndex(id)) {
            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                Timestamp itemCreated = resultSet.getTimestamp("created");
                LocalDateTime createdLocalDateTime = itemCreated.toLocalDateTime();
                item = new Item(itemName);
                item.setId(id);
                item.setCreated(createdLocalDateTime);
            }
        }
        return item;
    }

    private void connection(String sql) throws Exception {
        try (Statement statement = cn.createStatement()) {
            statement.execute(sql);
        }
    }

    private boolean isExistIndex(int id) throws SQLException {
        /**
         Уважаемые менторы! Пользуясь случаем, хотел вас спросить, если вы это читаете,
         почему такой вариант не работает?:

         preparedStatement = cn.prepareStatement(String.format(
         "select case when id = %s then 1 else 0 end as result from items where id = %s",
         id, id));
         */

        preparedStatement = cn.prepareStatement(
                "select 1 as result from items where id =" + id
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        int result = 0;

        while (resultSet.next()) {
            result = resultSet.getInt("result");
        }
        return result == 1;
    }

    private List<Item> foundItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int itemID = resultSet.getInt("id");
            String itemName = resultSet.getString("name");
            Timestamp itemCreated = resultSet.getTimestamp("created");
            LocalDateTime createdLocalDateTime = itemCreated.toLocalDateTime();
            Item item = new Item(itemName);
            item.setId(itemID);
            item.setCreated(createdLocalDateTime);
            items.add(item);
        }
        return items;
    }
}
