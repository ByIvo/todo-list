package rocks.byivo.todolist.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    
    TO_DO("To Do"),
    DOING("Doing"),
    DONE("Done"),
    ;
    
    private String humanReadableName;

    private TaskStatus(String humanReadableName) {
	this.humanReadableName = humanReadableName;
    }
    
    @Override
    @JsonValue
    public String toString() {
        return humanReadableName;
    }
}
