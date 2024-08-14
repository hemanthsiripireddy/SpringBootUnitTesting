package com.siripireddy.test;

import com.siripireddy.component.MvcTestingExampleApplication;
import com.siripireddy.component.models.CollegeStudent;
import com.siripireddy.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {


    private static int count = 0;


    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;
    @Autowired
    private CollegeStudent student;
    @Autowired
    private StudentGrades studentGrades;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void beforeEach() {
        count++;
        System.out.println("Testing " + appInfo + " which is " + appDescription + " " +
                " Version: " + appVersion + " .Execution of Test Method " + count);
        student.setFirstname("hemanth");
        student.setLastname("Siripireddy");
        student.setEmailAddress("siripireddy@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.5, 76.50, 99.99)));
        student.setStudentGrades(studentGrades);

    }


    @Test

    public void basicTest() {

    }

    @DisplayName("add Grades Results for Single Class")
    @Test
    public void addGradeResultsForSingleClass() {
        assertEquals(361.99, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }
    @DisplayName("add Grades Results for Single Class Asset not equals")
    @Test
    public void addGradeResultsForSingleClassAssetNotEquals() {
        assertNotEquals(361, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Is Grade Greater")
    @Test
    public void isGradeGrader(){
        assertTrue(studentGrades.isGradeGreater(92,10),"failure: should be true");
    }

    @DisplayName("Is Grade Greater Assert False")
    @Test
    public void isGradeGraderAssertFalse(){
        assertFalse(studentGrades.isGradeGreater(92,100),"failure: should be False");
    }

    @DisplayName("check not null")
    @Test
    public void checkNotNull(){
        assertNotNull(studentGrades.getMathGradeResults(),"failure: should be not null");
    }

    @DisplayName("Create Student without Grades in it")
    @Test
    public void createStudentWithoutGradesInIt(){
      CollegeStudent studentTwo=context.getBean("collegeStudent",CollegeStudent.class);

      studentTwo.setFirstname("sai");
      studentTwo.setLastname("Reddy");
      studentTwo.setEmailAddress("sai@gmail.com");
      assertNotNull(studentTwo.getFirstname());
      assertNull(studentTwo.getStudentGrades());
    }

    @DisplayName("Verify Students are Prototype")
    @Test
    public void verifyStudentsArePrototype(){
        CollegeStudent studentTwo=context.getBean("collegeStudent",CollegeStudent.class);

        assertNotEquals(studentTwo,student);
    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage(){
        assertAll("Asserting all  assertEquals",
                ()->assertEquals(361.99,studentGrades.addGradeResultsForSingleClass(student.getStudentGrades()
                        .getMathGradeResults())),
                ()->assertEquals(90.5,studentGrades.findGradePointAverage(student.getStudentGrades()
                        .getMathGradeResults()))


        );
    }
}
