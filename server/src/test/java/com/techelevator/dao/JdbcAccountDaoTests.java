package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests {


    private static final AccountDTO ACCOUNT_1 = new AccountDTO("bob", 5000);
    private static final AccountDTO ACCOUNT_2 = new AccountDTO("user", 1000);


    private JdbcAccountDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getUserAndBalance_returns_user_and_balance() {
        AccountDTO testAccount1 = sut.getUserAndBalance("bob");
        AccountDTO testAccount2 = sut.getUserAndBalance("user");
        assertAccountsMatch(ACCOUNT_1, testAccount1);
        assertAccountsMatch(ACCOUNT_2, testAccount2);
    }

    @Test
    public void getUsers_returns_returns_correct_amount_of_users() {
        List<UserDTO> userDTOs = sut.getUsers();
        Assert.assertEquals(2, userDTOs.size());
    }


    private void assertAccountsMatch(AccountDTO expected, AccountDTO actual) {
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getBalance(), actual.getBalance(), 0.1);
    }


}






