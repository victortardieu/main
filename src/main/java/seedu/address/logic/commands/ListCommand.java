package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.model.Model;

/**
 * Lists all books in the catalogue to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all books";

    public static final int PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_GUEST;


    @Override
    public CommandResult execute() {
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public int getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
