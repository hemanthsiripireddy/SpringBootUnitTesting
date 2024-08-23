package com.siripireddy.springmvc.service;

import com.siripireddy.springmvc.models.CollegeStudent;
import com.siripireddy.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao;

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
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {

        return studentDao.findAll();
    }
}
