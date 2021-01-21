package ru.job4j.collection;

import java.util.Comparator;

public class StringCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {

        for (int i = 0; i < Math.min(left.length(), right.length()); i++) {
            int result = Character.compare(left.charAt(i),right.charAt(i));
            int resLength = Integer.compare(left.length(), right.length());
            if (result == 1) {
                return 1;
            } else if (result == -1) {
                return -1;
            } else {
                if (resLength == 1) {
                    return 1;
                } else if (resLength == -1) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
