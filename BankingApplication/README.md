I have exposed following rest urls for testing:

Get:
http://localhost:8080/?email=Saurabh.Kumar@gmail.com
http://localhost:8080/balance?email=Saurabh.Kumar@gmail.com
http://localhost:8080/miniStatement?email=Saurabh.Kumar@gmail.com
http://localhost:8080/detailedStatement?email=Saurabh.Kumar@gmail.com&start=2021-02-05&end=2021-02-08

Post:
http://localhost:8080/balance
email Saurabh.Kumar@gmail.com
balance 15
http://localhost:8080/transfer
senderEmail Saurabh.Kumar@gmail.com
receiverEmail Bill.Gates@gmail.com
amount 5 