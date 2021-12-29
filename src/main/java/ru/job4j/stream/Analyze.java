package ru.job4j.stream;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyze {

    public static double averageScore(Stream<Pupil> stream) {
        return stream
                .flatMap(a -> a.getSubjects().stream())
                .mapToInt(Subject::getScore)
                .average()
                .getAsDouble();
    }

    public static List<Tuple> averageScoreBySubject(Stream<Pupil> stream) {
        return stream
                .map(a -> new Tuple(a.getName(), a.getSubjects()
                .stream()
                .mapToInt(Subject::getScore)
                .average()
                .getAsDouble()))
                .collect(Collectors.toList());
    }

    public static List<Tuple> averageScoreByPupil(Stream<Pupil> stream) {
        return stream
                .flatMap(a -> a.getSubjects().stream())
                .collect(Collectors
                        .groupingBy(Subject::getName, LinkedHashMap::new,
                                Collectors.averagingDouble(Subject::getScore)))
                .entrySet()
                .stream()
                .map(d -> new Tuple(d.getKey(), d.getValue()))
                .collect(Collectors.toList());
    }

    public static Tuple bestStudent(Stream<Pupil> stream) {
        return stream
                .map(pupil -> new Tuple(pupil.getName(), pupil.getSubjects()
                        .stream()
                        .mapToInt(Subject::getScore).sum()))
                .max(Comparator.comparingInt(x -> (int) x.getScore()))
                .orElse(new Tuple("Noname", 0));
    }

    public static Tuple bestSubject(Stream<Pupil> stream) {
        return stream
                .flatMap(a -> a.getSubjects().stream())
                .collect(Collectors.groupingBy(Subject::getName, LinkedHashMap::new,
                        Collectors.summingDouble(Subject::getScore)))
                .entrySet()
                .stream()
                .map(c -> new Tuple(c.getKey(), c.getValue()))
                .max(Comparator.comparingInt(d -> (int) d.getScore()))
                .orElse(new Tuple("Noname", 0));
    }

}
