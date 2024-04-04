package com.blog9.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String body;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    // Constructors, getters, setters

    // Other methods if needed
}