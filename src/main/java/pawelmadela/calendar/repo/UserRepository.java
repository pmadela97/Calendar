package pawelmadela.calendar.repo;


import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pawelmadela.calendar.model.User;

import java.util.List;

/**
 * Interface that extends <code>CrudRepository<T,ID></code> that provides access to persisted data in database by
 * implemented methods.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    public User getUserByUsername(String username);

    public User getUserByEmailAddress(String email);

    public User getUserByUserId(long userId);

    @Query(
            value = "SELECT * FROM calendar.users",
            nativeQuery = true)
    public List<User> getAll();

    public void deleteUserByUsername(String username);


}
