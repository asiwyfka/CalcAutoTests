package org.example.base;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.pages.CalculatorPage;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.addAttachment;

public class BaseTest {

    protected CalculatorPage calculator;

    @BeforeClass
    public void setUpClass() {
        addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @BeforeMethod
    public void setUpTest() {
        calculator = open("/simple", CalculatorPage.class);
    }

    @AfterMethod
    public void saveScreenshotIfFailed(ITestResult result) {
        if (!result.isSuccess()) {
            addAttachment( "Скриншот при падении: " + result.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot()),
                    "png");
        }
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() {
        return Selenide.screenshot(OutputType.BYTES);
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}