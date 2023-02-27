package com.example.quanlythuvien.model.dto;

import com.example.quanlythuvien.model.entity.Book;
import com.example.quanlythuvien.model.entity.Borrower;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketBookDTO {
    private int id;
    private Date createAt;
    private Date returnDate;
    private String status;
    private String note;
    private Borrower borrower;
    private Set<Book> books;
}
