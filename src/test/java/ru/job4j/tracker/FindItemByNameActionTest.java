package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindItemByNameActionTest {

    @Test
    public void findByName() throws SQLException {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        String editName = "TestItem";
        tracker.add(new Item(editName));
        FindItemByNameAction rep = new FindItemByNameAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(editName);
        rep.execute(input, tracker);
        List<Item> expected = tracker.findByName("TestItem");
        String ln = System.lineSeparator();
        assertThat(out.toString())
                .isEqualTo(
                        ln + "=== Find items by name ===="
                                + ln + "ID: " + expected.get(0).getId()
                                + ln + "Name: " + expected.get(0).getName()
                        + ln + "Date of creation: " + expected.get(0).getCreated() + ln
                );
        assertThat(expected.get(0).getName()).isEqualTo(editName);
    }
}