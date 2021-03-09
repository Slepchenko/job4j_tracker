package ru.job4j.stream;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// Класс Analyze получает статистику по аттестатам.
public class Analyze {

    public static double averageScore(Stream<Pupil> stream) {
        return stream
                .flatMap(a -> a.getSubjects().stream())
                .mapToInt(c -> c.getScore())
                .average()
                .getAsDouble();
    }

    public static List<Tuple> averageScoreBySubject(Stream<Pupil> stream) {
        return stream.map(a -> new Tuple(a.getName(), a.getSubjects()
                .stream()
                .mapToInt(b -> b.getScore())
                .average()
                .getAsDouble()))
                .collect(Collectors.toList());
    }

    public static List<Tuple> averageScoreByPupil(Stream<Pupil> stream) {
        return stream.flatMap(a -> a.getSubjects().stream())
                .collect(Collectors.groupingBy(c -> c.getName(), Collectors.averagingDouble(c-> c.getScore())))
                        .entrySet()
                        .stream()
                        .map(x->new Tuple(x.getKey(), x.getValue()))
                        .collect(Collectors.toList());
    }

    public static Tuple bestStudent(Stream<Pupil> stream) {
//        return stream.map(pupil -> new Tuple(pupil.getName(), pupil.getSubjects().stream().mapToInt(b->b.getScore()).sum())).max().get();
        return null;
    }

    public static Tuple bestSubject(Stream<Pupil> stream) {
        return null;
    }

}
