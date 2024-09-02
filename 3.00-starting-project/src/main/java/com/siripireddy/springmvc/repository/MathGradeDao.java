package com.siripireddy.springmvc.repository;

import com.siripireddy.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradeDao extends CrudRepository<MathGrade,Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
