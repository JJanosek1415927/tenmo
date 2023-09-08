package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.PUT)
    public Transfer transfer(@RequestBody TransferDTO transfer, Principal principal) {

        String senderUsername = principal.getName();
        String receiverUsername = transfer.getTo();
        double senderBalance = transferDao.getBalanceByUsername(senderUsername);
        double receiverBalance = transferDao.getBalanceByUsername(receiverUsername);
        double transferAmount = transfer.getTransferAmount();

        if (transferAmount <= 0 || transferAmount > senderBalance) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Transfer Amount.");
        }
        else if (receiverUsername.equals(senderUsername)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User.");
        }

        for (UserDTO user : this.accountDao.getUsers()) {
            if(user.getUsername().equals(receiverUsername) && !receiverUsername.equals(senderUsername)){

                senderBalance = senderBalance - transferAmount;
                transferDao.updateBalance(senderBalance, senderUsername);

                receiverBalance = receiverBalance + transferAmount;
                transferDao.updateBalance(receiverBalance, receiverUsername);
                return transferDao.createTransfer(transfer, principal.getName());
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User.");
    }


    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    public List<Transfer> showMyTransfers(Principal principal) {
        List<Transfer> myTransfers = transferDao.getAllTransfers(principal.getName());
        if (myTransfers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transfers found.");
        } else {
            return myTransfers;
        }
    }


    @RequestMapping(path = "/transactions/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id, Principal principal) {

        return transferDao.getTransferByTransferId(id, principal.getName());
    }


}




