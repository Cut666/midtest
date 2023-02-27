package com.example.quanlythuvien.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String cccd;
    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

}
