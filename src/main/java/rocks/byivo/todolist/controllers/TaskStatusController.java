package rocks.byivo.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.services.TaskService;

@RestController()
@RequestMapping("/tasks")
public class TaskStatusController {

    private TaskService taskService;

    @Autowired
    public TaskStatusController(TaskService taskService) {
	this.taskService = taskService;
    }

    @RequestMapping(path="{idTask}/start", method=RequestMethod.PUT)
    public ResponseEntity<TaskDTO> startTask(@PathVariable(name="idTask")Long idTask) {
	Task foundTask = taskService.findById(idTask);
	taskService.moveToStatus(foundTask, TaskStatus.DOING);
	
	return new ResponseEntity<>(foundTask.toTransferObject(), HttpStatus.OK);
    }
    
    @RequestMapping(path="{idTask}/complete", method=RequestMethod.PUT)
    public ResponseEntity<TaskDTO> completeTask(@PathVariable(name="idTask")Long idTask) {
	Task foundTask = taskService.findById(idTask);
	taskService.moveToStatus(foundTask, TaskStatus.DONE);
	
	return new ResponseEntity<>(foundTask.toTransferObject(), HttpStatus.OK);
    }
    
    @RequestMapping(path="{idTask}/reset", method=RequestMethod.PUT)
    public ResponseEntity<TaskDTO> resetTask(@PathVariable(name="idTask")Long idTask) {
	Task foundTask = taskService.findById(idTask);
	taskService.moveToStatus(foundTask, TaskStatus.TO_DO);
	
	return new ResponseEntity<>(foundTask.toTransferObject(), HttpStatus.OK);
    }
}
