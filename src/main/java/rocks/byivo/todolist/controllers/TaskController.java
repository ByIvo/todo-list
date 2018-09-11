package rocks.byivo.todolist.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.services.TaskService;

@RestController("tasks")
public class TaskController {

    private TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
	this.taskService = taskService;
    }

    @RequestMapping(method=POST)
    ResponseEntity<TaskDTO> insert(@RequestBody TaskDTO newTaskFromClient) {
	Task createdTask = taskService.createTaskFrom(newTaskFromClient);
	return new ResponseEntity<TaskDTO>(createdTask.toTransferObject(), HttpStatus.CREATED);
    }
}
