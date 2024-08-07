package ru.job4j.tracker;

public class DeleteAction implements UserAction {

    private final Output out;

    public DeleteAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Delete";
    }

    @Override
    public boolean execute(Input input, Store tracker) throws Exception {
        out.println(System.lineSeparator() + "=== Delete item ====");
        int id = input.askInt("Enter id: ");
        if (tracker.delete(id)) {
            out.println("Item " + id + " deleted");
        } else {
            out.println("Item " + id + " not deleted");
        }
        return true;
    }
}
