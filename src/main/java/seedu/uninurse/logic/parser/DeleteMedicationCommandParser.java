package seedu.uninurse.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.uninurse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.uninurse.commons.core.index.Index;
import seedu.uninurse.logic.commands.DeleteMedicationCommand;
import seedu.uninurse.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMedicationCommand object.
 */
public class DeleteMedicationCommandParser implements Parser<DeleteMedicationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicationCommand
     * and returns a DeleteMedicationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMedicationCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        List<Index> indices;

        try {
            indices = ParserUtil.parseTwoIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicationCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteMedicationCommand(indices.get(0), indices.get(1));
    }
}
