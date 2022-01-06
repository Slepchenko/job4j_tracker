package ru.job4j.tracker;

import java.sql.SQLException;
import java.util.List;

public interface Store {
    Item add(Item item) throws Exception;
    boolean replace(int id, Item item) throws Exception;
    boolean delete(int id) throws Exception;
    List<Item> findAll() throws Exception;
    List<Item> findByName(String key) throws SQLException;
    Item findById(int id) throws SQLException;
}
