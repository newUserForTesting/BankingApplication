package com.banking.application.controller;

import com.banking.application.entity.Transaction;
import com.banking.application.entity.User;
import com.banking.application.services.BankingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Application controller.
 *
 * @author Saurabh Kumar
 */
@RestController
public class ApplicationController {

	@Autowired
	BankingApplicationService bankingApplicationService;

	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public User getUsers(@RequestParam(value = "email")String email) {
		return bankingApplicationService.getUserByEmail(email);
	}

	@GetMapping("/balance")
	@ResponseStatus(value = HttpStatus.OK)
	public Double getBalance(@RequestParam(value = "email")String email) {
		User user = bankingApplicationService.getUserByEmail(email);
		return user.getBalance();
	}

	@GetMapping("/miniStatement")
	@ResponseStatus(value = HttpStatus.OK)
	public Collection<Transaction> generateMiniStatement(@RequestParam(value = "email")String email) {
		User user = bankingApplicationService.getUserByEmail(email);
		return bankingApplicationService.generateMiniStatement(user);
	}

	@GetMapping("/detailedStatement")
	@ResponseStatus(value = HttpStatus.OK)
	public Collection<Transaction> generateDetailedStatement(@RequestParam(value = "email")String email,
															 @RequestParam(value = "start")String start,
															 @RequestParam(value = "end")String end) throws ParseException {
		User user = bankingApplicationService.getUserByEmail(email);
		Date startDate=new SimpleDateFormat("yyyy-mm-dd").parse(start);
		Date endDate=new SimpleDateFormat("yyyy-mm-dd").parse(end);
		return bankingApplicationService.generateDetailedStatement(user, startDate, endDate);
	}


	@PostMapping("/balance")
	@ResponseStatus(value = HttpStatus.OK)
	public Double postBalance(@RequestParam(value = "email")String email,
							 @RequestParam(value = "balance")double balance) {
		User user = bankingApplicationService.getUserByEmail(email);
		return bankingApplicationService.updateBalance(user, balance);
	}

	@PostMapping("/transfer")
	@ResponseStatus(value = HttpStatus.OK)
	public Boolean transfer(@RequestParam(value = "senderEmail")String senderEmail,
							 @RequestParam(value = "receiverEmail")String receiverEmail,
							 @RequestParam(value = "amount")double amount) {
		User sender = bankingApplicationService.getUserByEmail(senderEmail);
		User receiver = bankingApplicationService.getUserByEmail(receiverEmail);
		return bankingApplicationService.transfer(sender, receiver, amount);
	}
}