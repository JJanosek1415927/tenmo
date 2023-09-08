package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.util.List;

public interface TransferDao {

    double getBalanceByUsername(String username);
    void updateBalance(double balance, String username);
    Transfer createTransfer(TransferDTO transferDTO, String username);
    List<Transfer> getAllTransfers(String nickname);
    Transfer getTransferByTransferId(int id, String username);

}
