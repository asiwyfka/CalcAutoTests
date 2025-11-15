package org.example.data;

import org.testng.annotations.DataProvider;

import static java.util.Arrays.stream;

public class TestData {

    @DataProvider(name = "calculatorData")
    public static Object[][] calculatorData() {
        return new Object[][]{
                {"2+3=", "5", "Smoke", "Сложение"},
                {"7-4=", "3", "Smoke", "Вычитание"},
                {"6*5=", "30", "Smoke", "Умножение"},
                {"10/2=", "5", "Smoke", "Деление"},
                {"10/0=", "Infinity", "Regression", "Деление на ноль"},
                {"-5/0=", "-Infinity", "Regression", "Деление на ноль негативного числа"},
                {"123C", "0", "Regression", "Очистка поля"},
                {"2+3*4=", "14", "Regression", "Последовательные операции"},
                {"2.5+3.1=", "5.6", "Regression", "Дробные числа"},
                {"9999999*9999999=", "99999980000001", "Regression", "Большие числа"},
                {"-2*3=", "-6", "Regression", "Отрицательные числа"},
                {"0.5+0.4=", "0.9", "Regression", "Десятичные операции"},
                {"4x²=", "16", "Regression", "Возведение в квадрат"},
                {"123←=", "12", "Regression", "Удаление последнего символа"}
        };
    }

    public static Object[][] getDataByGroup(String group) {
        return stream(calculatorData())
                .filter(row -> row[2].equals(group))
                .toArray(Object[][]::new);
    }
}