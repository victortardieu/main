package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ReserveCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "reserve";
    public static final String MESSAGE_SUCCESS = "Book reserved: %1$s";
    public static final String MESSAGE_FAILURE = "Book not available for reserving!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reserves the book identified by the index number used in the last book listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final int PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_STUDENT;

    private final Index targetIndex;

    private Book bookToReserve;

    public ReserveCommand (Index targetIndex) {this.targetIndex = targetIndex;}

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(bookToReserve);
        try {
            model.reserveBook(bookToReserve);
        } catch (BookNotFoundException pnfe) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(String.format (MESSAGE_SUCCESS, bookToReserve));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        bookToReserve = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, bookToReserve);
    }

    @Override
    public int getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
