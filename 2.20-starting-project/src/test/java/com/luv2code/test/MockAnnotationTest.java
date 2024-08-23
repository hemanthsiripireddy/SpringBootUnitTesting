package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

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

    }

    @Test
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(grades.getMathGradeResults()))
                .thenReturn(100.00);
        assertEquals(100, applicationDao.addGradeResultsForSingleClass(grades.getMathGradeResults()));
        verify(applicationDao).addGradeResultsForSingleClass(grades.getMathGradeResults());
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(grades.getMathGradeResults());
    }

    @Test
    public void assertEqualsTestFindGpa(){
        when(applicationDao.findGradePointAverage(grades.getMathGradeResults()))
                .thenReturn(87.3);
        assertEquals(87.3,applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));
    }
    @Test
    public void testNotNull(){
        when(applicationDao.checkNull(grades.getMathGradeResults()))
                .thenReturn(false);

        assertNotNull(applicationDao.checkNull(grades.getMathGradeResults()),"object should not be null");
    }

    @Test
    public void throwRunTimeError(){
        CollegeStudent nullStudent=(CollegeStudent)applicationContext.getBean("collegeStudent");
        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);
        assertThrows(RuntimeException.class,()->{
            applicationService.checkNull(nullStudent);
        });
        verify(applicationDao,times(1)).checkNull(nullStudent);
    }


    @Test
    @DisplayName("Multiple Stubbing")
    public void stubbingConsecutiveCalls(){
        CollegeStudent nullStudent=(CollegeStudent)applicationContext.getBean("collegeStudent");
        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                        .thenReturn("do not throw exception second time");
        assertThrows(RuntimeException.class,()->{
            applicationService.checkNull(nullStudent);
        });
        assertEquals("do not throw exception second time",applicationDao.checkNull(nullStudent));
        verify(applicationDao,times(2)).checkNull(nullStudent);
    }

}
