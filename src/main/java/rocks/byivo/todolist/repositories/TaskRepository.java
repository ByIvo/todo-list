package rocks.byivo.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rocks.byivo.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
