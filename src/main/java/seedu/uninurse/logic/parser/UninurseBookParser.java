package seedu.uninurse.logic.parser;

import static seedu.uninurse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.uninurse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.uninurse.logic.commands.AddGenericCommand;
import seedu.uninurse.logic.commands.ClearCommand;
import seedu.uninurse.logic.commands.Command;
import seedu.uninurse.logic.commands.DeleteGenericCommand;
import seedu.uninurse.logic.commands.EditGenericCommand;
import seedu.uninurse.logic.commands.ExitCommand;
import seedu.uninurse.logic.commands.FindCommand;
import seedu.uninurse.logic.commands.HelpCommand;
import seedu.uninurse.logic.commands.ListCommand;
import seedu.uninurse.logic.commands.ListTaskCommand;
import seedu.uninurse.logic.commands.PatientsTodayCommand;
import seedu.uninurse.logic.commands.ViewTaskCommand;
import seedu.uninurse.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class UninurseBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = ParserTranslator.translate(commandWord, matcher.group("arguments"));

        switch (commandWord) {

        case AddGenericCommand.COMMAND_WORD:
            return new AddGenericCommandParser().parse(arguments);

        case EditGenericCommand.COMMAND_WORD:
            return new EditGenericCommandParser().parse(arguments);

        case DeleteGenericCommand.COMMAND_WORD:
            return new DeleteGenericCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case PatientsTodayCommand.COMMAND_WORD:
            return new PatientsTodayCommand();

        case ViewTaskCommand.COMMAND_WORD:
            return new ViewTaskCommandParser().parse(arguments);

        case ListTaskCommand.COMMAND_WORD:
            return new ListTaskCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
