package rocks.byivo.todolist.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static rocks.byivo.todolist.ContextPathConfig.BASE_PATH;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIT {
    
    private static final String RANDOM_DESCRIPTION = "A random description";
    private static final String FAKE_TITLE = "Fake Title";
    private static final String TASKS_PATH = BASE_PATH +"/tasks";
    
    @LocalServerPort
    int port;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Before
    public void setUp() throws Exception {
	RestAssured.port = port;
	preTaskInsert();
    }
    
    @After
    public void tearDown() {
	taskRepository.removeAll();
    }

    private void preTaskInsert() {
	Task newTask = new TaskBuilder()
		.withIdTask(1l)
		.withTitle(FAKE_TITLE)
		.withDescription(RANDOM_DESCRIPTION)
		.withStatus(TaskStatus.TO_DO)
		.withCreatedAt(new Date())
		.withUpdatedAt(new Date())
		.build();
	
	taskRepository.create(newTask);
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
		.body("idTask", greaterThan(1))
		.body("title", equalTo(FAKE_TITLE))
		.body("description", equalTo(RANDOM_DESCRIPTION))
		.body("status", equalTo(TaskStatus.TO_DO.name()))
		.body("readableStatus", equalTo(TaskStatus.TO_DO.toString()));
    }
    
    @Test
    public void 
    should_retrieve_a_task_using_its_identifier() throws Exception {
	 given()
        	.basePath(TASKS_PATH)
        .when()
        	.get("/1")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(1));
    }
    
    @Test
    public void 
    should_get_a_404_when_retrieve_a_non_existing_task() throws Exception {
	 given()
        	.basePath(TASKS_PATH)
        .when()
        	.get("/2")
        .then()
        	.statusCode(404)
        	.body("message", equalTo("It was no possible to find a Task identified by 2"));
    }

    @Test
    public void 
    should_query_all_existing_tasks() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.get()
        .then()
        	.statusCode(200)
        	.body("$", hasSize(1));
    }
    
    @Test
    public void 
    should_delete_a_task_with_provided_id() throws Exception {
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.delete("/1")
        .then()
        	.statusCode(200)
        	.body("title", equalTo(FAKE_TITLE))
		.body("description", equalTo(RANDOM_DESCRIPTION));
    }
    
    @Test
    public void 
    should_get_a_404_when_delete_a_non_existing_task() throws Exception {
	 given()
        	.basePath(TASKS_PATH)
        .when()
        	.get("/2")
        .then()
        	.statusCode(404)
        	.body("message", equalTo("It was no possible to find a Task identified by 2"));
    }

}
