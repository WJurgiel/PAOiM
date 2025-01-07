package org.example.teachersspringboot.Repositories;

import org.example.teachersspringboot.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByGroupIDId(Long groupID);
    long countByGroupIDId(int groupId);
    List<Teacher> findAllByGroupIDId(Long groupId);
}
