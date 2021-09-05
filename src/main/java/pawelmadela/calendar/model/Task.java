package pawelmadela.calendar.model;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    long taskId;
    String taskName;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    String description;
    String location;
    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;
    long userId;

    public Task() {

    }

    public Task(long taskId, String taskName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, String location, TaskStatus taskStatus, long userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.location = location;
        this.taskStatus = taskStatus;
        this.userId = userId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
