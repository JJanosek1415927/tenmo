package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.RequestDao;
import com.techelevator.tenmo.dao.StatusDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.Status;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserDTO;
import jdk.net.SocketFlow;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class RequestController {

    private TransferDao transferDao;
    private RequestDao requestDao;
    private AccountDao accountDao;

    private StatusDao statusDao;


    public RequestController(TransferDao transferDao, RequestDao requestDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.requestDao = requestDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public Request request(@RequestBody Request request, Principal principal) {

        String senderUsername = principal.getName();
        String receiverUsername = request.getRequestTo();
        double requestAmount = request.getRequestAmount();

        if (requestAmount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Transfer Amount.");
        } else if (receiverUsername.equals(senderUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User.");
        }

        for (UserDTO user : this.accountDao.getUsers()) {
            if (user.getUsername().equals(receiverUsername) && !receiverUsername.equals(senderUsername)) {

                return requestDao.createRequest(request);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User.");
    }


    @RequestMapping(path = "/pending", method = RequestMethod.GET)
    public List<Request> viewPendingTransactions(Principal principal) {

        return requestDao.requestList(principal.getName());
    }


    @RequestMapping(path = "/pending/{id}", method = RequestMethod.GET)
    public Request getRequestByRequestId(@PathVariable int id, Principal principal) {

        return requestDao.getRequestByRequestId(id, principal.getName());
    }



}




