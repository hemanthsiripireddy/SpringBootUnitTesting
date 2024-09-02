package com.siripireddy.springmvc.repository;

import com.siripireddy.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

import javax.sql.rowset.CachedRowSet;

public interface ScienceGradeDao extends CrudRepository<ScienceGrade,Integer> {


    public Iterable<ScienceGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
