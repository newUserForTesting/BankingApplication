package com.banking.application.services.impl;

import com.banking.application.dao.TransactionDao;
import com.banking.application.dao.UserDao;
import com.banking.application.entity.Transaction;
import com.banking.application.entity.User;
import com.banking.application.services.BankingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Service APIs
 *
 * @author Saurabh Kumar
 */
@Component
public class BankingApplicationServiceImpl implements BankingApplicationService {

    @Autowired
    UserDao userDao;

    @Autowired
    TransactionDao transactionDao;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userDao.findAll().forEach(student -> users.add(student));
        return users;
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).get(0);
    }

    @Override
    public double getBalance(User user) {
        return user.getBalance();
    }

    @Override
    @Transactional
    public double updateBalance(User user, double amount) {
        Transaction transaction =  new Transaction();
        transaction.setStartingBalance(user.getBalance());
        transaction.setClosingBalance(amount);
        transaction.setTransactionDate(new Date());
        transaction.setUserId(user.getId());
        user.setBalance(amount);
        transactionDao.save(transaction);
        userDao.save(user);
        return user.getBalance();
    }

    @Override
    @Transactional
    public boolean transfer(User sender, User receiver, double amount) {

        if(sender.getBalance() < amount) {
            return false;
        }

        Transaction transactionSender =  new Transaction();
        transactionSender.setStartingBalance(sender.getBalance());
        transactionSender.setClosingBalance(sender.getBalance() - amount);
        transactionSender.setTransactionDate(new Date());
        transactionSender.setUserId(sender.getId());
        sender.setBalance(sender.getBalance() - amount);

        Transaction transactionReciever =  new Transaction();
        transactionReciever.setStartingBalance(receiver.getBalance());
        transactionReciever.setClosingBalance(receiver.getBalance() + amount);
        transactionReciever.setTransactionDate(new Date());
        transactionReciever.setUserId(receiver.getId());
        receiver.setBalance(receiver.getBalance() + amount);

        transactionDao.save(transactionSender);
        transactionDao.save(transactionReciever);
        userDao.save(sender);
        userDao.save(receiver);
        return true;
    }

    @Override
    public Collection<Transaction> generateMiniStatement(User user) {
        return transactionDao.findLast10Transactions(user.getId());
    }

    @Override
    public Collection<Transaction> generateDetailedStatement(User user, Date start, Date end) {
        String startDate=new SimpleDateFormat("yyyy-mm-dd").format(start);
        String endDate=new SimpleDateFormat("yyyy-mm-dd").format(end);
        return transactionDao.findTransactions(user.getId(), startDate, endDate);
    }
}
