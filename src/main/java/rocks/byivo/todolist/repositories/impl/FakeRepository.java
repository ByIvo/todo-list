package rocks.byivo.todolist.repositories.impl;

import org.springframework.stereotype.Repository;

import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.repositories.TaskRepository;

@Repository
public class FakeRepository implements TaskRepository {

    @Override
    public Task create(Task newTask) {
	newTask.setIdTask(1L);
	return newTask;
    }

}
