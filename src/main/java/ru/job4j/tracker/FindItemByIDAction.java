package ru.job4j.tracker;

import java.sql.SQLException;

public class FindItemByIDAction implements UserAction {
    private final Output out;

    public FindItemByIDAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find item by Id";
    }

    @Override
    public boolean execute(Input input, Store tracker) throws SQLException {
        out.println(System.lineSeparator() + "=== Find item by Id ====");
        int id = Integer.valueOf(input.askStr("Enter id: "));
        Item item = tracker.findById(id);
        if (item != null) {
            out.println("Item " + id + " found");
        } else {
            out.println("Item with this id " + id + " not found");
        }
        return true;
    }
}
