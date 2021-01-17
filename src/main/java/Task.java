/**
 * This class represents a task that can be created
 * through user command.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description information on task to be done.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Checks task completion status and returns formatted status icon.
     *
     * @return formatted status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "[X] " : "[ ] "); //return tick or X symbols
    }

    /**
     * Marks a task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return this.getStatusIcon() + this.description;
    }
}