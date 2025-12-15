package fr.epita.calculator.tests;


import fr.epita.calculator.services.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCalculator {

    private static final Logger LOGGER = LogManager.getLogger(TestCalculator.class);


    @BeforeEach
    public void setUp() {
        LOGGER.info("setUp");
    }

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

    @AfterEach
    public void tearDown() {
        LOGGER.info("tearDown");
    }
}
