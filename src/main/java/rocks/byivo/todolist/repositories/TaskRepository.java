package rocks.byivo.todolist.repositories;

import java.util.List;

import rocks.byivo.todolist.model.Task;

public interface TaskRepository {

    Task create(Task newTask);
    
    void update(Task taskToMerge);

    Task findById(Long taskId);

    List<Task> queryAll();

    Task deleteById(Long taskId);

    void removeAll();
}
