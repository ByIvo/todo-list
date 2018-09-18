package rocks.byivo.todolist.services.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import rocks.byivo.todolist.builders.TaskBuilder;
import rocks.byivo.todolist.dto.TaskDTO;
import rocks.byivo.todolist.exceptios.TaskNotFoundException;
import rocks.byivo.todolist.model.Task;
import rocks.byivo.todolist.model.TaskStatus;
import rocks.byivo.todolist.repositories.TaskRepository;
import rocks.byivo.todolist.services.TaskService;

@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    private static final String EXPECTED_TITLE = "EXPECTED_TITLE";
    private static final String EXPECTED_DESCRIPTION = "EXPECTED_DESCRIPTION";

    @Mock
    private TaskRepository taskRepository;
    
    private TaskService taskService;
    
    @Before
    public void setUp() throws Exception {
	taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    public void shouldCreateANewTaskWithTheTitleAndDescriptionProvidedInParams() {
	TaskDTO newTaskFromClient = setupNewTaskFromClient();
	
	taskService.createTaskFrom(newTaskFromClient);
	
	Task taskToBeCreated = captureTaskProvidedInRepository();
	assertThat(taskToBeCreated.getTitle(), is(EXPECTED_TITLE));
	assertThat(taskToBeCreated.getDescription(), is(EXPECTED_DESCRIPTION));
    }
    
    @Test
    public void shouldCreateANewTaskWithTheTodoStatus() {
	TaskDTO newTaskFromClient = setupNewTaskFromClient();
	
	taskService.createTaskFrom(newTaskFromClient);
	
	Task taskToBeCreated = captureTaskProvidedInRepository();
	assertThat(taskToBeCreated.getStatus(), is(TaskStatus.TO_DO));
    }
    
    @Test
    public void theCreatedAndUpdateDateShouldBeBetweenTheStartAndTheEndOfOperationExecutionDate() throws Exception {
	TaskDTO newTaskFromClient = setupNewTaskFromClient();
	
	Date startingDate = new Date();
	taskService.createTaskFrom(newTaskFromClient);
	Date endingDate = new Date();

	Task taskToBeCreated = captureTaskProvidedInRepository();
	assertThat(taskToBeCreated.getCreatedAt(), between(startingDate, endingDate));
	assertThat(taskToBeCreated.getUpdatedAt(), between(startingDate, endingDate));
    }
    
    @Test
    public void shouldReturnTheCreatedTaskFromRepostory() throws Exception {
	TaskDTO newTaskFromClient = setupNewTaskFromClient();
	
	Task expectedTask = mock(Task.class);
	doReturn(expectedTask).when(taskRepository).save(Mockito.any());
	
	Task createdTask = taskService.createTaskFrom(newTaskFromClient);
	
	assertThat(createdTask, is(expectedTask));
    }
    
    private TaskDTO setupNewTaskFromClient() {
	TaskDTO newTaskFromClient = new TaskBuilder()
		.withTitle(EXPECTED_TITLE)
		.withDescription(EXPECTED_DESCRIPTION)
		.build()
		.toTransferObject();
	return newTaskFromClient;
    }

    private Task captureTaskProvidedInRepository() {
	ArgumentCaptor<Task> newTaskCaptor = ArgumentCaptor.forClass(Task.class);
	verify(taskRepository).save(newTaskCaptor.capture());
	
	Task taskToBeCreated = newTaskCaptor.getValue();
	return taskToBeCreated;
    }

    private Matcher<Date> between(Date startDate, Date endindDate) {
	
	return new Matcher<Date>() {

	    @Override
	    public void describeTo(Description description) {}

	    @Override
	    public boolean matches(Object item) {
		Date actual = (Date) item;
		return actual.compareTo(startDate) >= 0 && actual.compareTo(endindDate) <= 0;
	    }

	    @Override
	    public void describeMismatch(Object item, Description mismatchDescription) {}

	    @Override
	    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {}

	};
    }
    
    @Test
    public void shouldFindByIdentifierTheTaskInRepository() throws Exception {
	long taskId = 1L;
	
	doReturn(Optional.of(mock(Task.class))).when(taskRepository).findById(taskId);
	taskService.findById(taskId);
	verify(taskRepository).findById(taskId);
    }
    
    @Test
    public void shouldReturnTheValueFoundInRepository() throws Exception {
	long taskId = 1L;
	Task expectedTask = mock(Task.class);
	doReturn(Optional.of(expectedTask)).when(taskRepository).findById(taskId);
	
	Task foundTask = taskService.findById(taskId);
	assertThat(foundTask, is(expectedTask));
    }
    
    @Test(expected=TaskNotFoundException.class)
    public void shouldThrowAnErrorWhenTheIdDoesNotExist() throws Exception {
	long taskId = 1L;
	doReturn(Optional.empty()).when(taskRepository).findById(taskId);
	
	taskService.findById(taskId);
    }
    
    @Test
    public void shouldQueryAllExistingTasksInRepository() throws Exception {
	taskService.queryAll();
	verify(taskRepository).findAll();
    }
    
    @Test
    public void shouldReturnTheSameTasksAsRepository() throws Exception {
	List<Task> expectedExistingTasks = Arrays.asList(mock(Task.class));
	doReturn(expectedExistingTasks).when(taskRepository).findAll();
	
	List<Task> allExistingTasks = taskService.queryAll();
	assertThat(allExistingTasks, is(expectedExistingTasks));
    }
    
    @Test
    public void shouldDeleteInRepositoryWithProvidedId() throws Exception {
	long taskId = 1L;
	taskService.deleteById(taskId);
	verify(taskRepository).deleteById(taskId);
    }
    
    @Test
    public void shouldChangeTheStatusInRequiredTaskToDesiredNewStatus() throws Exception {
	Task taskToBeChanged = mock(Task.class);
	
	taskService.moveToStatus(taskToBeChanged, TaskStatus.DOING);
	verify(taskToBeChanged).setStatus(TaskStatus.DOING);
    }
    
    @Test
    public void shouldPersistTheTaskWithNewStatusWhenItChanges() throws Exception {
	Task taskToBeChanged = mock(Task.class);
	
	taskService.moveToStatus(taskToBeChanged, TaskStatus.DOING);
	verify(taskRepository).save(taskToBeChanged);
    }

}
