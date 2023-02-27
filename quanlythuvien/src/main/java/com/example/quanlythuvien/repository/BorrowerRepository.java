package com.example.quanlythuvien.repository;

import com.example.quanlythuvien.model.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower,Integer> {
    Borrower findByPhone(String phone);
}
