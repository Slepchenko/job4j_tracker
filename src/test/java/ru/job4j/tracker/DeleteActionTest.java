package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteActionTest {
    @Test
    public void delete() throws Exception {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("Edite item"));
        String editName = "Item name";
        DeleteAction rep = new DeleteAction(out);
        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);
        when(input.askStr(any(String.class))).thenReturn(editName);
        rep.execute(input, tracker);
        tracker.delete(1);
        String ln = System.lineSeparator();
        assertThat(out.toString())
                .isEqualTo(
                        ln + "=== Delete item ===="
                                + ln + "Item 1 deleted"
                                + ln
                );
        assertThat(tracker.findAll().size()).isEqualTo(0);
    }
}