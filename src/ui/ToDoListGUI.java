package ui;

import model.Task;
import model.TaskModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ToDoListGUI {
    private JFrame frame = new JFrame("To-Do List");
    private JList<Task> taskList = new JList<>(new DefaultListModel<>());
    private JTextField descriptionField = new JTextField(20);
    private JTextField priorityField = new JTextField(5);
    private JTextField dueDateField = new JTextField(10);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TaskModel taskModel = new TaskModel();

    public ToDoListGUI() {
        setupGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoListGUI();
            }
        });
    }

    private void setupGUI() {
        setupComponents();
        setupLayout();
        setupListeners();
    }

    private void setupComponents() {
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupLayout() {
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityField);
        panel.add(new JLabel("Due Date:"));
        panel.add(dueDateField);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] labels = {"Add", "Edit", "Delete", "Complete", "Sort by Date", "Sort by Priority"};
        for (String label : labels) {
            JButton button = new JButton(label);
            panel.add(button);
            button.addActionListener(e -> performAction(e.getActionCommand()));
        }
        return panel;
    }

    private void setupListeners() {
        // Add any additional listeners needed for functionality
    }

    private void performAction(String action) {
        try {
            switch (action) {
                case "Add":
                    addTask();
                    break;
                case "Edit":
                    editTask();
                    break;
                case "Delete":
                    deleteTask();
                    break;
                case "Complete":
                    completeTask();
                    break;
                case "Sort by Date":
                    sortTasks(Comparator.comparing(Task::getDueDate));
                    break;
                case "Sort by Priority":
                    sortTasks(Comparator.comparingInt(Task::getPriority));
                    break;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format: Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Priority must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTask() throws ParseException {
        String description = descriptionField.getText().trim();
        String dueDateString = dueDateField.getText().trim();
        String priorityString = priorityField.getText().trim();

        if (description.isEmpty() || dueDateString.isEmpty() || priorityString.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled out to add a task.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dueDate = dateFormat.parse(dueDateString);
        int priority = Integer.parseInt(priorityString);
        Task newTask = new Task(description, dueDate, priority);
        taskModel.addTask(newTask);
        updateTaskListDisplay();
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task to edit.", "Edit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Task taskToEdit = ((DefaultListModel<Task>) taskList.getModel()).getElementAt(selectedIndex);
        String description = JOptionPane.showInputDialog(frame, "Enter new description:", taskToEdit.getDescription());
        if (description != null && !description.trim().isEmpty()) {
            taskToEdit.setDescription(description.trim());
            updateTaskListDisplay();
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        taskModel.removeTask(selectedIndex);
        updateTaskListDisplay();
    }

    private void completeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task to mark as completed.", "Completion Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        taskModel.completeTask(selectedIndex);
        updateTaskListDisplay();
    }

    private void sortTasks(Comparator<Task> comparator) {
        Collections.sort(taskModel.getTasks(), comparator);
        updateTaskListDisplay();
    }

    private void updateTaskListDisplay() {
        DefaultListModel<Task> model = (DefaultListModel<Task>) taskList.getModel();
        model.removeAllElements();
        for (Task task : taskModel.getTasks()) {
            model.addElement(task);
        }
    }
}
