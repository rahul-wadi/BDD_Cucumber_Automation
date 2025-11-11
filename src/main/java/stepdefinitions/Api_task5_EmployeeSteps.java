package stepdefinitions;

import Pojo.Employee;
import Utilities.RestUtils;
import config.Constants;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class Api_task5_EmployeeSteps {

    private Response response;
    private int initialCount;
    private int employeeId;
    private Employee newEmployee;

    @Given("I get the list of all employees")
    public void get_all_employees() {
        RestAssured.baseURI = Constants.BASE_URI;
        response = RestAssured.given().get(Constants.EMPLOYEES_ENDPOINT);
        RestUtils.verifyResponseDetails(response, 200);
        RestUtils.verifySuccessCode(response);
        initialCount = response.jsonPath().getList("data").size();
    }

    @When("I create a new employee with name {string}, salary {string}, and age {string}")
    public void create_employee(String name, String salary, String age) {
        newEmployee = new Employee(name, salary, age);
        response = RestAssured.given()
                .contentType("application/json")
                .body(newEmployee)
                .post(Constants.CREATE_EMPLOYEE_ENDPOINT);

        RestUtils.verifyResponseDetails(response, 200);
        RestUtils.verifySuccessCode(response);
        employeeId = response.jsonPath().getInt("data.id");
    }

    @Then("Verify employee is created and count increased by one")
    public void verify_creation_and_count() {
        Response newResponse = RestAssured.given().get(Constants.EMPLOYEES_ENDPOINT);
        RestUtils.verifyResponseDetails(newResponse, 200);
        int newCount = newResponse.jsonPath().getList("data").size();
        Assert.assertEquals(newCount, initialCount + 1, "Employee count did not increase!");
    }

    @Then("I get the details of the created employee")
    public void get_created_employee_details() {
        response = RestAssured.given().get(Constants.EMPLOYEE_ENDPOINT + employeeId);
        RestUtils.verifyResponseDetails(response, 200);
        Assert.assertEquals(response.jsonPath().getString("data.employee_name"), newEmployee.getName());
        Assert.assertEquals(response.jsonPath().getString("data.employee_salary"), newEmployee.getSalary());
        Assert.assertEquals(response.jsonPath().getString("data.employee_age"), newEmployee.getAge());
    }

    @When("I update the employee salary to {string} and age to {string}")
    public void update_employee(String newSalary, String newAge) {
        newEmployee.setSalary(newSalary);
        newEmployee.setAge(newAge);

        response = RestAssured.given()
                .contentType("application/json")
                .body(newEmployee)
                .put(Constants.UPDATE_ENDPOINT + employeeId);

        RestUtils.verifyResponseDetails(response, 200);
        RestUtils.verifySuccessCode(response);
    }

    @Then("Verify updated employee details")
    public void verify_updated_employee_details() {
        response = RestAssured.given().get(Constants.EMPLOYEE_ENDPOINT + employeeId);
        RestUtils.verifyResponseDetails(response, 200);
        Assert.assertEquals(response.jsonPath().getString("data.employee_salary"), newEmployee.getSalary());
        Assert.assertEquals(response.jsonPath().getString("data.employee_age"), newEmployee.getAge());
    }

    @When("I delete the employee")
    public void delete_employee() {
        response = RestAssured.given().delete(Constants.DELETE_ENDPOINT + employeeId);
        RestUtils.verifyResponseDetails(response, 200);
        RestUtils.verifySuccessCode(response);
    }

    @Then("Verify employee deleted and count decreased by one")
    public void verify_delete_and_count() {
        Response newResponse = RestAssured.given().get(Constants.EMPLOYEES_ENDPOINT);
        RestUtils.verifyResponseDetails(newResponse, 200);
        int finalCount = newResponse.jsonPath().getList("data").size();
        Assert.assertEquals(finalCount, initialCount, "Employee count did not decrease!");
    }
}
