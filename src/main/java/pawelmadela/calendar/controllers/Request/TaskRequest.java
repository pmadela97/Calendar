package pawelmadela.calendar.controllers.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import pawelmadela.calendar.enums.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;


public class TaskRequest {
    private String taskId;
    private String taskName;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endDateTime;
    private String description;
    private String taskStatus;
    private String userId;

    TaskRequest(){}

    public TaskRequest(String taskId, String taskName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, String taskStatus, String userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.taskStatus = taskStatus;
        this.userId = userId;
    }

    public TaskRequest(String taskName, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, String taskStatus, String userId) {
        this.taskName = taskName;

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.taskStatus = taskStatus;
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
