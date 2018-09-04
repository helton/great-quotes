package com.ciandt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="quotes")
@Data
@NoArgsConstructor
public class Quote implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="author", length = 30)
    private String author;

    @Column(name="quote")
    private String quote;

    @Column(name="created_at")
    private Date createdAt = new Date();

    @Column(name="likes")
    private Integer likes = 0;

    public Quote(Integer id, String author, String quote, Date createdAt, Integer likes) {
        this.id = id;
        this.author = author;
        this.quote = quote;
        this.createdAt = createdAt;
        this.likes = likes;
    }

    public Quote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

}