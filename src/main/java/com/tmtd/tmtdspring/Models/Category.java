package com.tmtd.tmtdspring.Models;

import jakarta.persistence.*;

@Entity
@Table(name="CATEGORY")
public class Category {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    @Column(name="content")
    private String content;

//    FOOD,
//    ENTERTAINMENT,
//    TRAVEL,
//    HOME,
//    HEALTH,
//    TECHNOLOGY,
//    OTHER

    public Category(String content) {
        this.content = content;
    }

    public Category() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}