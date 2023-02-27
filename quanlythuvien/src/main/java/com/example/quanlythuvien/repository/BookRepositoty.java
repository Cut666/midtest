package com.example.quanlythuvien.repository;

import com.example.quanlythuvien.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoty extends JpaRepository<Book,Integer> {
}
