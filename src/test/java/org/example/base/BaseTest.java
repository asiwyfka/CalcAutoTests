package org.example.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class BaseTest {

    @BeforeClass
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "maximize";
        Configuration.timeout = 5000;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }

    @AfterMethod
    public void saveScreenshotIfFailed(ITestResult result) {
        if (!result.isSuccess()) {
            try {
                File screenshot = Screenshots.takeScreenShotAsFile();
                if (screenshot != null && screenshot.exists()) {

                    // Создаем папку
                    var dir = Path.of("build", "screenshots");
                    createDirectories(dir);

                    // Имя метода
                    var testName = result.getName();

                    // Параметры теста
                    var params = result.getParameters();
                    var paramsPart = "";
                    if (params != null && params.length > 0) {
                        paramsPart = stream(params)
                                .map(Object::toString)
                                .map(string -> string.replaceAll("[\\\\/:*?\"<>|]", "_")) // безопасные символы для Windows
                                .collect(joining(","));
                        paramsPart = "-" + paramsPart;
                    }

                    // Текущая дата и время
                    var dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

                    // Полное имя файла
                    var fileName = testName + paramsPart + "-" + dateTime + ".png";

                    // Копируем скриншот
                    copy(screenshot.toPath(), dir.resolve(fileName));

                    System.out.println("Скриншот сохранён: " + dir.resolve(fileName).toAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }
}