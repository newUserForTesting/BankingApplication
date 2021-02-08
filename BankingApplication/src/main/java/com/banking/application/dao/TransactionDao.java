package com.banking.application.dao;

import com.banking.application.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TransactionDao extends CrudRepository<Transaction, Integer>{

    @Query(value= "SELECT * FROM transactions where user_id = ? limit 10", nativeQuery = true)
    Collection<Transaction> findLast10Transactions(Integer userId);

    @Query(value= "SELECT * FROM TRANSACTIONS where user_id = ? and transaction_date >= ? and  transaction_date <= ?", nativeQuery = true)
    Collection<Transaction> findTransactions(Integer userId, String start, String end);
}
