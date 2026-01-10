package com.nayak.authorization.repository;

import com.nayak.authorization.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findBySubject(String subject);

    List<Teacher> findByHod_HodId(Long hodId);
}
