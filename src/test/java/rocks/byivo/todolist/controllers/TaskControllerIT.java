package rocks.byivo.todolist.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static rocks.byivo.todolist.ContextPathConfig.BASE_PATH;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.TaskStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIT {
    
    private static final String RANDOM_DESCRIPTION = "A random description";
    private static final String FAKE_TITLE = "Fake Title";
    private static final String TASKS_PATH = BASE_PATH +"/tasks";
    
    @LocalServerPort
    int port;
    
    @Before
    public void setUp() throws Exception {
	RestAssured.port = port;
    }

    @Test
    public void 
    should_create_a_TODO_task_with_provided_title_and_description() {
	TaskDTO newTaskFromClient = new TaskBuilder()
		.withTitle(FAKE_TITLE)
		.withDescription(RANDOM_DESCRIPTION)
		.build()
		.toTransferObject();
	
	given()
		.basePath(TASKS_PATH)
		.contentType(ContentType.JSON)
		.body(newTaskFromClient)
	.when()
		.post()
	.then()
		.statusCode(201)
		.body("idTask", equalTo(1))
		.body("title", equalTo(FAKE_TITLE))
		.body("description", equalTo(RANDOM_DESCRIPTION))
		.body("status", equalTo(TaskStatus.TO_DO.name()))
		.body("readableStatus", equalTo(TaskStatus.TO_DO.toString()));
    }

}
