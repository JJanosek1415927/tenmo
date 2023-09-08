package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {


    private JdbcTemplate jdbcTemplate;


    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalanceByUsername(String username) {
        double balance = 0.0;
        String sql = "SELECT account.balance FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE tenmo_user.username = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                balance = results.getDouble("balance");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return balance;
    }

    @Override
    public void updateBalance(double balance, String username) {

        String sql = "UPDATE account " +
                "SET balance = ? " +
                "WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?);";
        try{
        int numberOfRows = jdbcTemplate.update(sql, balance, username);
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }


    @Override
    public Transfer createTransfer(TransferDTO transferDTO, String username) {

        Transfer transfer = new Transfer(transferDTO.getTransferAmount(), username, transferDTO.getTo());
        String sql = "INSERT INTO transfer (transfer_amount, transfer_from, transfer_to) " +
                "VALUES (?, ?, ?) RETURNING transfer_id;";
        try {
            Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class,
                    transferDTO.getTransferAmount(), username, transferDTO.getTo());

            transfer.setTransferId(newTransferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfers(String nickname) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_amount, transfer_from, transfer_to FROM transfer WHERE transfer_from = ? OR transfer_to = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nickname, nickname);
            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfers;
    }



    @Override
    public Transfer getTransferByTransferId(int id, String username) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_amount, transfer_from, transfer_to FROM transfer WHERE transfer_id = ? AND (transfer_from = ? OR transfer_to = ?);";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, username, username);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferAmount(rowSet.getDouble("transfer_amount"));
        transfer.setFrom(rowSet.getString("transfer_from"));
        transfer.setTo(rowSet.getString("transfer_to"));
        return transfer;
    }

}



