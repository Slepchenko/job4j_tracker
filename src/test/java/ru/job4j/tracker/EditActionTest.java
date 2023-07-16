package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditActionTest {

    @Test
    public void edit() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("Edite item"));
        String editName = "New item name";
        EditAction rep = new EditAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(editName);
        rep.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString())
                .isEqualTo(ln + "==== Edit item ====" + ln + "Item New item name item edited successfully" + ln);
        assertThat(tracker.findAll().get(0).getName()).isEqualTo(editName);

    }
}