package sec.project.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sec.project.domain.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    @Query(value = "SELECT * FROM Signup WHERE name =:goodId", nativeQuery=true)
    ArrayList<Signup> findSignups(@Param("goodId")String id);
}
