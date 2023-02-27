package com.example.quanlythuvien.Controller;

import com.example.quanlythuvien.model.dto.BorrowerDTO;
import com.example.quanlythuvien.model.dto.TicketBookDTO;
import com.example.quanlythuvien.repository.TicketBookRepository;
import com.example.quanlythuvien.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderRentController {
    @Autowired
    private RentService rentService;
    @PostMapping("/rent/create")
    public ResponseEntity<?> create(@RequestBody TicketBookDTO ticketBookDTO, HttpServletRequest request){
        return ResponseEntity.ok(rentService.orderRent(ticketBookDTO,request));
    }

    @PostMapping("/rent/update")
    public ResponseEntity<?> update(@RequestBody TicketBookDTO ticketBookDTO){
        return ResponseEntity.ok(rentService.giveBook(ticketBookDTO));
    }

    @PostMapping("/acc/create")
    public ResponseEntity<?> accCreate(@RequestBody BorrowerDTO borrowerDTO){
        return ResponseEntity.ok(rentService.createAcc(borrowerDTO));
    }
}
