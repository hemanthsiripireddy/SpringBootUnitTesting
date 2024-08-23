package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CollegeStudent studentOne;
    @Autowired
    StudentGrades grades;
    // @Mock
    @MockBean
    ApplicationDao applicationDao;
    // @InjectMocks
    @Autowired
    ApplicationService applicationService;

    @BeforeEach
    void beforeEach() {
        studentOne.setFirstname("hemanth");
        studentOne.setLastname("siripireddy");
        studentOne.setStudentGrades(grades);
        studentOne.setEmailAddress("hemanth@gmail.com");
        ReflectionTestUtils.setField(studentOne,"id",1);
        ReflectionTestUtils.setField(studentOne,"studentGrades",new StudentGrades(new ArrayList<>(
                Arrays.asList(1.2,34.5)
        )));


    }

    @Test
    public void getPrivateFields(){
    assertEquals(1,ReflectionTestUtils.getField(studentOne,"id"));
    }

    @Test
    public void invokePrivateMethod(){
        assertEquals("hemanth 1",ReflectionTestUtils.invokeMethod(studentOne,"getFirstNameAndId"));
    }

}
