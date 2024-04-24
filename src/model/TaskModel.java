package model;

import java.util.ArrayList;
import java.util.List;

public class TaskModel {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) { tasks.add(task); }
    public void editTask(int index, Task newTask) { if (index >= 0 && index < tasks.size()) tasks.set(index, newTask); }
    public void removeTask(int index) { if (index >= 0 && index < tasks.size()) tasks.remove(index); }
    public void completeTask(int index) { if (index >= 0 && index < tasks.size()) tasks.get(index).setCompleted(true); }
    public List<Task> getTasks() { return tasks; }
}
