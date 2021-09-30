package pawelmadela.calendar.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import pawelmadela.calendar.controllers.Request.TaskRequest;
import pawelmadela.calendar.enums.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class that represents object which contain all information about tasks, which users wants creates
 */
@Entity
@Table(name = "TASKS")
public class Task{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long taskId;
    String taskName;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime endDateTime;
    String description;
    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;
    long userId;

    public Task() {

    }

    public Task(long taskId, String taskName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, TaskStatus taskStatus, long userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startDateTime = LocalDateTime.of(startDateTime.toLocalDate(),startDateTime.toLocalTime());
        this.endDateTime = LocalDateTime.of(endDateTime.toLocalDate(),endDateTime.toLocalTime());
        this.description = description;
        this.taskStatus = TaskStatus.ACTIVE;
        this.userId = userId;
    }
    public Task(TaskRequest taskRequest) {
        this.taskName = taskRequest.getTaskName();
        this.startDateTime = LocalDateTime.of(taskRequest.getStartDateTime().toLocalDate(),taskRequest.getStartDateTime().toLocalTime());

        this.endDateTime = LocalDateTime.of(taskRequest.getEndDateTime().toLocalDate(),taskRequest.getEndDateTime().toLocalTime());
        this.description = taskRequest.getDescription();
        this.taskStatus = TaskStatus.ACTIVE;
        this.userId = Long.valueOf(taskRequest.getUserId());
    }

    public long getTaskId() {
        return taskId;
    }

    public long getUserId() {
        return userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
