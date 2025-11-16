package org.example.tests;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.example.base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.example.data.TestData.getDataByGroup;
import static org.testng.Assert.assertEquals;

@Listeners({AllureTestNg.class})
public class CalculatorTests extends BaseTest {

    @DataProvider(name = "smokeData")
    public Object[][] smokeData() {
        return getDataByGroup("Smoke");
    }

    @DataProvider(name = "regressionData")
    public Object[][] regressionData() {
        return getDataByGroup("Regression");
    }

    @Test(dataProvider = "smokeData", groups = {"Smoke"})
    @Description("Smoke тесты калькулятора")
    public void smokeTest(String input, String expected, String group, String description) {
        calculator.press(input);
        assertEquals(calculator.getResult(), expected, description);
    }

    @Test(dataProvider = "regressionData", groups = {"Regression"}, dependsOnGroups = {"Smoke"})
    @Description("Regression тесты калькулятора")
    public void regressionTest(String input, String expected, String group, String description) {
        calculator.press(input);
        assertEquals(calculator.getResult(), expected, description);
    }
}