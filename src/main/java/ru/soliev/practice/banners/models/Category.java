package ru.soliev.practice.banners.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "req_name")
    private String reqName;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Banner> banners = new ArrayList<>();
}
