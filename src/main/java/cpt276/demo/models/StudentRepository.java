package cpt276.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface StudentRepository extends JpaRepository<Students,Integer>  {
    List<Students> findByName(String name);
    List<Students> findByNameAndGpa(String name, double gpa);
    void deleteByName(String name);

}
