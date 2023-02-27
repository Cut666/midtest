package com.example.quanlythuvien.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    private int id;
    private String name;
    private String author;
    private double price;
    private int quantity;
}
