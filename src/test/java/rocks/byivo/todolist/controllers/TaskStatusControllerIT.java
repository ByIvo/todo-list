package rocks.byivo.todolist.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static rocks.byivo.todolist.ContextPathConfig.BASE_PATH;
import static rocks.byivo.todolist.factory.TaskFixture.newDoingTask;
import static rocks.byivo.todolist.factory.TaskFixture.newTodoTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskStatusControllerIT {

    private static final Long TODO_TASK_ID = 1L;
    private static final Long DOING_TASK_ID = 2L;

    private static final String TASKS_PATH = BASE_PATH +"/tasks";
    
    @LocalServerPort
    int port;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Before
    public void setUp() throws Exception {
	RestAssured.port = port;
	
	taskRepository.create(newTodoTask().withIdTask(TODO_TASK_ID).build());
	taskRepository.create(newDoingTask().withIdTask(DOING_TASK_ID).build());
    }
    
    @After
    public void tearDown() {
	taskRepository.removeAll();
    }

    @Test
    public void 
    should_change_status_from_todo_to_doing() {
	given()
		.basePath(TASKS_PATH)
	.when()
		.put(TODO_TASK_ID + "/start")
	.then()
		.statusCode(200)
		.body("idTask", equalTo(TODO_TASK_ID.intValue()))
		.body("readableStatus", equalTo(TaskStatus.DOING.toString()))
		.body("status", equalTo(TaskStatus.DOING.name()));
    }
    
    @Test
    public void should_change_status_from_doing_to_done() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.put(DOING_TASK_ID + "/complete")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(DOING_TASK_ID.intValue()))
        	.body("readableStatus", equalTo(TaskStatus.DONE.toString()))
        	.body("status", equalTo(TaskStatus.DONE.name()));
    }
    
    @Test
    public void should_rollback_status_from_doing_to_todo() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.put(DOING_TASK_ID + "/reset")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(DOING_TASK_ID.intValue()))
        	.body("readableStatus", equalTo(TaskStatus.TO_DO.toString()))
        	.body("status", equalTo(TaskStatus.TO_DO.name()));
    }

}
