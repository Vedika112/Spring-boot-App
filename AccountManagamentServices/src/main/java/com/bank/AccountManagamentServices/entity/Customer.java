package com.bank.AccountManagamentServices.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
@Id
private Integer custId;
private String custName;
private String custAddress;
private String custPhone;
private String custEmail;

}