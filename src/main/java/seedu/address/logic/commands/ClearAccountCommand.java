package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import static java.util.Objects.requireNonNull;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;


public class ClearAccountCommand extends Command{

    public static final String COMMAND_WORD = "cleara";
    public static final String MESSAGE_SUCCESS = "AccountList has been cleared!";
    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    @Override
    public CommandResult execute() throws CommandException {
        UniqueAccountList blankList = new UniqueAccountList();
        try {
            blankList.add(Account.createDefaultAdminAccount());
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
        }
        model.resetAccount(blankList);
        return null;
    }

    public PrivilegeLevel getPrivilegeLevel() {

        return PRIVILEGE_LEVEL;
    }
}
