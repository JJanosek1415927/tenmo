package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;

import java.util.List;

public interface AccountDao {

    AccountDTO getUserAndBalance(String username);
    List<UserDTO> getUsers();



}
