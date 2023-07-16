package ru.job4j.tracker;

public class EditAction implements UserAction {
    private final Output out;

    public EditAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Edit";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println(System.lineSeparator() + "==== Edit item ====");
//        int id = Integer.parseInt(input.askStr("Enter id: "));
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        try {
            if (tracker.replace(id, item)) {
                out.println("Item " + name + " item edited successfully");
            } else {
                out.println("Item is not edited");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
