package com.example.quanlythuvien.service;

import com.example.quanlythuvien.model.dto.BorrowerDTO;
import com.example.quanlythuvien.model.dto.TicketBookDTO;
import com.example.quanlythuvien.model.entity.Borrower;
import com.example.quanlythuvien.model.entity.TicketBook;

import javax.servlet.http.HttpServletRequest;

public interface RentService {
    TicketBook orderRent(TicketBookDTO ticketBookDTO, HttpServletRequest request);
    TicketBook giveBook(TicketBookDTO ticketBookDTO);
    Borrower createAcc(BorrowerDTO borrowerDTO);
    TicketBook indemnifyBook(TicketBookDTO ticketBookDTO);

}
