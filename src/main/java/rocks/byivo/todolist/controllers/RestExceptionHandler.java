package rocks.byivo.todolist.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import rocks.byivo.todolist.dto.ErrorDTO;
import rocks.byivo.todolist.exceptios.TaskNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    protected ResponseEntity<Object> handleTaskNotFoundExcepton(TaskNotFoundException ex) {
	return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
}
