package com.bank.AccountManagamentServices.controller;

import com.bank.AccountManagamentServices.entity.Account;
import com.bank.AccountManagamentServices.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AccountController {
    @Autowired
    AccountService accountService;
    @GetMapping("/")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to the Account Management Service!");
    }
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }
    @PostMapping("/{custId}")
    public ResponseEntity<Account> createAccountId(@RequestBody Account account,@PathVariable Integer custId){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccountId(account,custId));
    }
    @GetMapping("/{accId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Integer accId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccDetails(accId));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Account>> getAllAccountDetails()
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccDetails());
    }

@PutMapping("/addMoney/{accId}")
    public ResponseEntity<Account> addMoney(@PathVariable Integer accId,@RequestParam Integer custId,@RequestParam  int amount){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.addMoney(accId,custId,amount));
    }
    @PutMapping("/withdraw/{accId}")
    public ResponseEntity<Account> withdrawMoney(@PathVariable Integer accId,@RequestParam Integer custId,@RequestParam  int amount){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.withdrawMoney(accId,custId,amount));
    }
    @DeleteMapping("/{accId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer accId)
    {
        accountService.deleteAccount(accId);
        String message ="Account with ID " + accId + " is deleted successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
    @DeleteMapping("/deleteByCustId/{custId}")
    public ResponseEntity<String> deleteAccountByCustomerID(@PathVariable Integer custId)
    {
        accountService.deleteAccountByCustomerID(custId);
        String message ="Account with customer ID " + custId + " is deleted successfully.";
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}
