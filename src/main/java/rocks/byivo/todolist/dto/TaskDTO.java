package rocks.byivo.todolist.dto;

import java.io.Serializable;

public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTask;
    
    private String title;
    
    private String description;
    
    private String status;
    
    private String readableStatus;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadableStatus() {
        return readableStatus;
    }

    public void setReadableStatus(String readableStatus) {
        this.readableStatus = readableStatus;
    }

}
