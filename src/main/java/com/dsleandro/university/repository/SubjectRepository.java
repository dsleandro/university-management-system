package com.dsleandro.university.repository;

import java.util.List;

import com.dsleandro.university.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findAllByOrderByNameDesc();

    List<Subject> findAllByOrderByNameAsc();

}
