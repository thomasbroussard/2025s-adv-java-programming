package fr.epita.calculator.tests;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCalculator {

    @Test
    void shouldDivideTwoNumbers() {
        Calculator calc = new Calculator();
        double result = calc.divide(10, 2);
        assertEquals(5.0, result, 0.001);
    }

    @Test
    void shouldThrowExceptionWhenDividingByZero() {
        Calculator calc = new Calculator();
        assertThrows(ArithmeticException.class,
                () -> calc.divide(10, 0));
    }
}
