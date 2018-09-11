package rocks.byivo.todolist.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;
import rocks.byivo.todolist.services.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
	this.taskRepository = taskRepository;
    }

    @Override
    public Task createTaskFrom(TaskDTO taskFromClient) {
	Task taskToBeCreated = createTaskWithInfoFrom(taskFromClient);
	return taskRepository.create(taskToBeCreated);
    }

    private Task createTaskWithInfoFrom(TaskDTO taskFromClient) {
	Date creationDate = new Date();
	
	return new TaskBuilder()
		.withTitle(taskFromClient.getTitle())
		.withDescription(taskFromClient.getDescription())
		.withStatus(TaskStatus.TO_DO)
		.withCreatedAt(creationDate)
		.withUpdatedAt(creationDate)
		.build();
    }

    @Override
    public Task findById(Long taskId) {
	return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> queryAll() {
	return taskRepository.queryAll();
    }

    @Override
    public Task deleteById(Long taskId) {
	return taskRepository.deleteById(taskId);
    }

}
