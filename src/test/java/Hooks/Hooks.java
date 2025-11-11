package Hooks;

import Utilities.RestUtils;
import Utilities.TestBase;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void beforeScenario() {
        TestBase.setup();
    }

    @Before
    public void setup() {
        RestUtils.token = null; // reset before each scenario
    }
}
