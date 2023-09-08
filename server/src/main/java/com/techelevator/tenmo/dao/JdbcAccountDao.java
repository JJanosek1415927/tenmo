package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;


    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AccountDTO getUserAndBalance(String username) {
        String sql = "SELECT tenmo_user.username, account.balance FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE tenmo_user.username = ?;";
        AccountDTO accountDTO;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            accountDTO = new AccountDTO();
            if (result.next()) {
                accountDTO.setUsername(result.getString("username"));
                accountDTO.setBalance(result.getDouble("balance"));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accountDTO;
    }


    @Override
    public List<UserDTO> getUsers() {
        String sql = "SELECT username FROM tenmo_user;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        List<UserDTO> users = new ArrayList<>();
        try {
            while (result.next()) {
                UserDTO user = new UserDTO();
                user.setUsername(result.getString("username"));
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }
}







