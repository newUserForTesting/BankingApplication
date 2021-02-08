package com.banking.application;

import com.banking.application.dao.TransactionDao;
import com.banking.application.dao.UserDao;
import com.banking.application.entity.Transaction;
import com.banking.application.entity.User;
import com.banking.application.services.BankingApplicationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    BankingApplicationService bankingApplicationService;

    @Autowired
    UserDao userDao;

    @Autowired
    TransactionDao transactionDao;

    User sender;
    User receiver;

    @Before
    public void setup() {
        sender = new User();
        Random r = new Random();
        sender.setId(r.nextInt());
        sender.setFirstName("sender");
        sender.setLastName("sender");
        sender.setEmail("sender@gmail.com");
        sender.setBalance(4000);
        receiver = new User();
        receiver.setId(r.nextInt());
        receiver.setFirstName("receiver");
        receiver.setLastName("receiver");
        receiver.setEmail("receiver@gmail.com");
        receiver.setBalance(1000);
        userDao.save(sender);
        userDao.save(receiver);
        sender = bankingApplicationService.getUserByEmail(sender.getEmail());
        receiver = bankingApplicationService.getUserByEmail(receiver.getEmail());
    }

    @After
    public void cleanup() {
        sender = bankingApplicationService.getUserByEmail(sender.getEmail());
        receiver = bankingApplicationService.getUserByEmail(receiver.getEmail());
        userDao.delete(sender);
        userDao.delete(receiver);
    }

    @Test
    public void getBalanceTest() {
        double balance = bankingApplicationService.getBalance(sender);
        Assert.assertTrue(4000 == balance);
    }

    @Test
    public void updateBalanceTest() {
        double newAmount = 5000;
        double balance = bankingApplicationService.updateBalance(sender, newAmount);
        double userBalance = bankingApplicationService.getBalance(sender);
        Assert.assertTrue(newAmount == userBalance);
        Assert.assertTrue(newAmount == balance);
    }

    @Test
    public void transferTest() {
        double trfAmount = 2000;
        boolean status = bankingApplicationService.transfer(sender, receiver, trfAmount);
        double sBalance = bankingApplicationService.getBalance(sender);
        double rBalance = bankingApplicationService.getBalance(receiver);
        Assert.assertTrue(status);
        Assert.assertTrue(4000 - trfAmount == sBalance);
        Assert.assertTrue(1000 + trfAmount == rBalance);
    }

    @Test
    public void invalidTransferTest() {
        double trfAmount = 10000;
        boolean status = bankingApplicationService.transfer(sender, receiver, trfAmount);
        double sBalance = bankingApplicationService.getBalance(sender);
        double rBalance = bankingApplicationService.getBalance(receiver);
        Assert.assertFalse(status);
        Assert.assertTrue(4000 == sBalance);
        Assert.assertTrue(1000 == rBalance);
    }

    @Test
    public void generateMiniStatementTest() {
        Collection<Transaction> transactions= bankingApplicationService.generateMiniStatement(sender);
        Assert.assertTrue(transactions.isEmpty());
    }

    @Test
    public void generateDetailedStatementTest() {
        Collection<Transaction> transactions= bankingApplicationService.generateDetailedStatement(sender, new Date(), new Date());
        Assert.assertTrue(transactions.isEmpty());
    }

}
