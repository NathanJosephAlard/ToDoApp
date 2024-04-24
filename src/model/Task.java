package model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String description;
    private Date dueDate;
    private int priority;
    private boolean completed;

    public Task(String description, Date dueDate, int priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("%s (Priority: %d) Due: %s - %s",
                description, priority, dueDate != null ? dueDate.toString() : "No deadline", completed ? "Completed" : "Pending");
    }
}
