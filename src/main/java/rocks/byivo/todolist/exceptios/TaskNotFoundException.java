package rocks.byivo.todolist.exceptios;

public class TaskNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TaskNotFoundException(Long taskId) {
	super(String.format("It was no possible to find a Task identified by %d", taskId));
    }

}
