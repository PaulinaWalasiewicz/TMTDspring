package com.tmtd.tmtdspring.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="DESCRIPTION")
public class Description {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;
    @Column(name="content")
    private String content;

    public Description(String content) {
        this.content = content;
    }

    public Description() {

    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }
}
