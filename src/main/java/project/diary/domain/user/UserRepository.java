package project.diary.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUser_id(String user_id);


    Optional<User> findByUser_id(String user_id);

}