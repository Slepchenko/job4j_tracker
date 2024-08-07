package ru.job4j.tracker;

import org.junit.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Ignore
public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().
                getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenReplaceItemThenTrue() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        Item testItem = new Item("Test Item");
        tracker.add(item);
        Assert.assertTrue(tracker.replace(item.getId(), testItem));
    }

    @Test
    public void whenReplaceItemThenItemIsReplaced() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("Item");
        tracker.add(item);
        Item testItem = new Item("Test Item");
        tracker.replace(item.getId(), testItem);
        assertThat(tracker.findById(item.getId()).getName(), is(testItem.getName()));
    }

    @Test
    public void whenDeleteItemThenTrue() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        Assert.assertTrue(tracker.delete(item.getId()));
    }

    @Test
    public void whenDeleteItemThenItemIsDeleted() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.delete(item.getId());
        Assert.assertNull(tracker.findById(item.getId()));
    }

    @Test
    public void whenFindByNameTwoSameItemThenGetListItems() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("name");
        Item item2 = new Item("name");
        List<Item> items = List.of(item1, item2);
        tracker.add(item1);
        tracker.add(item2);
        assertThat(tracker.findByName("name"), is(items));
    }

    @Test
    public void whenFindByIdThenGetItem() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("Item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenFindAllWhereThereAreThreeItemsThenGetAllListSameItems() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item1 = new Item("name1");
        Item item2 = new Item("name2");
        Item item3 = new Item("name3");
        List<Item> items = List.of(item1, item2, item3);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findAll(), is(items));
    }
}
