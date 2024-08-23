package com.siripireddy.springmvc;

import com.siripireddy.springmvc.models.CollegeStudent;
import com.siripireddy.springmvc.repository.StudentDao;
import com.siripireddy.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class StudentAndGradeServiceTest {
    @Autowired
    private StudentAndGradeService studentService;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createStudentService(){
        studentService.createStudent("hemanth","siripireddy","siripireddy@gmail.com");
       CollegeStudent collegeStudent=studentDao.findByEmailAddress("siripireddy@gmail.com");
       assertEquals("siripireddy@gmail.com",collegeStudent.getEmailAddress(),"find by Email");
    }

    @BeforeEach
    public void setUp(){
        jdbcTemplate.execute("insert into student(id,firstname,lastname,email_address) " +
                "values (1,'hemanth','siripireddy','siripireddy@gmail.com')");

    }

    @Test
    public void isStudentNotNullCheck(){
        assertTrue(studentService.checkIfStudentIsNotNull(1));
        assertFalse(studentService.checkIfStudentIsNotNull(0));
    }

    @Test
    public void deleteStudentService(){
            Optional<CollegeStudent> student=studentDao.findById(1);
        assertTrue(student.isPresent(),"should return true");
        studentService.deleteStudent(1);
        student=studentDao.findById(1);
        assertFalse(student.isPresent(),"should return false");
    }
    @AfterEach
    public void cleanUp(){
        jdbcTemplate.execute("delete from student");
    }
@Sql("/insertData.sql")
    @Test
    public void getGradeBookService(){
        Iterable<CollegeStudent> collegeStudents=studentService.getGradeBook();
        List<CollegeStudent> list=new ArrayList<>();
        for(CollegeStudent collegeStudent:collegeStudents){
            list.add(collegeStudent);
        }
        assertEquals(5,list.size());
    }
}
