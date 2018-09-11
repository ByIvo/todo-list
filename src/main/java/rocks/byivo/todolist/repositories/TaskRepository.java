package rocks.byivo.todolist.repositories;

import rocks.byivo.todolist.model.Task;

public interface TaskRepository {

    Task create(Task newTask);

    Task findById(long taskId);
}
