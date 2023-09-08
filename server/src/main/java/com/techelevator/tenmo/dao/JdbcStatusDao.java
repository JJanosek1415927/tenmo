package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcStatusDao implements StatusDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Status changeRequestStatus(Status status, String statusDesc) {

        String sql = "UPDATE status SET status_desc = ? WHERE transfer_id = ?";
        try {
            int numberOfRows = jdbcTemplate.update(sql, statusDesc, status.getTransferId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
return status;
    }



    private Status mapRowToStatus(SqlRowSet rowSet) {
        Status status = new Status();
        status.setStatusId(rowSet.getInt("status_id"));
        status.setTransferId(rowSet.getInt("transfer_id"));
        status.setStatusDescription(rowSet.getString("status_desc"));
        return status;
    }

}



