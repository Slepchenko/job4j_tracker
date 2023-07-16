package ru.job4j.tracker;

public enum TrackerSingle1 {
    INSTANCE;
    private MemTracker memTracker = new MemTracker();

    public MemTracker getTracker() {
        return memTracker;
    }
}
