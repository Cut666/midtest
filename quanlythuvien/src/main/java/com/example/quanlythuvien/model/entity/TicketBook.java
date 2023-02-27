package com.example.quanlythuvien.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class TicketBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "create_at")
    private Date createAt;
    @Column (name = "return_date")
    private Date returnDate;
    private String status;
    private String note;
    @JsonManagedReference
    @OneToMany(mappedBy = "ticketBook")
    private Set<Book> books;
    @OneToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;
}
