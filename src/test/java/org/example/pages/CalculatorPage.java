package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.Character.isDigit;

public class CalculatorPage {

    private SelenideElement resultField = $(".calculator__display");

    @Step("Открываем калькулятор")
    public CalculatorPage openCalculator() {
        open("https://ecalc.ru/simple/");
        return this;
    }

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
                        $("button[value='" + symbol + "']").click();   // только цифры и обычные операции + . / * - больше ничего не ищем
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