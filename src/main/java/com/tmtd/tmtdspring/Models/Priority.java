package com.tmtd.tmtdspring.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "PRIORITY")
public class Priority {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    @Column(name = "content")
    private String content;
//    LOW,
//    MEDIUM,
//    HIGH


    public Priority(String content) {
        this.content = content;
    }

    public Priority() {

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

