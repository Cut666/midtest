package com.example.quanlythuvien.service;

import com.example.quanlythuvien.model.dto.BookDTO;
import com.example.quanlythuvien.model.dto.BorrowerDTO;
import com.example.quanlythuvien.model.dto.TicketBookDTO;
import com.example.quanlythuvien.model.entity.Book;
import com.example.quanlythuvien.model.entity.Borrower;
import com.example.quanlythuvien.model.entity.TicketBook;
import com.example.quanlythuvien.model.entity.Wallet;
import com.example.quanlythuvien.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentImlService implements RentService{
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
    @Transactional
    @Override
    public TicketBook orderRent(TicketBookDTO ticketBookDTO, HttpServletRequest request) {
        TicketBook ticketBook = new TicketBook();
        ModelMapper mapper = new ModelMapper();
        double dep = deposit(ticketBookDTO.getBooks());
        Borrower borrower = borrowerRepository.findByPhone(ticketBookDTO.getBorrower().getPhone());
        if(borrower!=null){
            if (borrower.getWallet().getBalance()-dep>0){
                borrower.getWallet().setBalance(borrower.getWallet().getBalance()-dep);
                ticketBook.setBorrower(borrower);
            }else {
                System.out.println("yeu cau nap tien");
                borrower.getWallet().setBalance(borrower.getWallet().getBalance());
                walletRepository.save(borrower.getWallet());
            }

        }
        else {
            Wallet wallet = new Wallet();
            wallet.setAccountNum(ticketBookDTO.getBorrower().getWallet().getAccountNum());
            wallet.setBalance(ticketBookDTO.getBorrower().getWallet().getBalance());
            walletRepository.save(wallet);

            Borrower borrower1 = mapper.map(ticketBookDTO.getBorrower(),Borrower.class);
            borrowerRepository.save(borrower1);
            ticketBook.setBorrower(borrower1);
        }

        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println("Current: "+ currentDate);
        ticketBook.setCreateAt(currentDate);

        int numberOfDaysToAdd = Integer.parseInt(request.getParameter("numberOfDaysToAdd"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, numberOfDaysToAdd);
        currentDate = calendar.getTime();
        System.out.println("returnDate: " + currentDate);
        ticketBook.setReturnDate(currentDate);

        ticketBook.setStatus("are renting");
        ticketBookRepository.save(ticketBook);

        Set<Book> books = ticketBookDTO.getBooks().stream().map(bookDTO -> mapper.map(bookDTO,Book.class)).collect(Collectors.toSet());
        for (Book b:books) {
            Book book = bookRepositoty.findById(b.getId()).orElse(null);
            if (book == null) {
                System.out.println("Sách không tồn tại trong kho!");
                return null;
            }
            if (book.getQuantity() < b.getQuantity()) {
                System.out.println("Số lượng sách trong kho không đủ!");
                return null;
            }
            b.setTicketBook(ticketBook);
            book.setQuantity(book.getQuantity() - b.getQuantity());
            bookRepositoty.save(book);
        }
        Wallet wallet = walletRepository.findByAccountNum("123123");
        wallet.setBalance(wallet.getBalance()+dep);
        walletRepository.save(wallet);
        return ticketBook;
    }

    private double deposit(Set<Book> book){
        double dep = 0;
        for (Book b:book){
            dep+= b.getPrice();
        }
        return dep*0.8;
    }
    @Override
    public TicketBook giveBook(TicketBookDTO ticketBookDTO) {
        TicketBook ticketBook = ticketBookRepository.findById(ticketBookDTO.getId()).get();
        double dep = deposit(ticketBookDTO.getBooks());

        Date currentDate = new Date(System.currentTimeMillis());
        ticketBook.setReturnDate(currentDate);

        Date bookBack = ticketBook.getCreateAt();
        LocalDate startTicketBook = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTicketBook = bookBack.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long numDate = ChronoUnit.DAYS.between(startTicketBook,endTicketBook);
        long totalBook = ticketBook.getBooks().size();
        double price = (numDate*totalBook*15000)-dep;

        double walletBorr = ticketBook.getBorrower().getWallet().getBalance();
        if (walletBorr<price){
            System.out.println("yeu cau nap tien");
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance());
            walletRepository.save(ticketBook.getBorrower().getWallet());
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance()-price);
            walletRepository.save(ticketBook.getBorrower().getWallet());
        }
        else {
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance()-price);
            walletRepository.save(ticketBook.getBorrower().getWallet());
        }
        Wallet wallet = walletRepository.findByAccountNum("123123");
        wallet.setBalance(wallet.getBalance()+price);
        walletRepository.save(wallet);

        ticketBook.setStatus("đã trả sách");
        ticketBookRepository.save(ticketBook);
        Set<Book> books = ticketBook.getBooks();
        for (Book book: books){
            Book book1 = bookRepositoty.findById(book.getId()).get();
            book1.setQuantity(book1.getQuantity()+book.getQuantity());
            bookRepositoty.save(book);
        }
        return ticketBook;
    }

    @Override
    public Borrower createAcc(BorrowerDTO borrowerDTO) {
        Borrower borrower = borrowerRepository.findByPhone(borrowerDTO.getPhone());
        if (borrower==null){
            Wallet wallet = new Wallet();
            wallet.setAccountNum(wallet.getAccountNum());
            wallet.setBalance(wallet.getBalance());
            walletRepository.save(wallet);

            Borrower borrower1 = new Borrower();
            borrower1.setName(borrower1.getName());
            borrower1.setPhone(borrower1.getPhone());
            borrower1.setCccd(borrower1.getCccd());
            borrower1.setWallet(wallet);
            borrowerRepository.save(borrower1);
        }
        return borrower;
    }

    @Override
    public TicketBook indemnifyBook(TicketBookDTO ticketBookDTO) {
        ModelMapper mapper = new ModelMapper();
        TicketBook ticketBook = ticketBookRepository.findById(ticketBookDTO.getId()).get();
        double dep = deposit(ticketBookDTO.getBooks());

        Set<Book> returnBooks = ticketBookDTO.getBooks().stream().map(bookDTO -> mapper.map(bookDTO,Book.class)).collect(Collectors.toSet());
        Set<Book> books = ticketBook.getBooks().stream().map(bookDTO -> mapper.map(bookDTO,Book.class)).collect(Collectors.toSet());
        double totalPay=0;
        for (Book b: returnBooks){
            Book book = bookRepositoty.findById(b.getId()).orElse(null);
            if (books.contains(book)){
                for (Book b2: books){
                    Book book2 = bookRepositoty.findById(b2.getId()).orElse(null);
                    if (book==book2){
                        long quantity = b2.getQuantity()-b.getQuantity();
                        double pay = b2.getPrice()*quantity*0.85;
                        totalPay+=pay;
                    }
                }
            }else {
                double pay = book.getPrice()*book.getQuantity()*0.85;
                totalPay+=pay;
            }
        }

        Date currentDate = new Date(System.currentTimeMillis());
        ticketBook.setReturnDate(currentDate);

        Date bookBack = ticketBook.getCreateAt();
        LocalDate startTicketBook = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTicketBook = bookBack.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long numDate = ChronoUnit.DAYS.between(startTicketBook,endTicketBook);
        long totalBook = ticketBook.getBooks().size();
        double price = (numDate*totalBook*15000)-dep+totalPay;

        double walletBorr = ticketBook.getBorrower().getWallet().getBalance();
        if (walletBorr<price){
            System.out.println("yeu cau nap tien");
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance());
            walletRepository.save(ticketBook.getBorrower().getWallet());
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance()-price);
            walletRepository.save(ticketBook.getBorrower().getWallet());
        }
        else {
            ticketBook.getBorrower().getWallet().setBalance(ticketBook.getBorrower().getWallet().getBalance()-price);
            walletRepository.save(ticketBook.getBorrower().getWallet());
        }
        Wallet wallet = walletRepository.findByAccountNum("123123");
        wallet.setBalance(wallet.getBalance()+price);
        walletRepository.save(wallet);

        ticketBook.setStatus("đã hoàn thành");
        ticketBookRepository.save(ticketBook);
        return ticketBook;
    }
}
