package pawelmadela.calendar.repo;


import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pawelmadela.calendar.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {

    public User getUserByUsername(String username);
    public User getUserByEmailAddress(String email);
    public User getUserByUserId(long userId);



    @Query(
            value = "SELECT * FROM calendar.users",
            nativeQuery = true)
    public List<User> getAll();

}
