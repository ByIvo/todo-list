package rocks.byivo.todolist.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static rocks.byivo.todolist.ContextPathConfig.BASE_PATH;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.http.ContentType;
import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.factory.TaskFixture;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;


public class TaskControllerIT extends DefaultIntegrationTestRunner {
    
    private static final String RANDOM_DESCRIPTION = "A random description";
    private static final String FAKE_TITLE = "Fake Title";
    private static final String TASKS_PATH = BASE_PATH +"/tasks";
    
    private Integer generatedId;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Before
    public void setUp() throws Exception {
	preTaskInsert();
    }
    
    @After
    public void tearDown() {
	taskRepository.deleteAll();
    }

    private void preTaskInsert() {
	Task newTask = TaskFixture.newTodoTask()
		.withIdTask(1l)
		.withTitle(FAKE_TITLE)
		.withDescription(RANDOM_DESCRIPTION)
		.build();
	
	Task persistedTask = taskRepository.save(newTask);
	generatedId = persistedTask.getIdTask().intValue();
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
        	.get(generatedId + "")
        .then()
        	.statusCode(200)
        	.body("idTask", equalTo(generatedId));
    }
    
    @Test
    public void 
    should_get_a_404_when_retrieve_a_non_existing_task() throws Exception {
	 int nonExistingId = generatedId + 1;
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.get(nonExistingId + "")
        .then()
        	.statusCode(404)
        	.body("message", equalTo("It was not possible to find a Task identified by " + nonExistingId));
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
        	.delete(generatedId + "")
        .then()
        	.statusCode(200);
    }
    
    @Test
    public void 
    should_get_a_404_when_delete_a_non_existing_task() throws Exception {
	int nonExistingId = generatedId + 1;
	given()
        	.basePath(TASKS_PATH)
        .when()
        	.get(nonExistingId + "")
        .then()
        	.statusCode(404)
        	.body("message", equalTo("It was not possible to find a Task identified by " + nonExistingId));
    }

}
