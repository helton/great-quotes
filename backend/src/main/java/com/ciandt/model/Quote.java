package com.ciandt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="quotes")
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

    public Quote() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}