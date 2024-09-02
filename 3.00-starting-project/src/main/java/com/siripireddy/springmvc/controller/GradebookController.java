package com.siripireddy.springmvc.controller;


import com.siripireddy.springmvc.models.CollegeStudent;
import com.siripireddy.springmvc.models.Gradebook;
import com.siripireddy.springmvc.models.GradebookCollegeStudent;
import com.siripireddy.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;
    @Autowired
    private StudentAndGradeService studentAndGradeService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        m.addAttribute("students", collegeStudents);

        return "index";
    }


    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        if (!studentAndGradeService.checkIfStudentIsNotNull(id))
            return "error";
        studentAndGradeService.configureStudentInformationMode(id, m);


        return "studentInformation";
    }


    @GetMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable int id, Model model) {

        if (!studentAndGradeService.checkIfStudentIsNotNull(id)) return "error";

        studentAndGradeService.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        model.addAttribute("students", collegeStudents);

        return "index";
    }


    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent collegeStudent, Model model) {
        studentAndGradeService.createStudent(collegeStudent.getFirstname(), collegeStudent.getLastname(),
                collegeStudent.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradeBook();
        model.addAttribute("students", collegeStudents);
        return "index";

    }

    @PostMapping(value = "/grades")
    public String createGrade(@RequestParam("studentId") int studentId, @RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType, Model m) {
        if (!studentAndGradeService.checkIfStudentIsNotNull(studentId))
            return "error";
        boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);
        if (!success) {
            return "error";

        }
        studentAndGradeService.configureStudentInformationMode(studentId, m);


        return "studentInformation";


    }

    @GetMapping("/grades/{id}/{gradeType}")
    public String deleteGrade(@PathVariable int id, @PathVariable("gradeType") String gradeType, Model m) {

        int studentId =studentAndGradeService.deleteGrade(id,gradeType);
        if(studentId==0)
            return "error";
        studentAndGradeService.configureStudentInformationMode(studentId,m);


        return "studentInformation";

    }



}
