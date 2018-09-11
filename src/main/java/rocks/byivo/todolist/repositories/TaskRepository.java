package rocks.byivo.todolist.repositories;

import java.util.List;

import rocks.byivo.todolist.model.Task;

public interface TaskRepository {

    Task create(Task newTask);

    Task findById(long taskId);

    List<Task> queryAll();
}
