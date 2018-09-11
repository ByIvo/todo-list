package rocks.byivo.todolist.repositories.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import rocks.byivo.todolist.exceptios.TaskNotFoundException;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.repositories.TaskRepository;

@Repository
public class FakeRepository implements TaskRepository {

    private Map<Long, Task> tasks;
    
    public FakeRepository() {
	tasks = new HashMap<>();
    }
    
    @Override
    public Task create(Task newTask) {
	long idTask = 1L;
	
	newTask.setIdTask(idTask);
	tasks.put(idTask, newTask);
	
	return newTask;
    }

    @Override
    public Task findById(long taskId) {
	if(!tasks.containsKey(taskId)) {
	    throw new TaskNotFoundException(taskId);
	}
	return tasks.get(taskId);
    }

}
