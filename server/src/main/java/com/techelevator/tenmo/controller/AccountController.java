package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/account")
public class AccountController {
    private AccountDao accountDao;


    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public AccountDTO showMyBalance(Principal principal){
        return this.accountDao.getUserAndBalance(principal.getName());
    }


    @RequestMapping(path = "/recipients", method = RequestMethod.GET)
    public List<UserDTO> showUsers(Principal principal){
        List<UserDTO> users = new ArrayList<>();
        for (UserDTO user : this.accountDao.getUsers()) {
            if (user.getUsername().equals(principal.getName())) {
                continue;
            }
            users.add(user);
        }
        return users;
    }

}
