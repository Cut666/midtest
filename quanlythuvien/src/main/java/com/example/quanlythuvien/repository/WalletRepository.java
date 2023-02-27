package com.example.quanlythuvien.repository;

import com.example.quanlythuvien.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {
    Wallet findByAccountNum(String s);
}
