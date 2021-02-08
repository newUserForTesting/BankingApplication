package com.banking.application.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.banking.application.entity.Transaction;
import com.banking.application.entity.User;

public interface BankingApplicationService {

    public List<User> getAllUsers();

    public User getUserByEmail(String email);

    /** Get current balance in user account */
    double getBalance(User user);

    /**
     * Deposit or debit money into a user account. should return the updated
     * balance.
     */
    double updateBalance(User user, double amount);

    /**
     * Transfer balance from one user account to another. Handle all edge cases
     * gracefully and specify the expected behavior in comments.
     */
    boolean transfer(User sender, User receiver, double amount);

    /** Retrieve last 10 transactions on the user account. */
    Collection<Transaction> generateMiniStatement(User user);

    /**
     * Retrieve all transactions for the user in the specified period. Use
     * filesystem or an embedded database like SQLite/H2 for persistence.
     */
    Collection<Transaction> generateDetailedStatement(User user, Date start, Date end);
}
