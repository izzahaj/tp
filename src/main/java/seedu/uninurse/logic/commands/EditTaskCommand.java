package seedu.uninurse.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.uninurse.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.List;
import java.util.Set;

import seedu.uninurse.commons.core.Messages;
import seedu.uninurse.commons.core.index.Index;
import seedu.uninurse.logic.commands.exceptions.CommandException;
import seedu.uninurse.model.Model;
import seedu.uninurse.model.person.Address;
import seedu.uninurse.model.person.Email;
import seedu.uninurse.model.person.Name;
import seedu.uninurse.model.person.Patient;
import seedu.uninurse.model.person.Phone;
import seedu.uninurse.model.tag.Tag;
import seedu.uninurse.model.task.Task;
import seedu.uninurse.model.task.TaskList;

/**
 * Edits the details of an existing Task for a patient.
 */
public class EditTaskCommand extends EditGenericCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the specified task associated with a patient "
            + "by the index number used in the displayed patient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: PATIENT INDEX (must be a positive integer) TASK INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK_DESCRIPTION + "TASK]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + " 2 "
            + PREFIX_TASK_DESCRIPTION + "change bandage";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Task to edit must be provided.";

    private final Index patientIndex;
    private final Index taskIndex;
    private final Task updatedTask;

    /**
     * @param patientIndex of the patient in the filtered patient list to edit.
     * @param taskIndex of the task to be edited.
     * @param updatedTask details to edit for the task.
     */
    public EditTaskCommand(Index patientIndex, Index taskIndex, Task updatedTask) {
        requireNonNull(patientIndex);
        requireNonNull(taskIndex);
        requireNonNull(updatedTask);

        this.patientIndex = patientIndex;
        this.taskIndex = taskIndex;
        this.updatedTask = updatedTask;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(patientIndex.getZeroBased());

        if (taskIndex.getZeroBased() >= patientToEdit.getTasks().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        Patient editedPatient = createEditedPatient(patientToEdit, taskIndex, updatedTask);

        model.setPerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedPatient));
    }

    /**
     * Creates an edited person with the updated Task.
     *
     * @param patientToEdit the patient associated with the task to edit.
     * @param indexOfTask the index of the task to be edited.
     * @param updatedTask the new task details to be edited with.
     * @return a person with the updated task details.
     */
    private static Patient createEditedPatient(Patient patientToEdit, Index indexOfTask, Task updatedTask) {
        assert patientToEdit != null;

        Name name = patientToEdit.getName();
        Phone phone = patientToEdit.getPhone();
        Email email = patientToEdit.getEmail();
        Address address = patientToEdit.getAddress();
        Set<Tag> tags = patientToEdit.getTags();

        TaskList updatedTaskList = patientToEdit.getTasks().edit(indexOfTask.getZeroBased(), updatedTask);

        return new Patient(name, phone, email, address, updatedTaskList, tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return patientIndex.equals(e.patientIndex)
                && taskIndex.equals(e.taskIndex)
                && updatedTask.equals(e.updatedTask);
    }
}