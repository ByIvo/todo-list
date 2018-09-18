package rocks.byivo.todolist.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.services.TaskService;

@RestController()
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
	this.taskService = taskService;
    }

    @RequestMapping(method=POST)
    public ResponseEntity<TaskDTO> insert(@RequestBody TaskDTO newTaskFromClient) {
	Task createdTask = taskService.createTaskFrom(newTaskFromClient);
	return new ResponseEntity<>(createdTask.toTransferObject(), HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{taskId}", method=GET)
    public ResponseEntity<TaskDTO> getById(@PathVariable(name="taskId", required=true) Long taskId) {
	Task foundTask = taskService.findById(taskId);
	return new ResponseEntity<>(foundTask.toTransferObject(), HttpStatus.OK);
    }
    
    @RequestMapping(method=GET)
    public ResponseEntity<List<TaskDTO>> queryAll() {
	List<Task> allExistingsTasks = taskService.queryAll();
	
	List<TaskDTO> allTaksAsDTO = convertToTransferObjects(allExistingsTasks);
	
	return new ResponseEntity<>(allTaksAsDTO, HttpStatus.OK);
    }

    private List<TaskDTO> convertToTransferObjects(List<Task> allExistingsTasks) {
	return allExistingsTasks.stream()
		.map(Task::toTransferObject)
		.collect(Collectors.toList());
    }
    
    @RequestMapping(value="/{taskId}", method=DELETE)
    public ResponseEntity<TaskDTO> removeTaskById(@PathVariable(name="taskId", required=true) Long taskId) {
	taskService.deleteById(taskId);
	return new ResponseEntity<>(HttpStatus.OK);
    }

}
