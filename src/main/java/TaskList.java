import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle all the tasks.
 */
public class TaskList {
    private static final String ADD_TODO_COMMAND = "todo";
    private static final String ADD_DEADLINE_COMMAND = "deadline";
    private static final String ADD_EVENT_COMMAND = "event";

    private static final String ADD_TASK_RESPONSE = "You got it! I added this task:\n   ";
    private static final String ADD_DEADLINE_RESPONSE = "You got it! I added this deadline:\n   ";
    private static final String ADD_EVENT_RESPONSE = "You got it! I added this event:\n   ";
    private static final String DELETE_TASK_RESPONSE = "Sweet! I have deleted the following task:\n";
    private static final String WRONG_COMMAND_RESPONSE = "Hey! What is this gibberish?";
    private static final String WRONG_FORMAT_RESPONSE = "Did you format your request properly? \n This is getting old.";

    List<Task> lst = new ArrayList<>();

    /**
     * Iterates and returns enumerated list of tasks.
     */
    public String iterateList() {
        String s = "Here are the items in your list:\n";
        System.out.println("Here are the items in your list:");
        int i = 1;
        lst.forEach(item -> System.out.println(lst.indexOf(item)+ ". " + item.toString() + "\n"));
        for (Task item : lst) {
            s += i + ". " + item.toString() + "\n";
            i++;
        }
        return s;
    };

    public List<Task> fetchTasks() {
        return lst;
    }

    /**
     * Checks for task in the given index and marks it as completed if valid.
     *
     * @param str task number given one-based indexing
     */
    public String markAsDone(String str) {
        try {
            int index = Integer.parseInt(str) - 1;
            Task task = lst.get(index);
            task.markAsDone();

            Ui.formatText();
            System.out.println("Sweet! I have marked the following task as done:\n"
                    + task.toString());
            Ui.formatText();
            return "Sweet! I have marked the following task as done:\n" + task.toString();
        } catch (final NumberFormatException e) {
            System.err.println("Oof, did you type a valid number or not?");
            return "Oof, did you type a valid number or not?";
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("You don't have so many items, dumbass!");
            return "You don't have so many items, dumbass!";
        }
    }

    /**
     * Adds task to list of tasks if it is a valid input.
     * @param input user input in command line
     */
    public String addTask(String input) {
        try {
            String[] split = input.split(" ", 2);
            String command = split[0];

            Ui.formatText();

            boolean isValidCommand = command.equals(ADD_TODO_COMMAND) || command.equals(ADD_DEADLINE_COMMAND)
                    || command.equals(ADD_EVENT_COMMAND);

            if (!isValidCommand || split.length < 2) {
                System.out.println(WRONG_COMMAND_RESPONSE);
                return WRONG_COMMAND_RESPONSE;
            } else if (command.equals("todo")) {
                return addTodo(split[1]);
            } else {
                String[] separateDetails = split[1].split("/by |/at ");
                assert separateDetails.length > 1 : WRONG_FORMAT_RESPONSE;

                String description = separateDetails[0];
                String date = separateDetails[1];
                LocalDate localDate = LocalDate.parse(date);

                if (command.startsWith(ADD_DEADLINE_COMMAND)) {
                    return addDeadline(description, localDate);
                } else {
                    return addEvent(description, localDate);
                }
            }
        } catch (DateTimeParseException e) {
            System.err.println("Your date formatting is invalid, use YYYY-MM-DD please...");
            return "Your date formatting is invalid, use YYYY-MM-DD please...";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Did you format your request properly?";
        }
    }

    /**
     * Adds todo task to task list.
     *
     * @param description description for todo task
     * @return response message to user
     */
    public String addTodo(String description) {
        Todo todo = new Todo(description);
        lst.add(todo);
        System.out.println(ADD_TASK_RESPONSE
                + todo.toString());
        countTasks();
        return ADD_TASK_RESPONSE
                + todo.toString() + "\n"
                + countTasks();
    }

    /**
     * Adds deadline task to task list.
     *
     * @param description description for deadline task
     * @param localDate deadline for task
     * @return response message to user
     */
    public String addDeadline(String description, LocalDate localDate) {
        Deadline deadline = new Deadline(description, localDate);

        lst.add(deadline);
        System.out.println(ADD_DEADLINE_RESPONSE
                + deadline.toString());
        return ADD_DEADLINE_RESPONSE
                + deadline.toString() + "\n" + countTasks();
    }

    /**
     * Adds event to task list.
     *
     * @param description description for event
     * @param localDate date for event
     * @return response message to user
     */
    public String addEvent(String description, LocalDate localDate) {
        Event event = new Event(description, localDate);
        lst.add(event);
        System.out.println(ADD_EVENT_RESPONSE
                + event.toString());
        return ADD_EVENT_RESPONSE
                + event.toString() + "\n" + countTasks();
    }


    /**
     * Deletes task from list of tasks.
     * @param str task number given one-based indexing
     */
    public String deleteTask(String str) {
        try {
            int index = Integer.parseInt(str) - 1;
            Task task = lst.get(index);
            lst.remove(index);

            Ui.formatText();
            System.out.println(DELETE_TASK_RESPONSE + task.toString());
            countTasks();
            Ui.formatText();
            return DELETE_TASK_RESPONSE + task.toString() + "\n" + countTasks();
        } catch (final NumberFormatException e) {
            System.err.println("Oof, did you type a valid number or not?");
            return "Oof, did you type a valid number or not?";
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("You don't have so many items, dumbass!");
            return "You don't have so many items, dumbass!";
        }
    }

    public String searchTask(String str) {
        System.out.println("Here are the items that match your search:");
        String s = "Here are the items that match your search:";
        int i = 1;
        for (Task item : lst) {
            if (item.description.toLowerCase().contains(str.toLowerCase())) {
                System.out.println(i + ". " + item.toString());
                s += i + ". " + item.toString() + "\n";
                i++;
            }
        }
        return s;
    }

    /**
     * Determines if task is Todo, Deadline or Event and handles them accordingly.
     *
     * @param str input from user.
     */
    public void readTask(String str) {
        String[] split = str.split("`");

        switch (split[0]) {
        case "Todo":
            Todo todo = new Todo(split[2], Boolean.parseBoolean(split[1]));
            lst.add(todo);
            break;
        case "Deadline":
            Deadline deadline = new Deadline(Boolean.parseBoolean(split[1]),
                    split[2], LocalDate.parse(split[3]));
            lst.add(deadline);
            break;
        case "Event":
            Event event = new Event(Boolean.parseBoolean(split[1]),
                    split[2], LocalDate.parse(split[3]));
            lst.add(event);
            break;
        }
    }

    /**
     * Outputs current number of active tasks in the TaskList.
     */
    public String countTasks() {
        System.out.println("Now you have " + lst.size() + " tasks.");
        return "Now you have " + lst.size() + " tasks.";
    }
}
