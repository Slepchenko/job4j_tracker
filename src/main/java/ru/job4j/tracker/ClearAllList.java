package ru.job4j.tracker;

public class ClearAllList implements UserAction {

    private final Output out;

    public ClearAllList(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Clear all List";
    }

    @Override
    public boolean execute(Input input, Store tracker) throws Exception {
        out.println(System.lineSeparator() + "=== Clear all List ====");
        int size = tracker.findAll().size();
        for (int i = 1; i < size + 1; i++) {
            try {
                tracker.delete(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
