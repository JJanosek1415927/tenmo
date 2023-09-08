package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Status;

public interface StatusDao {

     Status changeRequestStatus(Status status, String statusDesc);

}
