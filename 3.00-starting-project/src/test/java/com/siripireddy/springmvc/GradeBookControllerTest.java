package com.siripireddy.springmvc;

import com.siripireddy.springmvc.models.CollegeStudent;
import com.siripireddy.springmvc.models.GradebookCollegeStudent;
import com.siripireddy.springmvc.models.MathGrade;
import com.siripireddy.springmvc.repository.MathGradeDao;
import com.siripireddy.springmvc.repository.StudentDao;
import com.siripireddy.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class GradeBookControllerTest {

    private static MockHttpServletRequest request;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private StudentAndGradeService studentCreateServiceMock;
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private MathGradeDao mathGradeDao;

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

    @BeforeAll
    public static void setUP() {
        request = new MockHttpServletRequest();
        request.setParameter("firstName", "suresh");
        request.setParameter("lastName", "dharma");
        request.setParameter("emailAddress", "suresh_dharma@gmail.com");


    }

    @BeforeEach
    public void beforeEach() {
        jdbcTemplate.execute(sqlCreateStudent);
        jdbcTemplate.execute(sqlCreateMathGrade);
        jdbcTemplate.execute(sqlCreateScienceGrade);
        jdbcTemplate.execute(sqlCreateHistoryGrade);

    }

    @Test
    public void getStudentHTTPRequest() throws Exception {
        CollegeStudent collegeStudent1 = new CollegeStudent("hemanth", "siripi",
                "hem_siripi@gmail.com");
        CollegeStudent collegeStudent2 = new CollegeStudent("sai", "siripi",
                "sai_siripi@gmail.com");
        List<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(collegeStudent1, collegeStudent2));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);
        assertIterableEquals(collegeStudents, studentCreateServiceMock.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).
                andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "index");

    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        CollegeStudent collegeStudent1 = new CollegeStudent("hemanth", "siripi",
                "hem_siripi@gmail.com");
        List<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(collegeStudent1));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);
        assertIterableEquals(collegeStudents, studentCreateServiceMock.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstName", request.getParameterValues("firstName"))
                        .param("lastName", request.getParameterValues("lastName"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        CollegeStudent collegeStudent = studentDao.findByEmailAddress("suresh_dharma@gmail.com");
        assertNotNull(collegeStudent, "should not be null");


    }

    @Test
    public void deleteStudentHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/delete/{id}", 1))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        assertFalse(studentDao.findById(1).isPresent());


    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception {


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/delete/{id}", 0))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");


    }

    @Test
    public void studentInformationHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");

    }

    @Test
    public void studentInformationHttpStudentDoesNotExitRequest() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");


    }

    @Test
    public void createValidGradeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        GradebookCollegeStudent student = studentService.studentInformation(1);
        assertEquals(1,student.getStudentGrades().getMathGradeResults().size());

        MvcResult mvcResult = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85")
                        .param("gradeType", "math")
                        .param("studentId", "1")
                )

                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        student =studentService.studentInformation(1);
        assertEquals(2,student.getStudentGrades().getMathGradeResults().size());

    }

    @Test
    public void createValidGradeHttpRequestStudentDoesNotExist() throws Exception{
        assertFalse(studentDao.findById(0).isPresent());
        MvcResult mvcResult = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85")
                        .param("gradeType", "math")
                        .param("studentId", "0")
                )

                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");

    }

    @Test
    public void createGradeHttpRequestGradeTypeDoesNotExist() throws Exception{
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85")
                        .param("gradeType", "literature")
                        .param("studentId", "1")
                )

                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    public void deleteAValidGradeHttpRequest() throws Exception {
        Optional<MathGrade>mathGrade=mathGradeDao.findById(1);
        assertTrue(mathGrade.isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","1","math"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        mathGrade=mathGradeDao.findById(1);
        assertFalse(mathGrade.isPresent());


    }

    @Test
    public void deleteGradeHttpRequestIdDoesNotExit() throws Exception{
        assertFalse(mathGradeDao.findById(2).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","2","math"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    public void deleteInvalidGradeHttpRequest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/grades/{id}/{gradeType}","1","literature"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }



    @AfterEach
    public void cleanUp() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

}
