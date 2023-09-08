package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.Status;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface RequestDao {


    Request createRequest(Request request);
    List<Request> requestList(String username);
    Request getRequestByRequestId(int id, String username);

}


