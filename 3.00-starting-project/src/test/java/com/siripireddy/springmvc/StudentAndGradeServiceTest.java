package com.siripireddy.springmvc;

import com.siripireddy.springmvc.models.*;
import com.siripireddy.springmvc.repository.HistoryGradeDao;
import com.siripireddy.springmvc.repository.MathGradeDao;
import com.siripireddy.springmvc.repository.ScienceGradeDao;
import com.siripireddy.springmvc.repository.StudentDao;
import com.siripireddy.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class StudentAndGradeServiceTest {
    @Autowired
    private StudentAndGradeService studentService;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Value("${sql.script.create.student}")
    private String sqlCreateStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlCreateMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlCreateScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlCreateHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @Test
    public void createStudentService() {
        studentService.createStudent("hemanth", "siripireddy", "siripireddy11@gmail.com");
        CollegeStudent collegeStudent = studentDao.findByEmailAddress("siripireddy11@gmail.com");
        assertEquals("siripireddy11@gmail.com", collegeStudent.getEmailAddress(), "find by Email");
    }

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute(sqlCreateStudent);
        jdbcTemplate.execute(sqlCreateMathGrade);
        jdbcTemplate.execute(sqlCreateScienceGrade);
        jdbcTemplate.execute(sqlCreateHistoryGrade);


    }

    @Test
    public void isStudentNotNullCheck() {
        assertTrue(studentService.checkIfStudentIsNotNull(1));
        assertFalse(studentService.checkIfStudentIsNotNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> student = studentDao.findById(1);
        Optional<MathGrade> mathGrade = mathGradeDao.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradeDao.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradeDao.findById(1);
        assertTrue(student.isPresent(), "should return true");
        assertTrue(mathGrade.isPresent(), "should return true");
        assertTrue(scienceGrade.isPresent(), "should return true");
        assertTrue(historyGrade.isPresent(), "should return true");
        studentService.deleteStudent(1);

        student = studentDao.findById(1);
        mathGrade = mathGradeDao.findById(1);
        historyGrade = historyGradeDao.findById(1);
        scienceGrade = scienceGradeDao.findById(1);
        assertFalse(student.isPresent(), "should return false");
        assertFalse(mathGrade.isPresent(), "should return false");
        assertFalse(scienceGrade.isPresent(), "should return false");
        assertFalse(historyGrade.isPresent(), "should return false");
    }

    @AfterEach
    public void cleanUp() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);


    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        List<CollegeStudent> list = new ArrayList<>();
        for (CollegeStudent collegeStudent : collegeStudents) {
            list.add(collegeStudent);
        }
        assertEquals(5, list.size());
    }


    @Test
    public void createGradeService() {
        assertTrue(studentService.createGrade(86.7, 1, "math"));
        assertTrue(studentService.createGrade(80, 1, "science"));
        assertTrue(studentService.createGrade(80, 1, "history"));

        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrads = scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1);
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2, "student should has grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrads).size() == 2, "student should has grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2, "student should has grades");

    }

    @Test
    public void createGradeServiceReturnFalse() {

        assertFalse(studentService.createGrade(786, 1, "math"));
        assertFalse(studentService.createGrade(-4, 1, "math"));
        assertFalse(studentService.createGrade(54, 2, "math"));
        assertFalse(studentService.createGrade(54, 1, "literature"));
    }

    @Test
    public void deleteGradeService() {

        assertEquals(1, studentService.deleteGrade(1, "math"));
        assertEquals(1, studentService.deleteGrade(1, "science"));
        assertEquals(1, studentService.deleteGrade(1, "history"));
    }

    @Test
    public void deleteGradeServiceReturnStudentIdZero() {
        assertEquals(0, studentService.deleteGrade(0, "math"));
        assertEquals(0, studentService.deleteGrade(1, "literature"));
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("hemanth", gradebookCollegeStudent.getFirstname());
        assertEquals("siripireddy", gradebookCollegeStudent.getLastname());
        assertEquals("siripireddy@gmail.com", gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    public void studentInformationReturnNull() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);
        assertNull(gradebookCollegeStudent);


    }
}
