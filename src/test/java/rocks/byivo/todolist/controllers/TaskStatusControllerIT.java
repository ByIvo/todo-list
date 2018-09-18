package rocks.byivo.todolist.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static rocks.byivo.todolist.ContextPathConfig.BASE_PATH;
import static rocks.byivo.todolist.factory.TaskFixture.newDoingTask;
import static rocks.byivo.todolist.factory.TaskFixture.newTodoTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;

public class TaskStatusControllerIT extends DefaultIntegrationTestRunner {

    private Integer todoTaskId;
    private Integer doingTaskId;

    private static final String TASKS_PATH = BASE_PATH +"/tasks";
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Before
    public void setUp() throws Exception {
	todoTaskId = taskRepository.save(newTodoTask().build()).getIdTask().intValue();
	doingTaskId = taskRepository.save(newDoingTask().build()).getIdTask().intValue();
    }
    
    @After
    public void tearDown() {
	taskRepository.deleteAll();
    }

    @Test
    public void 
    should_change_status_from_todo_to_doing() {
	given()
		.basePath(TASKS_PATH)
	.when()
		.put(todoTaskId + "/start")
	.then()
		.statusCode(200)
		.body("idTask", equalTo(todoTaskId.intValue()))
		.body("readableStatus", equalTo(TaskStatus.DOING.toString()))
		.body("status", equalTo(TaskStatus.DOING.name()));
    }
    
    @Test
    public void should_change_status_from_doing_to_done() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.put(doingTaskId + "/complete")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(doingTaskId.intValue()))
        	.body("readableStatus", equalTo(TaskStatus.DONE.toString()))
        	.body("status", equalTo(TaskStatus.DONE.name()));
    }
    
    @Test
    public void should_rollback_status_from_doing_to_todo() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.put(doingTaskId + "/reset")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(doingTaskId.intValue()))
        	.body("readableStatus", equalTo(TaskStatus.TO_DO.toString()))
        	.body("status", equalTo(TaskStatus.TO_DO.name()));
    }

}
