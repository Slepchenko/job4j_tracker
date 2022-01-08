package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store, AutoCloseable {

    private Connection cn;

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
    public Item add(Item item) {
        try (PreparedStatement statement =
                     cn.prepareStatement("insert into items(name, created) values(?, ?);",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        try (PreparedStatement statement =
                     cn.prepareStatement("update items set name = ?, created = ? where id = ?",
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.setInt(3, id);
            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement statement =
                     cn.prepareStatement("delete from items where id = ?")) {
            statement.setInt(1, id);
            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("select * from items")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Timestamp itemCreated = resultSet.getTimestamp("created");
                    LocalDateTime createdLocalDateTime = itemCreated.toLocalDateTime();
                    Item item = new Item(resultSet.getString("name"));
                    item.setId(resultSet.getInt("id"));
                    item.setCreated(createdLocalDateTime);
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement(String.format(
                "select * from items where name = '%s'", key))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Timestamp itemCreated = resultSet.getTimestamp("created");
                    LocalDateTime createdLocalDateTime = itemCreated.toLocalDateTime();
                    Item item = new Item(resultSet.getString("name"));
                    item.setId(resultSet.getInt("id"));
                    item.setCreated(createdLocalDateTime);
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item findById(int id) throws SQLException {
        Item item;
        try (PreparedStatement statement = cn.prepareStatement(String.format(
                "select * from items where id = '%s'",
                id))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                item = new Item(resultSet.getString("name"));
                Timestamp itemCreated = resultSet.getTimestamp("created");
                LocalDateTime createdLocalDateTime = itemCreated.toLocalDateTime();
                item.setId(id);
                item.setCreated(createdLocalDateTime);
            }
        }
        return item;
    }
}