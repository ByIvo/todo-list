package rocks.byivo.todolist.services;

import java.util.List;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;

public interface TaskService {

    Task createTaskFrom(TaskDTO taskFromClient);

    Task findById(Long taskId);

    List<Task> queryAll();

    void deleteById(Long taskId);
    
    void moveToStatus(Task taskToBeChanged, TaskStatus newTaskStatus);
}
