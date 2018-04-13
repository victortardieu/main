package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

//import static seedu.address.logic.parser.CliSyntax.;


/**
 * Adds a book to the catalogue.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the catalogue. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_AUTHOR + "AUTHOR "
        + PREFIX_ISBN + "ISBN "
        + PREFIX_AVAIL + "AVAIL "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Animal Farm "
        + PREFIX_AUTHOR + "George Orwell "
        + PREFIX_ISBN + "9780736692427 "
        + PREFIX_AVAIL + "Borrowed "
        + PREFIX_TAG + "political "
        + PREFIX_TAG + "satire ";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the catalogue";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Book toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     */
    public AddCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addBook(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateBookException e) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }

    //@@author QiuHaohao
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
