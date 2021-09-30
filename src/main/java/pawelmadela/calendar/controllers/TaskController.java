package pawelmadela.calendar.controllers;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pawelmadela.calendar.controllers.Request.TaskRequest;
import pawelmadela.calendar.controllers.Request.UserRequest;
import pawelmadela.calendar.enums.AccountStatus;
import pawelmadela.calendar.enums.TaskServiceResponseStatus;
import pawelmadela.calendar.enums.TaskStatus;
import pawelmadela.calendar.enums.UserServiceResponseStatus;
import pawelmadela.calendar.model.Task;
import pawelmadela.calendar.model.User;
import pawelmadela.calendar.services.ServiceResponse;
import pawelmadela.calendar.services.TaskServiceImp;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pawelmadela.calendar.enums.TaskServiceResponseStatus.*;
import static pawelmadela.calendar.enums.UserServiceResponseStatus.*;
import static pawelmadela.calendar.enums.UserServiceResponseStatus.SERVER_CONNECTION_FAILURE;

@RestController
@RequestMapping("/api/user/tasks")
public class TaskController {

    TaskServiceImp taskServiceImp;

    @Autowired
    TaskController(TaskServiceImp taskServiceImp){
        this.taskServiceImp = taskServiceImp;
    }

    @GetMapping("/{username}/getall")
    public ResponseEntity<?> getAll(@PathVariable String username, @RequestParam(required = false) boolean isActive,@RequestParam(required = false) boolean isExpired,@RequestParam(required = false) boolean isArchived, Principal principal) {
        ServiceResponse<TaskServiceResponseStatus,List<Task>> serviceResponse = taskServiceImp.getAllUserTasks(username);
        TaskServiceResponseStatus status = serviceResponse.getResponseStatus();
        List<Task> list = serviceResponse.getResponseObject();
        List<Task> responseList = new ArrayList<>();
        int size = list.size();
        System.err.println(size);
        for(int i = 0; i<size; i++){
            if(list.get(i).getStartDateTime().isBefore(LocalDateTime.now())){
                if(!list.get(i).getTaskStatus().equals(TaskStatus.ARCHIVED)){
                    list.get(i).setTaskStatus(TaskStatus.EXPIRED);
                }

            }else{
                if(!list.get(i).getTaskStatus().equals(TaskStatus.ARCHIVED)){
                    list.get(i).setTaskStatus(TaskStatus.ACTIVE);

                }
            }


        }
        switch (status){
            case OK:
                if(isActive){
                    responseList.addAll(list
                            .stream()
                            .filter((Task t) -> t.getTaskStatus().equals(TaskStatus.ACTIVE))
                            .collect(Collectors.toList()));
                }
                if(isExpired){
                    responseList.addAll(list
                            .stream()
                            .filter((Task t) -> t.getTaskStatus().equals(TaskStatus.EXPIRED))
                            .collect(Collectors.toList()));
                }
                if(isArchived){
                    responseList.addAll(list
                            .stream()
                            .filter((Task t) -> t.getTaskStatus().equals(TaskStatus.ARCHIVED))
                            .collect(Collectors.toList()));
                }
                return ResponseEntity.ok().body(responseList);
            case NOT_FOUND:
                return ResponseEntity.badRequest().body(serviceResponse.getResponseStatus());
            case SERVER_CONNECTION_FAILURE:
                return ResponseEntity.internalServerError().build();
            default:
                return  ResponseEntity.badRequest().body("ERROR");
        }
    }

    @PostMapping("/{username}/new")
    public ResponseEntity<?> createTask(@PathVariable String username, @RequestBody TaskRequest taskRequest, Principal principal){
        TaskServiceResponseStatus status = taskServiceImp.createTask(taskRequest);
        switch(status){
            case OK:
                return ResponseEntity.ok().build();
            case NOT_FOUND:
                return ResponseEntity.badRequest().build();
            default:
                return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{userId}/edit")
    public ResponseEntity<?> editTask(@PathVariable long userId, @RequestBody TaskRequest taskRequest, Principal principal){
        TaskServiceResponseStatus status = taskServiceImp.editTask(userId,taskRequest);
        switch(status){
            case OK:
                System.out.println("START"+ taskRequest.getStartDateTime());
                System.out.println("Start2"+ taskServiceImp.getTask(Long.valueOf(taskRequest.getTaskId())).getResponseObject().getStartDateTime());
                return ResponseEntity.ok().build();
            case NOT_FOUND:
                return ResponseEntity.badRequest().build();
            default:
                return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{userId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable long userId, @RequestBody TaskRequest taskRequest, Principal principal){
        TaskServiceResponseStatus status = taskServiceImp.deleteTask(userId,taskRequest);
        switch(status){
            case OK:
                return ResponseEntity.ok().build();
            case NOT_FOUND:
                return ResponseEntity.badRequest().build();
            default:
                return ResponseEntity.internalServerError().build();
        }
    }
}
