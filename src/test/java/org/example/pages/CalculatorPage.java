package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.Character.isDigit;

public class CalculatorPage {

    private SelenideElement resultField = $(".calculator__display");

    @Step("Нажимаем кнопки: {input}")
    public CalculatorPage press(String input) {
        for (var symbol : input.toCharArray()) {
            switch (symbol) {
                case 'C':
                    $(".calculator__clear").click();
                    break;
                case '=':
                    $(".calculator__key--equal").click();
                    break;
                case '←':
                    $(".calculator__backspace").click();
                    break;
                case '²':
                    $(".calculator__power").click();
                    break;
                default:
                    if (isDigit(symbol) || "+-*/.".indexOf(symbol) >= 0) {
                        $("button[value='" + symbol + "']").click();
                    }
                    break;
            }
        }
        return this;
    }

    @Step("Получаем результат")
    public String getResult() {
        return resultField.getValue();
    }
}