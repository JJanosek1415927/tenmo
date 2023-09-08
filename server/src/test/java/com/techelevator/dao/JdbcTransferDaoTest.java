package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferDaoTest extends BaseDaoTests {
    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getBalanceByUsername_returns_balance_based_on_username(){
        Assert.assertEquals(5000, sut.getBalanceByUsername("bob"), 0.0);
        Assert.assertEquals(1000, sut.getBalanceByUsername("user"), 0.0);
    }

    @Test
    public void updateBalance_has_expected_value_when_retrieved(){
        sut.updateBalance(1, "bob");
        Assert.assertEquals(1, sut.getBalanceByUsername("bob"), 0.0);
    }

    @Test
    public void createTransfer_has_expected_values_when_retrieved(){
        Transfer expectedTransfer = new Transfer();
        expectedTransfer.setTransferId(3001);
        expectedTransfer.setTransferAmount(100);
        expectedTransfer.setFrom("bob");
        expectedTransfer.setTo("user");

        Transfer createdTransfer = sut.createTransfer(new TransferDTO(100, "user"), "bob");
        assertTransferMatch(expectedTransfer, createdTransfer);
    }

    @Test
    public void getAllTransfers_has_expected_values_when_retrieved(){

        Assert.assertEquals(2, sut.getAllTransfers("bob").size());

    }

    @Test
    public void getTransferByTransferId_has_expected_values_when_retrieved(){
        Transfer expectedTransfer = new Transfer();
        expectedTransfer.setTransferId(3003);
        expectedTransfer.setTransferAmount(150);
        expectedTransfer.setFrom("bob");
        expectedTransfer.setTo("user");

        Transfer actualTransfer = sut.getTransferByTransferId(3003, "bob");
        assertTransferMatch(expectedTransfer, actualTransfer);
    }

    private void assertTransferMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferAmount(), actual.getTransferAmount(), 0.0);
        Assert.assertEquals(expected.getFrom(), actual.getFrom());
        Assert.assertEquals(expected.getTo(), actual.getTo());
    }

}
