package stepdefinitions;

import com.bdd.task1.ATM;
import com.bdd.task1.Account;
import com.bdd.task1.BankAccount;
import com.bdd.task1.CashWithdrawService;
import io.cucumber.java.en.*;
import org.testng.Reporter;

import static org.junit.Assert.*;

public class BDD_task1_WithdrawCashSteps {

    public Account account;
    public ATM atm;
    public CashWithdrawService withdrawService;

    @Given("I have a balance of {int} in my account")
    public void i_have_a_balance_in_my_account(Integer balance) {
        account = new BankAccount(balance);
        atm = new ATM();
        withdrawService = new CashWithdrawService(account, atm);
    }

    @When("I request {int}")
    public void i_request(Integer amount) {
        withdrawService.withdraw(amount);
    }

    @Then("{int} should be dispensed")
    public void should_be_dispensed(Integer amount) {
        assertEquals(amount.doubleValue(), atm.getCashDispensed(), 0.0);
        Reporter.log("$" + amount + " dispensed successfully!", true);
    }
}
