package rocks.byivo.todolist.model;


import java.util.Date;

import rocks.byivo.todolist.dto.TaskDTO;

public class Task {

    private Long idTask;
    
    private String title;
    
    private String description;
    
    private TaskStatus status;
    
    private Date createdAt;
    
    private Date updatedAt;

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean hasId() {
	return idTask != null;
    }

    public TaskDTO toTransferObject() {
	TaskDTO taskDTO = new TaskDTO();
	taskDTO.setIdTask(this.getIdTask());
	taskDTO.setTitle(this.getTitle());
	taskDTO.setDescription(this.getDescription());
	
	if(status != null) {
	    taskDTO.setStatus(status.name());
	    taskDTO.setReadableStatus(status.toString());
	}
	
	return taskDTO;
    }
    
}
