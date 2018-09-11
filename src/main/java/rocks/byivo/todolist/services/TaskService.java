package rocks.byivo.todolist.services;

import java.util.List;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;

public interface TaskService {

    Task createTaskFrom(TaskDTO taskFromClient);

    Task findById(Long taskId);

    List<Task> queryAll();

    Task deleteById(Long taskId);
}
