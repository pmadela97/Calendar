package pawelmadela.calendar.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pawelmadela.calendar.model.Task;
import pawelmadela.calendar.model.User;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task,Long> {

    public Task getTaskByUserId(long userId);
    public Task getTaskByTaskId(long taskId);
   // public void deleteAllBy(long userId);
    public List<Task> getTasksByUserIdOrderByStartDateTime(long userId);

}
