package com.siripireddy.springmvc.repository;

import com.siripireddy.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;

public interface HistoryGradeDao extends CrudRepository<HistoryGrade,Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
