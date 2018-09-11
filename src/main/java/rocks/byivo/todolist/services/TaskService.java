package rocks.byivo.todolist.services;

import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;

public interface TaskService {

    Task createTaskFrom(TaskDTO taskFromClient);
}
