package pawelmadela.calendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import pawelmadela.calendar.controllers.Request.TaskRequest;
import pawelmadela.calendar.enums.TaskServiceResponseStatus;
import pawelmadela.calendar.enums.TaskStatus;
import pawelmadela.calendar.model.Task;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.repo.TaskRepository;
import pawelmadela.calendar.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static pawelmadela.calendar.enums.TaskServiceResponseStatus.*;

@Service
public class TaskServiceImp implements TaskService{
    TaskRepository taskRepository;
    UserRepository userRepository;

    @Autowired
    TaskServiceImp(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ServiceResponse<TaskServiceResponseStatus, List<Task>> getAllUserTasks(String username) {
        try {
            if (username == null || username.equals("")) return new ServiceResponse(NOT_FOUND, null);
            User user = userRepository.getUserByUsername(username);
            if (user == null) return new ServiceResponse(NOT_FOUND, null);
            List<Task> list = taskRepository.getTasksByUserIdOrderByStartDateTime(user.getUserId());
            return new ServiceResponse<>(OK,list);
        }catch(DataAccessResourceFailureException e){
            return new ServiceResponse(SERVER_CONNECTION_FAILURE,null);
        }
    }

    @Override
    public ServiceResponse<TaskServiceResponseStatus, Task> getTask(long taskId) {

        ServiceResponse<TaskServiceResponseStatus,Task> response = new ServiceResponse<TaskServiceResponseStatus, Task>(OK,taskRepository.getTaskByTaskId(taskId));
        return response;

    }

    @Override
    public TaskServiceResponseStatus createTask(TaskRequest taskRequest) {
        if(taskRequest == null) return NOT_FOUND;
        Task task = new Task(taskRequest);
        System.out.println("AAA"+taskRequest.getStartDateTime());
        taskRequest.setStartDateTime(taskRequest.getStartDateTime());
        taskRequest.setEndDateTime(taskRequest.getEndDateTime());

        try {
            taskRepository.save(task);
            return OK;
        }catch (Exception e){
            return SERVER_CONNECTION_FAILURE;
        }
    }

    @Override
    public TaskServiceResponseStatus editTask(long  userId, TaskRequest taskRequest) {
        if( userId != Long.valueOf(taskRequest.getUserId())) return NOT_FOUND;
            if (taskRequest == null) return NOT_FOUND;
            Task task = taskRepository.getTaskByTaskId(Long.valueOf(taskRequest.getTaskId()));
            task.setTaskName(taskRequest.getTaskName());
            task.setTaskStatus(TaskStatus.valueOf(taskRequest.getTaskStatus()));
            task.setStartDateTime(taskRequest.getStartDateTime());
            task.setEndDateTime(taskRequest.getEndDateTime());
            try {
                taskRepository.save(task);
                return OK;
            } catch (Exception e) {
                return SERVER_CONNECTION_FAILURE;
            }

    }

    @Override
    public TaskServiceResponseStatus editStartDate(String username, LocalDateTime dateTime) {
        return null;
    }

    @Override
    public TaskServiceResponseStatus editEndDate(String username, LocalDateTime dateTime) {
        return null;
    }

    @Override
    public TaskServiceResponseStatus editDescription(String username, String description) {
        return null;
    }

    @Override
    public TaskServiceResponseStatus editTaskStatus(String username, TaskStatus taskStatus) {
        return null;
    }

    @Override
    public TaskServiceResponseStatus deleteTask(long userId,TaskRequest taskRequest) {
        if( userId != Long.valueOf(taskRequest.getUserId())) return NOT_FOUND;
        if (taskRequest == null) return NOT_FOUND;
        Task task = taskRepository.getTaskByTaskId(Long.valueOf(taskRequest.getTaskId()));
        try {
            taskRepository.delete(task);
            return OK;
        } catch (Exception e) {
            return SERVER_CONNECTION_FAILURE;
        }    }
}
