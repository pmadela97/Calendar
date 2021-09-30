package pawelmadela.calendar.services;

import pawelmadela.calendar.controllers.Request.TaskRequest;
import pawelmadela.calendar.enums.TaskServiceResponseStatus;
import pawelmadela.calendar.enums.TaskStatus;
import pawelmadela.calendar.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    public ServiceResponse<TaskServiceResponseStatus, List<Task>> getAllUserTasks(String username);
    public ServiceResponse<TaskServiceResponseStatus, Task> getTask(long taskId);
    public TaskServiceResponseStatus createTask(TaskRequest taskRequest);
    public TaskServiceResponseStatus editTask(long userId,TaskRequest taskRequest);
    public TaskServiceResponseStatus editStartDate(String username, LocalDateTime dateTime);
    public TaskServiceResponseStatus editEndDate(String username,LocalDateTime dateTime);
    public TaskServiceResponseStatus editDescription(String username,String description);
    public TaskServiceResponseStatus editTaskStatus(String username, TaskStatus taskStatus);
    public TaskServiceResponseStatus deleteTask(long userId,TaskRequest taskRequest);
}
