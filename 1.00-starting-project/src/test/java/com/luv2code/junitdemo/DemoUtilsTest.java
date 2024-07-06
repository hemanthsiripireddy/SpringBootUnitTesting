package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {
    DemoUtils demoUtils;


    @Test
    //@DisplayName("Equals And NotEquals")
    public void test_Equals_And_NotEquals() {
        // DemoUtils demoUtils = new DemoUtils();
        System.out.println("inside Test: testEqualsAndNotEquals");


        assertEquals(6, demoUtils.add(5, 1), "5 + 1 must be 6");
        assertNotEquals(9, demoUtils.add(5, 1), "5 + 1 must not be 9");
    }

    @Test
    // @DisplayName("Null And notNull")
    public void test_Null_And_NotNull() {

        // DemoUtils demoUtils = new DemoUtils();
        System.out.println("inside Test: testNullAndNotNull");

        String str1 = null;
        String str2 = "hello";
        assertNull(demoUtils.checkNull(str1), "str1 must be null");
        assertNotNull(demoUtils.checkNull(str2), "str2 must not be  null");

    }

    @Test
    @DisplayName("test Same And NotSame")
    public void testSameAndNotSame() {
        String str = "luv2code";
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Objects should be same");
        assertNotSame(demoUtils.getAcademy(), str, "Objects should not be same");

    }

    @Test
    @DisplayName(" True or False")
    @Order(1)
    public void testTrueOrFalse() {

        int gradOne = 10;
        int gradeTwo = 5;
        assertTrue(demoUtils.isGreater(gradOne, gradeTwo), "grandOne should be greater than gradeTwo");
        assertFalse(demoUtils.isGreater(gradeTwo, gradOne), "grandOne should be greater than gradeTwo");

    }

    @Test
    @DisplayName("Array Equals")
    public void testArrayEquals() {
        class Student {
            String name;
            int rolno;

            public Student(String name, int rolno) {
                this.name = name;
                this.rolno = rolno;
            }
        }
        String[] str = {"A", "B", "C"};
        assertArrayEquals(str, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be the same");
        Student s1 = new Student("sai", 1);
        Student s2 = new Student("hemanth", 2);
        Student[] arr1 = {s1, s2};
        Student[] arr2 = {s1, s2};
        assertArrayEquals(arr1, arr2);
    }

    @Test
    @DisplayName("Iterable Equals")
    public void testIterableEquals() {

        List<String> list = List.of("luv", "2", "code");
        assertIterableEquals(list, demoUtils.getAcademyInList());


    }

    @Test
    @DisplayName("Lines Match")
    public void linesMatch() {

        List<String> list = List.of("luv", "2", "code");
        assertLinesMatch(list, demoUtils.getAcademyInList());

    }

    @Test
    @DisplayName("Throws and Does Not Throw")

    public void testThrowAndDoesNotThrowException() {

        assertThrows(Exception.class, () -> {
            demoUtils.throwException(-1);
        }, "Should throw Exception");
        assertDoesNotThrow(() -> {
            demoUtils.throwException(4);
        }, "Should not throw Exception");


    }
    @Test
    @DisplayName("Timeout")
    public void timeOut(){
        assertTimeoutPreemptively(Duration.ofSeconds(3),()->{demoUtils.checkTimeout();});

    }
    @Test
    @DisplayName("Multiply")
    public void testMultiply(){
        assertEquals(12,demoUtils.multiply(4,3));
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("@BeforeAll runs before all of the test methods");
        System.out.println();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("@AfterAll runs after all of the test methods");


    }


    @BeforeEach
    public void setup() {
        System.out.println("@BeforeEach runs before every test method");
        demoUtils = new DemoUtils();
    }

    @AfterEach
    public void destroy() {
        System.out.println("@AfterEach runs after every test method");
        System.out.println();
    }

}
