package com.siripireddy.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {





    //if number is divisible by 3, then print Fizz
    @Test
    @DisplayName("Divisible by three")
    @Order(1)
    public void testForDivisibleByThree() {
        String actual = FizzBuzz.compute(3);
        String expected="Fizz";
        assertEquals(expected, actual, "should return Fizz");

    }
    //if number is divisible by 5, then print Buzz
    @Test
    @DisplayName("Divisible by Five")
    @Order(2)
    public void testForDivisibleByFive() {
        String actual = FizzBuzz.compute(5);
        String expected="Buzz";
        assertEquals(expected, actual, "should return Buzz");

    }

    //if number is divisible by 3 and 5, then print FizzBuzz
    @Test
    @DisplayName("Divisible by Three And Five")
    @Order(3)
    public void testForDivisibleByThreeAndFive() {
        String actual = FizzBuzz.compute(15);
        String expected="FizzBuzz";
        assertEquals(expected, actual, "should return FizzBuzz");

    }

    //if number is not divisible by either 3 or 5, then print the number

    @Test
    @DisplayName("Not Divisible by Three or Five")
    @Order(4)
    public void testForNotDivisibleByThreeOrFive() {
        String actual = FizzBuzz.compute(1);
        String expected="1";
        assertEquals(expected, actual, "should return 1");

    }


    @DisplayName("testing with small data file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    public void testSmallDataFile(int value,String expected) {
        String actual=FizzBuzz.compute(value);
        assertEquals(expected, actual, "should return 1");

    }


    @DisplayName("testing with medium data file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(6)
    public void testMediumDataFile(int value,String expected) {
        String actual=FizzBuzz.compute(value);
        assertEquals(expected, actual, "should return 1");

    }

    @DisplayName("testing with large data file")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(7)
    public void testLargeDataFile(int value,String expected) {
        String actual=FizzBuzz.compute(value);
        assertEquals(expected, actual, "should return 1");

    }
}
