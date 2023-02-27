package com.example.quanlythuvien;

import com.example.quanlythuvien.model.entity.*;
import com.example.quanlythuvien.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class QuanlythuvienApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QuanlythuvienApplication.class, args);
    }
    @Autowired
    BookRepositoty bookRepositoty;
    @Autowired
    BorrowerRepository borrowerRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    TicketBookRepository ticketBookRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    WalletRepository walletRepository;
    @Override
    public void run(String... args) throws Exception {
//        Service service1 = new Service();
//        service1.setName("thuê sách");
//        Service service2 = new Service();
//        service2.setName("mua sách");
//        serviceRepository.saveAll(Arrays.asList(service1,service2));
//
//        Type type1 =new Type();
//        type1.setName("tài liệu");
//        Type type2 = new Type();
//        type2.setName("học tập");
//        Type type3 = new Type();
//        type3.setName("truyện");
//        typeRepository.saveAll(Arrays.asList(type1,type2,type3));
//
//        Book book1 = new Book();
//        book1.setName("spring boot");
//        book1.setAuthor("Nguyễn Văn A");
//        book1.setPrice(15000);
//        book1.setQuantity(5);
//        book1.setTypes(Set.of(type1,type2));
//        Book book2 = new Book();
//        book2.setName("java");
//        book2.setAuthor("Nguyễn Văn B");
//        book2.setPrice(20000);
//        book2.setQuantity(6);
//        book2.setTypes(Set.of(type1,type2));
//        Book book3 = new Book();
//        book3.setName("javascrips");
//        book3.setAuthor("Nguyễn Văn C");
//        book3.setPrice(22000);
//        book3.setQuantity(10);
//        book3.setTypes(Set.of(type1,type2));
//        bookRepositoty.saveAll(Arrays.asList(book1,book2,book3));
//
//        Wallet wallet1 = new Wallet();
//        wallet1.setAccountNum("45729");
//        wallet1.setBalance(50000);
//        Wallet wallet2 = new Wallet();
//        wallet2.setAccountNum("123123");
//        wallet2.setBalance(100000);
//        walletRepository.saveAll(Arrays.asList(wallet1,wallet2));
//
//        Borrower borrower1 = new Borrower();
//        borrower1.setName("thai1");
//        borrower1.setPhone("0912345678");
//        borrower1.setCccd("9243.3847.2938");
//        borrower1.setWallet(wallet1);
//        borrowerRepository.save(borrower1);
    }
}
