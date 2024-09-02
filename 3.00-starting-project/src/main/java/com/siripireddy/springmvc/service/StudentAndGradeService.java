package com.siripireddy.springmvc.service;

import com.siripireddy.springmvc.models.*;
import com.siripireddy.springmvc.repository.HistoryGradeDao;
import com.siripireddy.springmvc.repository.MathGradeDao;
import com.siripireddy.springmvc.repository.ScienceGradeDao;
import com.siripireddy.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private MathGrade mathGrade;
    @Autowired
    private ScienceGrade scienceGrade;
    @Autowired
    private HistoryGrade historyGrade;
    @Autowired
    private StudentGrades studentGrades;
    @Autowired
    private MathGradeDao mathGradeDao;
    @Autowired
    private ScienceGradeDao scienceGradeDao;
    @Autowired
    private HistoryGradeDao historyGradeDao;
    public void createStudent(String firstName,String lastName,String mail){
        CollegeStudent collegeStudent=new CollegeStudent(firstName,lastName,mail);

        studentDao.save(collegeStudent);
    }

    public boolean checkIfStudentIsNotNull(int id){

        Optional<CollegeStudent> collegeStudent=studentDao.findById(id);
        if(collegeStudent.isPresent())
            return true;
        return false;

    }

    public void deleteStudent(int id) {

        if(checkIfStudentIsNotNull(id)){
            studentDao.deleteById(id);

            mathGradeDao.deleteByStudentId(id);
            scienceGradeDao.deleteByStudentId(id);
            historyGradeDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {

        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int id, String type) {

        if(!checkIfStudentIsNotNull(id)){
            return false;
        }
        if(grade>=0&&grade<=100){
            if(type.equals("math")){
                mathGrade.setStudentId(id);
                mathGrade.setGrade(grade);
                mathGrade.setId(0);
                mathGradeDao.save(mathGrade);
                return true;


            }
            else  if(type.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(id);
                scienceGradeDao.save(scienceGrade);
                return true;
            }
            else  if(type.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(id);
                historyGradeDao.save(historyGrade);
                return true;
            }





        }

        return false;
    }

    public int deleteGrade(int id, String type) {

        int studentId=0;

        if(type.equals("math")){
            Optional<MathGrade> mathGrade1=mathGradeDao.findById(id);

            if(mathGrade1.isPresent()){
              studentId=  mathGrade1.get().getStudentId();
              mathGradeDao.deleteById(id);
            }
        }else if(type.equals("science")){
            Optional<ScienceGrade> scienceGrade1=scienceGradeDao.findById(id);

            if(scienceGrade1.isPresent()){
                studentId=  scienceGrade1.get().getStudentId();
                scienceGradeDao.deleteById(id);
            }
        } else if(type.equals("history")){
            Optional<HistoryGrade> historyGrade1=historyGradeDao.findById(id);

            if(historyGrade1.isPresent()){
                studentId=  historyGrade1.get().getStudentId();
                historyGradeDao.deleteById(id);
            }
        }

        return studentId;


    }

    public GradebookCollegeStudent studentInformation(int id) {

        if(!checkIfStudentIsNotNull(id)) return null;
        Optional<CollegeStudent>collegeStudent=studentDao.findById(id);
        Iterable<MathGrade> mathGrades=mathGradeDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades=scienceGradeDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades=historyGradeDao.findGradeByStudentId(id);

        List<Grade>mathGradeList=new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);
        List<Grade>scienceGradeList=new ArrayList<>();
        scienceGrades.forEach(scienceGradeList::add);
        List<Grade>historyGradeList=new ArrayList<>();
        historyGrades.forEach(historyGradeList::add);
        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);
        GradebookCollegeStudent gradebookCollegeStudent=new GradebookCollegeStudent(collegeStudent.get().getId(),collegeStudent.get().getFirstname(),
                collegeStudent.get().getLastname(),collegeStudent.get().getEmailAddress(),studentGrades);
        return gradebookCollegeStudent;
    }

    public void configureStudentInformationMode(int id, Model m){
        GradebookCollegeStudent gradebookCollegeStudent = studentInformation(id);
        m.addAttribute("student", gradebookCollegeStudent);

        if (gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", gradebookCollegeStudent.getStudentGrades().findGradePointAverage(
                    gradebookCollegeStudent.getStudentGrades().getMathGradeResults()
            ));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }
        if (gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", gradebookCollegeStudent.getStudentGrades().findGradePointAverage(
                    gradebookCollegeStudent.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }
        if (gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", gradebookCollegeStudent.getStudentGrades().findGradePointAverage(
                    gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }
    }
}
