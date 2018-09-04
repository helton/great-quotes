package com.ciandt.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Message implements Serializable {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message() {

    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}