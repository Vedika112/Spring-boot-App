package com.bank.AccountManagamentServices.service;

import com.bank.AccountManagamentServices.entity.Account;
import com.bank.AccountManagamentServices.entity.Customer;
import com.bank.AccountManagamentServices.repo.AccountRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service

public class AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RestTemplate restTemplate;

    public Account createAccount(Account account) {
        Date curDate = new Date();
        account.setAccOpenDate(curDate);
        return accountRepo.save(account);
    }
    public Account createAccountId(Account account, Integer custId) {
        Date curDate = new Date();
        validateCustomerDetails(custId);
        account.setAccOpenDate(curDate);
        account.setCustId(custId);
        return accountRepo.save(account);
    }

    public Account getAccDetails(Integer id) {
        try {
            Account account = accountRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Account with given id not found."));

            Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + account.getCustId(), Customer.class);
            account.setCustomer(customer);

            return account;
        } catch (NoSuchElementException e) {

            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account with given id not found. Please check the details.");
        } catch (Exception e) {

            throw new RuntimeException("An error occurred while fetching account details.", e);
        }
    }

    public List<Account> getAllAccDetails() {
        return accountRepo.findAll() ;

    }
        public Account addMoney(Integer accId, Integer custId, int addedMoney) {
        try {
            // Validate customer details
            validateCustomerDetails(custId);

            // If customer details are valid, proceed with adding money to the account
            Account account = accountRepo.findById(accId)
                    .orElseThrow(() -> new RuntimeException("Account with given id not found. Please check the details."));

            int updatedBalance = account.getAccBalance() + addedMoney;
            account.setAccBalance(updatedBalance);

            // Save the updated account
            return accountRepo.save(account);
        } catch (HttpClientErrorException.NotFound e) {

            throw new RuntimeException("Customer details are not valid. Please check the details.", e);
        } catch (Exception e) {

            throw new RuntimeException("An error occurred while adding money to the account.", e);
        }
    }


    public Account withdrawMoney(Integer accId, Integer custId, int withdrawal) {
        try {
            // Validate customer details
            validateCustomerDetails(custId);

            // If customer details are valid, proceed with adding money to the account
            Account account = accountRepo.findById(accId).orElseThrow(() -> new RuntimeException("Account with given id not found. Please check the details."));

            int updatedBalance = account.getAccBalance() - withdrawal;
            account.setAccBalance(updatedBalance);

            // Save the updated account
            return accountRepo.save(account);
        } catch (HttpClientErrorException.NotFound e) {

            throw new RuntimeException("Customer details are not valid. Please check the details.", e);
        } catch (Exception e) {

            throw new RuntimeException("An error occurred while withdrawing money to the account.", e);
        }
    }


    public void deleteAccount(Integer accId) {
        Account account = accountRepo.findById(accId).orElseThrow(() -> new RuntimeException("Account with given id not found try again with correct details !!"));
        accountRepo.delete(account);


    }
    public void deleteAccountByCustomerID(Integer custId) {
        Account account = accountRepo.findByCustId(custId)
                .orElseThrow(() -> new RuntimeException("Account with given customer id not found. Please check the details."));
        accountRepo.delete(account);
    }

    private void validateCustomerDetails(Integer custId) {

        Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customer/" + custId, Customer.class);

        if (customer == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer with given id not found. Please check the details.");
        }



    }
}
