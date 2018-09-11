package rocks.byivo.todolist.dto;

public class ErrorDTO {

    private final String message;

    public ErrorDTO(String message) {
	super();
	this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((message == null) ? 0 : message.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ErrorDTO other = (ErrorDTO) obj;
	if (message == null) {
	    if (other.message != null)
		return false;
	} else if (!message.equals(other.message))
	    return false;
	return true;
    }

}
