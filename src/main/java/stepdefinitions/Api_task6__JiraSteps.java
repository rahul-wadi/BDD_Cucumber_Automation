package stepdefinitions;

import Utilities.TestBase;
import Utilities.TestDataBuilder;
import io.cucumber.java.en.*;

import java.io.File;

public class Api_task6__JiraSteps {

    @Given("I create a new defect in Jira")
    public void createDefect() {
        String id = TestDataBuilder.createDefect();
        System.out.println("Defect Created: " + id);
    }

    @When("I update the created defect")
    public void updateDefect() {
        TestDataBuilder.updateDefect(TestBase.ISSUE_ID);
        System.out.println("Defect Updated: " + TestBase.ISSUE_ID);
    }

    @When("I search for the defect by ID")
    public void searchDefect() {
        TestDataBuilder.searchDefect(TestBase.ISSUE_ID);
        System.out.println("Defect Found: " + TestBase.ISSUE_ID);
    }

    @When("I add an attachment to the defect")
    public void attachToDefect() {
        File file = new File("src/test/resources/testfile.txt");
        TestDataBuilder.attachFile(TestBase.ISSUE_ID, file);
        System.out.println("Attachment added to: " + TestBase.ISSUE_ID);
    }

    @When("I create a defect with an attached document")
    public void createDefectWithAttachment() {
        String newId = TestDataBuilder.createDefect();
        File file = new File("src/test/resources/testfile.txt");
        TestDataBuilder.attachFile(newId, file);
        System.out.println("Defect created with attachment: " + newId);
    }

    @Then("I delete the created defect")
    public void deleteDefect() {
        TestDataBuilder.deleteDefect(TestBase.ISSUE_ID);
        System.out.println("Deleted defect: " + TestBase.ISSUE_ID);
    }
}
