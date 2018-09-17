package rocks.byivo.todolist.factory;

import java.util.Date;

import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.model.TaskStatus;

public class TaskFixture {

    public static TaskBuilder newTodoTask() {
	return new TaskBuilder()
		.withDescription("")
		.withTitle("")
		.withStatus(TaskStatus.TO_DO)
		.withCreatedAt(new Date())
		.withUpdatedAt(new Date());
    }

    public static TaskBuilder newDoingTask() {
	return new TaskBuilder()
		.withDescription("")
		.withTitle("")
		.withStatus(TaskStatus.DOING)
		.withCreatedAt(new Date())
		.withUpdatedAt(new Date());
    }
}
