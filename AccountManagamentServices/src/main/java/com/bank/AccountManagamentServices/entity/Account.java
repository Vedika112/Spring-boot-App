package com.bank.AccountManagamentServices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer accID;
    private String accNo;
    private String accHolderName;
    private int accBalance;
    @DateTimeFormat
    Date accOpenDate;
    private Integer custId;
    @Transient
    Customer customer;



}
