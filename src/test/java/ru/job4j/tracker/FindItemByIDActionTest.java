package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindItemByIDActionTest {

    @Test
    public void findById() throws SQLException {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("Edite item"));
        String editName = "Item name";
        FindItemByIDAction rep = new FindItemByIDAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(editName);
        rep.execute(input, tracker);
        Item expected = tracker.findById(1);
        String ln = System.lineSeparator();
        assertThat(out.toString())
                .isEqualTo(
                        ln + "=== Find item by Id ===="
                                + ln + "Item 1 found"
                                + ln
                );
        assertThat(expected.getId()).isEqualTo(1);
    }
}