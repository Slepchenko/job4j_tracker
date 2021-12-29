package ru.job4j;

import ru.job4j.collection.Job;
import ru.job4j.collection.JobDescByName;
import ru.job4j.collection.JobDescByPriority;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.List.of;

public class Tester {

    private String passport;

    private List<Child> children = new LinkedList<>();

    public Tester(String passport, List<Child> children) {
        this.passport = passport;
        this.children = children;
    }

    public String getPassport() {
        return passport;
    }

    public List<Child> getChildren() {
        return children;
    }


public static class Child {

    private String name;

    private int age;

    public Child(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public static List<Child> defineChildren(List<Tester> workers, String passport) {
        return findByPassport(workers, passport).stream().flatMap(z -> z.getChildren().stream()).filter(z -> z.getAge() >= 15).collect(Collectors.toList());
    }

    public static Optional<Tester> findByPassport(List<Tester> workers, String passport) {
        return workers.stream()
                .filter(w -> w.getPassport().equals(passport))
                .findFirst();
    }

    public static void main(String[] args) {
        Child c1 = new Child("c1", 15);
        Child c2 = new Child("c2", 18);
        Child c3 = new Child("c1", 20);
        Child c4 = new Child("c2", 15);
        Tester worker1 = new Tester("123", List.of(c1, c2, c3));
        Tester worker2 = new Tester("456", List.of(c4));
        List<Child> l =defineChildren(List.of(worker1, worker2), "456");
        for (Child ch : l)
        System.out.println(ch.getName() + " " + ch.getAge());
    }
}
}

