package com.example.quanlythuvien.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String author;
    private double price;
    private int quantity;
    @JsonBackReference
    @ManyToMany
    @JoinTable (name = "book_type", joinColumns = @JoinColumn(name = "Book_id"),inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<Type> types;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ticketBook_id")
    private TicketBook ticketBook;

}
