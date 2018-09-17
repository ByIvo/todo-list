package rocks.byivo.todolist.repositories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import rocks.byivo.todolist.exceptios.TaskNotFoundException;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.repositories.TaskRepository;

@Repository
public class FakeRepository implements TaskRepository {

    private Map<Long, Task> tasks;
    
    private long idCounter;
    
    public FakeRepository() {
	tasks = new HashMap<>();
	idCounter = 0;
    }
    
    @Override
    public Task create(Task newTask) {
	long idTask = setupId(newTask);
	
	newTask.setIdTask(idTask);
	tasks.put(idTask, newTask);
	
	return newTask;
    }

    private long setupId(Task newTask) {
	if(newTask.hasId()) {
	    return setIdCountToBeAtLeastActualId(newTask);
	} else {
	    return ++idCounter;
	}
    }

    private Long setIdCountToBeAtLeastActualId(Task newTask) {
	Long idTask = newTask.getIdTask();
	
	if(idTask > idCounter) {
	idCounter = idTask;
	}
	
	return idTask;
    }

    @Override
    public Task findById(Long taskId) {
	validateExistanceOf(taskId);
	return tasks.get(taskId);
    }

    private void validateExistanceOf(Long taskId) {
	if(!tasks.containsKey(taskId)) {
	    throw new TaskNotFoundException(taskId);
	}
    }

    @Override
    public List<Task> queryAll() {
	return new ArrayList<>(tasks.values());
    }

    @Override
    public Task deleteById(Long taskId) {
	validateExistanceOf(taskId);
	return tasks.remove(taskId);
    }

    @Override
    public void removeAll() {
	tasks.clear();
    }

    @Override
    public void update(Task taskToMerge) {
	this.deleteById(taskToMerge.getIdTask());
	this.create(taskToMerge);
    }

}
