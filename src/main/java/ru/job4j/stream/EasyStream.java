package ru.job4j.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class EasyStream {

    private List<Integer> source;

    private EasyStream(List<Integer> source) {
        this.source = source;
    }

    public static EasyStream of(List<Integer> source) {
        if (source == null) {
            throw new UnsupportedOperationException();
        }
        return new EasyStream(source);
    }

    public EasyStream map(Function<Integer, Integer> fun) {
        if (source == null) {
            throw new UnsupportedOperationException();
        }
        List<Integer> res = new ArrayList<>();
        for (Integer i : source) {
            int a = fun.apply(i);
            res.add(a);
        }
        return new EasyStream(res);
    }

    public EasyStream filter(Predicate<Integer> fun) {
        if (source == null) {
            throw new UnsupportedOperationException();
        }
        List<Integer> res = new ArrayList<>();
        for (Integer i : source) {
            if (fun.test(i)) {
                res.add(i);
            }
        }
        return new EasyStream(res);
     }

    public List<Integer> collect() {
        return source;
    }
}
