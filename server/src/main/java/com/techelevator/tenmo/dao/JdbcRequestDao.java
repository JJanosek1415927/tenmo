package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.Status;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRequestDao implements RequestDao {


    private JdbcTemplate jdbcTemplate;


    public JdbcRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Request createRequest(Request request) {
        String sql = "INSERT INTO transfer (transfer_amount, transfer_from, transfer_to) VALUES (?, ?, ?) RETURNING transfer_id;";

        Integer requestId;
        try {
            requestId = jdbcTemplate.queryForObject(sql, Integer.class, request.getRequestAmount(), request.getRequestFrom(),
                    request.getRequestTo());
            request.setRequestId(requestId);
            String sql2 = "INSERT INTO status (transfer_id, status_desc) VALUES (?, 'Pending');";
            jdbcTemplate.update(sql2, requestId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return request;
    }


    @Override
    public List<Request> requestList(String username) {
        List<Request> request = new ArrayList<>();
        String sql = "SELECT transfer.transfer_id, transfer_amount, transfer_from, transfer_to " +
                "FROM transfer JOIN status ON transfer.transfer_id = status.transfer_id " +
                "WHERE status_desc = 'Pending' AND transfer_to = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            while (results.next()) {
                request.add(mapRowToRequest(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return request;
    }

    @Override
    public Request getRequestByRequestId(int id, String username) {
        Request request = null;
        String sql = "SELECT transfer_id, transfer_amount, transfer_from, transfer_to FROM transfer WHERE transfer_id = ? AND (transfer_from = ? OR transfer_to = ?);";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, username, username);
            if (results.next()) {
                request = mapRowToRequest(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return request;
    }



    private Request mapRowToRequest(SqlRowSet rowSet) {
        Request request = new Request();
        request.setRequestId(rowSet.getInt("transfer_id"));
        request.setRequestAmount(rowSet.getDouble("transfer_amount"));
        request.setRequestFrom(rowSet.getString("transfer_from"));
        request.setRequestTo(rowSet.getString("transfer_to"));
        return request;
    }


}
