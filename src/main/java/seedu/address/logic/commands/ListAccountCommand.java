package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;

public class ListAccountCommand extends Command{

    public static final String COMMAND_WORD = "listAccount";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }

    public CommandResult execute() {
        model.getFilteredAccountList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
