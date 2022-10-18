package seedu.uninurse.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.uninurse.model.Model;

/**
 * Lists all tasks associated to all patients in the uninurse book to the user.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listTask";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonListWithTasks(p -> !(p.getTasks().isEmpty()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}