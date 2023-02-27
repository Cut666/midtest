package com.example.quanlythuvien.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowerDTO {
    private int id;
    private String name;
    private String phone;
    private String cccd;
}
