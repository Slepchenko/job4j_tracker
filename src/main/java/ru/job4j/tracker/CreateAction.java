package ru.job4j.tracker;

public class CreateAction implements UserAction {
    private final Output out;

    public CreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Create";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "=== Create a new Item ====");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        try {
            tracker.add(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
