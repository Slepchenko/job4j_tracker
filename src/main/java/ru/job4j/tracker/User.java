package ru.job4j.tracker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "j_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "j_user_id")
    private List<UserMessenger> messengers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
