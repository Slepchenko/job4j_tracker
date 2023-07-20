package ru.job4j.tracker;

public class CreateBigListItemAction implements UserAction {

    private final Output out;

    public CreateBigListItemAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Big list of Items";
    }

    @Override
    public boolean execute(Input input, Store tracker) throws Exception {
        out.println(System.lineSeparator() + "=== Create Big List of Item ====");
        for (int i = 0; i < 300000; i++) {
            Item item = new Item("Item_" + i);
            try {
                tracker.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
