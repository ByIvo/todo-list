package rocks.byivo.todolist.builders;

import java.util.Date;

import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;

public class TaskBuilder {

    private Task task;

    public TaskBuilder() {
	task = new Task();
    }

    public TaskBuilder withIdTask(Long idTask) {
	task.setIdTask(idTask);
	return this;
    }

    public TaskBuilder withTitle(String title) {
	task.setTitle(title);
	return this;
    }
    
    public TaskBuilder withDescription(String description) {
	task.setDescription(description);
	return this;
    }

    public TaskBuilder withStatus(TaskStatus status) {
	task.setStatus(status);
	return this;
    }

    public TaskBuilder withCreatedAt(Date createdAt) {
	task.setCreatedAt(createdAt);
	return this;
    }

    public TaskBuilder withUpdatedAt(Date updatedAt) {
	task.setUpdatedAt(updatedAt);
	return this;
    }
    
    public Task build() {
	return task;
    }
}
