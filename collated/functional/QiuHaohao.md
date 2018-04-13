# QiuHaohao
###### /src/test/java/seedu/address/logic/parser/LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        LoginCommand expectedFindCommand =
            new LoginCommand("admin", "admin");
        assertParseSuccess(parser, "admin admin", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n admin \n \t admin  \t", expectedFindCommand);
    }
}
```
###### /src/test/java/seedu/address/logic/commands/LoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        LoginCommand loginStudentCommand = new LoginCommand("student", "student");
        LoginCommand loginAdminCommand = new LoginCommand("admin", "admin");

        // same object -> returns true
        assertTrue(loginStudentCommand.equals(loginStudentCommand));

        // same values -> returns true
        LoginCommand loginStudentCommandCopy = new LoginCommand("student", "student");
        assertTrue(loginStudentCommand.equals(loginStudentCommandCopy));

        // different types -> returns false
        assertFalse(loginStudentCommand.equals(1));

        // null -> returns false
        assertFalse(loginStudentCommand.equals(null));

        // different person -> returns false
        assertFalse(loginStudentCommand.equals(loginAdminCommand));
    }

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, "admin");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand("admin", null);
    }

    @Test
    public void execute_loginAsStudent_loginSuccessful() throws Exception {
        Model model = new ModelManager();
        Account student = Account.createDefaultStudentAccount();
        model.addAccount(student);
        LoginCommand studentLogin = new LoginCommand("student", "student");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_STUTENT, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_STUDENT);
    }

    @Test
    public void execute_loginAsLibrarian_loginSuccessful() {
        Model model = new ModelManager();
        LoginCommand studentLogin = new LoginCommand("admin", "admin");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_LIBRARIAN, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_LIBRARIAN);
    }
}
```
###### /src/test/java/seedu/address/logic/commands/LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import static org.junit.Assert.assertEquals;
//import org.junit.Rule;
//import org.junit.rules.ExpectedException;
//import seedu.address.model.ModelManager;
//import seedu.address.model.Model;


public class LogoutCommandTest {
    @Test
    public void equals() {
        LogoutCommand logoutCommand1 = new LogoutCommand();
        LogoutCommand logoutCommand2 = new LogoutCommand();

        // same object -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand1));

        // same values -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand2));

        // different types -> returns false
        assertFalse(logoutCommand1.equals(1));

        // null -> returns false
        assertFalse(logoutCommand1.equals(null));
    }
}
```
###### /src/test/java/seedu/address/model/account/UsernameTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void isValidUsername() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        //invalid
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername("123")); // too short
        assertFalse(Username.isValidUsername("abc")); // too short
        assertFalse(Username.isValidUsername("!!!")); // too short and non-word characters
        assertFalse(Username.isValidUsername("!!!!!!")); // non-word characters
        assertFalse(Username.isValidUsername("abcasj!")); // too short and non-word characters
        assertFalse(Username.isValidUsername(""));

        //valid
        assertTrue(Username.isValidUsername("abcde"));
        assertTrue(Username.isValidUsername("banana"));
        assertTrue(Username.isValidUsername("addressbook"));
        assertTrue(Username.isValidUsername("abcde123"));
        assertTrue(Username.isValidUsername("FHAIgasjd123987514"));
        assertTrue(Username.isValidUsername("123123123123"));

    }

    @Test
    public void getUsername() {
        String usernameString = "username";
        Username p = new Username(usernameString);
        assertEquals(usernameString, p.getUsername());
    }

    @Test
    public void equals() {
        Username p1 = new Username("username1");
        Username p1copy = new Username("username1");
        Username p2 = new Username("username2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /src/test/java/seedu/address/model/account/AccountTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AccountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(new Name("dummy"), null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, new Credential("dummy", "dummy"), null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, new MatricNumber("A1231231A"), null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, new PrivilegeLevel(0)));
    }

    @Test
    public void credentialMatchesTest() {
        Credential studentCredential = new Credential("student", "student");
        Credential adminCredential = new Credential("admin", "admin");
        Account studentAccount = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();
        assertTrue(studentAccount.credentialMatches(studentCredential));
        assertTrue(adminAccount.credentialMatches(adminCredential));
        assertFalse(studentAccount.credentialMatches(adminCredential));
        assertFalse(adminAccount.credentialMatches(studentCredential));
    }

    @Test
    public void equalsTest() {
        Account studentAccount = Account.createDefaultStudentAccount();
        Account studentAccountCopy = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();

        assertTrue(studentAccount.equals(studentAccount));
        assertTrue(studentAccount.equals(studentAccountCopy));
        assertFalse(studentAccount.equals(adminAccount));
        assertFalse(studentAccount.equals(null));
        assertFalse(studentAccount.equals(0));
    }

    @Test
    public void usernameMatches() {
        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        assertTrue(student2.usernameMatches(student));
        assertFalse(student2.usernameMatches(admin));
    }
}
```
###### /src/test/java/seedu/address/model/account/MatricNumberTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MatricNumberTest {

    @Test
    public void isValidMatricNumber() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        //invalid
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber("123"));
        assertFalse(MatricNumber.isValidMatricNumber("abc"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("A1234567XX!"));
        assertFalse(MatricNumber.isValidMatricNumber("A123456723X!"));
        assertFalse(MatricNumber.isValidMatricNumber("1234567XX!"));

        //valid
        assertTrue(MatricNumber.isValidMatricNumber("A1234567Z"));
        assertTrue(MatricNumber.isValidMatricNumber("A9992567B"));
    }

    @Test
    public void getMatricNumber() {
        String matricNumberString = "A1234567Z";
        MatricNumber m = new MatricNumber(matricNumberString);
        assertEquals(matricNumberString, m.getMatricNumber());
    }

    @Test
    public void equals() {
        MatricNumber p1 = new MatricNumber("A1234567Z");
        MatricNumber p1copy = new MatricNumber("A1234567Z");
        MatricNumber p2 = new MatricNumber("A9992567B");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /src/test/java/seedu/address/model/account/CredentialTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//import javax.jws.soap.SOAPBinding;

public class CredentialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Credential(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Credential("username", null));
        Assert.assertThrows(NullPointerException.class, () -> new Credential(null, "password"));
    }


    @Test
    public void getPassword_and_getUsername() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        String usernameString = "username";
        Username u = new Username(usernameString);
        Credential c = new Credential(usernameString, passwordString);
        assertEquals(c.getPassword(), p);
        assertEquals(c.getUsername(), u);
    }

    @Test
    public void equals() {
        String u1 = "username1";
        String u1copy = "username1";
        String u2 = "username2";
        String p1 = "password1";
        String p1copy = "password1";
        String p2 = "password2";
        Credential c1 = new Credential(u1, p1);
        Credential c1copy = new Credential(u1copy, p1copy);
        Credential c2 = new Credential(u2, p2);

        //equal with itself
        assertTrue(c1.equals(c1));

        //equal with an other object with same state
        assertTrue(c1.equals(c1copy));

        //not equal with null
        assertFalse(c1.equals(null));

        //not equal with other type
        assertFalse(c1.equals(1));

        //not equal with same type with different state
        assertFalse(c1.equals(c2));
    }

    @Test
    public void usernameEquals() {
        String u1 = "username1";
        String u2 = "username2";
        String p1 = "password1";
        Credential u1p1 = new Credential(u1, p1);
        Credential u1p2 = new Credential(u1, p1);
        Credential u2p1 = new Credential(u2, p1);
        Username username1 = u1p1.getUsername();
        Username username2 = u2p1.getUsername();

        assertTrue(u1p1.usernameEquals(username1));
        assertTrue(u1p2.usernameEquals(username1));
        assertFalse(u1p1.usernameEquals(username2));
    }
}
```
###### /src/test/java/seedu/address/model/account/UniqueAccountListTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.testutil.Assert;

public class UniqueAccountListTest {
    @Test
    public void add() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();

        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);

        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        Assert.assertThrows(DuplicateAccountException.class, ()
            -> accountList.add(student2));
    }

    @Test
    public void searchByUsername() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        assertEquals(accountList.searchByUsername(new Username("student")), student);
    }
}
```
###### /src/test/java/seedu/address/model/account/PrivilegeLevelTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PrivilegeLevelTest {

    @Test
    public void constructor_invalidPrivilegeLevel_throwsIllegalArgumentException() {
        final int invalidPrivilegeLevel1 = 3;
        final int invalidPrivilegeLevel2 = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel1));
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel2));
    }

    @Test
    public void isValidPrivilegeLevel_test() {
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(0));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(1));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(2));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(3));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(-1));
    }

    @Test
    public void equals() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p0copy = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p0.equals(p0copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }


    @Test
    public void compareTo() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        assertTrue(p0.compareTo(p1) < 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p0) > 0);
        assertTrue(p1.compareTo(p1) == 0);
    }
}
```
###### /src/test/java/seedu/address/model/account/PasswordTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void isValidPassword() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword("123")); // too short
        assertFalse(Password.isValidPassword("abc")); // too short
        assertFalse(Password.isValidPassword("!!!")); // too short and non-word characters
        assertFalse(Password.isValidPassword("!!!!!!")); // non-word characters
        assertFalse(Password.isValidPassword("abcasj!")); // too short and non-word characters
        assertFalse(Password.isValidPassword(""));

        //valid
        assertTrue(Password.isValidPassword("abcde"));
        assertTrue(Password.isValidPassword("banana"));
        assertTrue(Password.isValidPassword("addressbook"));
        assertTrue(Password.isValidPassword("abcde123"));
        assertTrue(Password.isValidPassword("FHAIgasjd123987514"));
        assertTrue(Password.isValidPassword("123123123123"));


    }

    @Test
    public void getPassword() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        assertEquals(passwordString, p.getPassword());
    }

    @Test
    public void equals() {
        Password p1 = new Password("password1");
        Password p1copy = new Password("password1");
        Password p2 = new Password("password2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /src/main/java/seedu/address/commons/events/model/AccountListChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.account.UniqueAccountList;

/**
 * Indicates the AccountList in the model has changed
 */
public class AccountListChangedEvent extends BaseEvent {

    public final UniqueAccountList data;

    public AccountListChangedEvent(UniqueAccountList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Number of accounts: " + data.size();
    }
}
```
###### /src/main/java/seedu/address/logic/parser/LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords.length != 2
            || !Username.isValidUsername(nameKeywords[0])
            || !Password.isValidPassword(nameKeywords[1])) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = nameKeywords[0];
        String password = nameKeywords[1];

        return new LoginCommand(username, password);
    }
}
```
###### /src/main/java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/ListCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/RedoCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/LoginCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;

/**
 * Logs in as student or librarian.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login as student or librarian.\n"
        + "Parameters: USERNAME PASSWORD(both username and password should be at least 5 chars long)\n"
        + "Example: " + COMMAND_WORD + " MyUsername MyPassword";

    public static final String MESSAGE_LOGGED_IN_AS_STUTENT = "You are logged in as student";
    public static final String MESSAGE_LOGGED_IN_AS_LIBRARIAN = "You are logged in as librarian";
    public static final String MESSAGE_NOT_LOGGED_IN = "Wrong username/password, try again";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_GUEST;


    private final Credential credential;


    public LoginCommand(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        this.credential = new Credential(username, password);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoginCommand // instanceof handles nulls
            && credential.equals(((LoginCommand) other).credential));
    }

    @Override
    public CommandResult execute() {
        PrivilegeLevel newPrivilegeLevel = model.authenticate(this.credential);
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_GUEST)) {
            return new CommandResult(MESSAGE_NOT_LOGGED_IN);
        }
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_STUDENT)) {
            return new CommandResult(MESSAGE_LOGGED_IN_AS_STUTENT);
        }
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_LIBRARIAN)) {
            return new CommandResult(MESSAGE_LOGGED_IN_AS_LIBRARIAN);
        }
        return new CommandResult(MESSAGE_NOT_LOGGED_IN);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/ClearCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/Command.java
``` java
    public PrivilegeLevel getPrivilegeLevel() {
        return Model.PRIVILEGE_LEVEL_GUEST;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/AddCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/HelpCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/HistoryCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/EditCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
```
###### /src/main/java/seedu/address/logic/commands/SelectCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/UndoCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/FindCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/commands/LogoutCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;

/**
 * Logs out as student or librarian.
 */

public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout as student or librarian.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LOGGED_OUT = "You are logged out.";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_STUDENT;

    @Override
    public CommandResult execute() {
        model.logout();
        return new CommandResult(MESSAGE_LOGGED_OUT);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LogoutCommand);
    }
}
```
###### /src/main/java/seedu/address/logic/commands/ExitCommand.java
``` java
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
```
###### /src/main/java/seedu/address/logic/LogicManager.java
``` java
    protected boolean isPrivileged(Command command) {
        return command.getPrivilegeLevel().compareTo(model.getPrivilegeLevel()) <= 0;
    }
```
###### /src/main/java/seedu/address/storage/Storage.java
``` java
    String getAccountListFilePath();

    Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException;

    void saveAccountList(UniqueAccountList accountList) throws IOException;

    void handleAccountListChangedEvent(AccountListChangedEvent event);
}
```
###### /src/main/java/seedu/address/storage/SerialisedAccountListStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

/**
 * A class to access AccountList data stored as an .ser file on the hard disk.
 */
public class SerialisedAccountListStorage implements AccountListStorage {
    private static final Logger logger = LogsCenter.getLogger(SerialisedAccountListStorage.class);

    private String filePath;

    public SerialisedAccountListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAccountListFilePath() {
        return filePath;
    }

    @Override
    public Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(filePath);
    }

    @Override
    public Optional<UniqueAccountList> readAccountList(String filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        FileInputStream file = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(file);

        if (!new File(filePath).exists()) {
            logger.info("AccountList file " + filePath + " not found");
            return Optional.empty();
        }

        UniqueAccountList accountList = SerialisedFileStorage.loadDataFromSaveFile(in);
        return Optional.of(accountList);
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList) throws IOException {
        saveAccountList(accountList, filePath);
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList, String filePath) throws IOException {
        requireNonNull(accountList);
        requireNonNull(filePath);

        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);
        SerialisedFileStorage.saveDataToFile(out, accountList);
        out.close();
        file.close();
    }
}
```
###### /src/main/java/seedu/address/storage/StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleAccountListChangedEvent(AccountListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "AccountList data changed, saving to file"));
        try {
            saveAccountList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public String getAccountListFilePath() {
        return accountListStorage.getAccountListFilePath();
    }

    @Override
    public Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(accountListStorage.getAccountListFilePath());
    }

    @Override
    public Optional<UniqueAccountList> readAccountList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accountListStorage.readAccountList(filePath);
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList) throws IOException {
        saveAccountList(accountList, accountListStorage.getAccountListFilePath());
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        accountListStorage.saveAccountList(accountList, filePath);
    }

}
```
###### /src/main/java/seedu/address/storage/AccountListStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

//import java.util.Optional;

/**
 * Represents a storage for {@link UniqueAccountList}.
 */
public interface AccountListStorage {
    /**
     * Returns the file path of the data file.
     */
    String getAccountListFilePath();

    /**
     * Returns AccountList data as a {@link UniqueAccountList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException;

    /**
     * @see #getAccountListFilePath()
     */
    Optional<UniqueAccountList> readAccountList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UniqueAccountList} to the storage.
     *
     * @param accountList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountList(UniqueAccountList accountList) throws IOException;

    /**
     * @see #saveAccountList(UniqueAccountList)
     */
    void saveAccountList(UniqueAccountList accountList, String filePath) throws IOException;

}
```
###### /src/main/java/seedu/address/storage/SerialisedFileStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

/**
 * Stores accountList data in a .ser file
 */
public class SerialisedFileStorage {
    /**
     * Saves the given catalogue data to the specified file.
     */
    public static void saveDataToFile(ObjectOutputStream out, UniqueAccountList accountList) {
        try {
            out.writeObject(accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns catalogue in the file or an empty catalogue
     */
    public static UniqueAccountList loadDataFromSaveFile(ObjectInputStream in) throws DataConversionException {
        try {
            return (UniqueAccountList) in.readObject();
        } catch (IOException e) {
            throw new DataConversionException(e);
        } catch (ClassNotFoundException e) {
            throw new DataConversionException(e);
        }
    }
}
```
###### /src/main/java/seedu/address/MainApp.java
``` java
        try {
            catalogueOptional = storage.readCatalogue();
            if (!catalogueOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Catalogue");
            }
            initialData = catalogueOptional.orElseGet(SampleDataUtil::getSampleCatalogue);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Catalogue");
            initialData = new Catalogue();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Catalogue");
            initialData = new Catalogue();
        }

        try {
            accountListOptional = storage.readAccountList();
            if (!accountListOptional.isPresent()) {
                logger.info("AccountList file not found. Will be starting with an accountList with only admin");
                initlaAccountList = new UniqueAccountList();
            } else {
                initlaAccountList = accountListOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("AccountList file not in the correct format. "
                + "Will be starting with an accountList with only admin");
            initlaAccountList = new UniqueAccountList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the AccountList file. "
                + "Will be starting with an accountList with only admin");
            System.out.print(e.getMessage());
            initlaAccountList = new UniqueAccountList();
        }

        try {
            if (!initlaAccountList.contains(Account.createDefaultAdminAccount())) {
                initlaAccountList.add(Account.createDefaultAdminAccount());
            }
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
        }
        return new ModelManager(initialData, initlaAccountList, userPrefs);
```
###### /src/main/java/seedu/address/model/UserPrefs.java
``` java
    public String getAccountListFilePath() {
        return accountListFilePath;
    }

    public void setAccountListFilePath(String accountListFilePath) {
        this.accountListFilePath = accountListFilePath;
    }
```
###### /src/main/java/seedu/address/model/ModelManager.java
``` java
    /**
     * Initializes a ModelManager with the given catalogue, accountList and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue catalogue, UniqueAccountList accountList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(catalogue, accountList, userPrefs);

        logger.fine("Initializing with catalogue: " + catalogue
                            + ", accountList: " + accountList
                            + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(catalogue);
        filteredBooks = new FilteredList<>(this.catalogue.getBookList());
        this.accountList = accountList;
        this.currentAccount = Account.createGuestAccount();
    }
```
###### /src/main/java/seedu/address/model/ModelManager.java
``` java
    /**
     * Adds an account to the AccountList
     * @param account
     * @throws DuplicateAccountException
     */
    public void addAccount(Account account) throws DuplicateAccountException {
        accountList.add(account);
        indicateAccountListChanged();
    }

    /**
     * Deletes an account from the AccountList
     * @param account
     * @throws AccountNotFoundException
     */
    public void deleteAccount(Account account) throws AccountNotFoundException {
        accountList.remove(account);
        indicateAccountListChanged();
    }

    /**
     * Replaces an account with a new one
     * @param account
     * @param editedAccount
     * @throws DuplicateAccountException
     * @throws AccountNotFoundException
     */
    public void updateAccount(Account account, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException {
        accountList.setAccount(account, account);
        indicateAccountListChanged();
    }

    /**
     * Adds the initial admin account to the accountList
     */
    private void addFirstAccount() {
        Account admin = Account.createDefaultAdminAccount();
        if (!this.accountList.contains(admin)) {
            try {
                this.accountList.add(admin);
            } catch (DuplicateAccountException e) {
                e.printStackTrace();
            }
        }
    }
```
###### /src/main/java/seedu/address/model/ModelManager.java
``` java
    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAccountListChanged() {
        raise(new AccountListChangedEvent(accountList));
    }
```
###### /src/main/java/seedu/address/model/ModelManager.java
``` java
    @Override
    public PrivilegeLevel authenticate(Credential c) {
        Account matched = accountList.authenticate(c);
        if (matched != null) {
            this.currentAccount = matched;
            return currentAccount.getPrivilegeLevel();
        }
        //if not found
        return PRIVILEGE_LEVEL_GUEST;
    }

    @Override
    public void logout() {
        currentAccount = Account.createGuestAccount();
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return this.currentAccount.getPrivilegeLevel();
    }
```
###### /src/main/java/seedu/address/model/account/UniqueAccountList.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.model.account.exceptions.AccountNotFoundException;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Account#equals(Object)
 */
public class UniqueAccountList implements Serializable, Iterable<Account> {
    private final ArrayList<Account> internalList = new ArrayList<Account>();

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a account to the list.
     *
     * @throws DuplicateAccountException if the account to add is an account with the same username in the list.
     */
    public void add(Account toAdd) throws DuplicateAccountException {
        requireNonNull(toAdd);
        if (containsUsername(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}.
     *
     * @throws DuplicateAccountException if the replacement is equivalent to another existing account in the list.
     * @throws AccountNotFoundException  if {@code target} could not be found in the list.
     */
    public void setAccount(Account target, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException {
        requireNonNull(editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccountNotFoundException();
        }

        if (!target.usernameMatches(editedAccount) && this.containsUsername(target)) {
            throw new DuplicateAccountException();
        }

        internalList.set(index, editedAccount);
    }

    /**
     * Removes the equivalent account from the list.
     *
     * @throws AccountNotFoundException if no such account could be found in the list.
     */
    public boolean remove(Account toRemove) throws AccountNotFoundException {
        requireNonNull(toRemove);
        final boolean accountFoundAndDeleted = internalList.remove(toRemove);
        if (!accountFoundAndDeleted) {
            throw new AccountNotFoundException();
        }
        return accountFoundAndDeleted;
    }

    /**
     * Returns the account that matches with the provided credential,
     * returns null if none exists
     *
     * @param c
     * @return
     */
    public Account authenticate(Credential c) {
        for (Account a : internalList) {
            if (a.credentialMatches(c)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Returns true if there is an account with the username provided
     * @param u
     * @return
     */
    public boolean containsUsername(Username u) {
        for (Account a : internalList) {
            if (a.usernameMatches(u)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there is an account with an username that is the
     * same as that of the credential provided
     * @param c
     * @return
     */
    public boolean containsUsername(Credential c) {
        return containsUsername(c.getUsername());
    }

    /**
     * Returns true if there is an account with an username that is the
     * same as that of the account provided
     * @param a
     * @return
     */
    public boolean containsUsername(Account a) {
        return containsUsername(a.getCredential());
    }

    /**
     * Returns the account if there is an account with the username provided
     * @param u
     * @return
     */
    public Account searchByUsername(Username u) {
        for (Account a : internalList) {
            if (a.usernameMatches(u)) {
                return a;
            }
        }
        return null;
    }


    public int size() {
        return internalList.size();
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueAccountList // instanceof handles nulls
            && this.internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
```
###### /src/main/java/seedu/address/model/account/PrivilegeLevel.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the privilegeLevel of an account/a command
 */
public class PrivilegeLevel implements Comparable<PrivilegeLevel>, Serializable {

    public static final int PRIVILEGE_LEVEL_GUEST = 0;
    public static final int PRIVILEGE_LEVEL_STUDENT = 1;
    public static final int PRIVILEGE_LEVEL_LIBRARIAN = 2;
    public static final String MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS =
        "Privilege Level should be an integer from 0 to 2 inclusive.";
    private final int privilegeLevel;

    /**
     * Constructs a PrivilegeLevel
     *
     * @param privilegeLevel
     */
    public PrivilegeLevel(int privilegeLevel) {
        requireNonNull(privilegeLevel);
        checkArgument(isValidPrivilegeLevel(privilegeLevel), MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS);
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Returns true if a given string is a valid PrivilegeLevel
     */
    public static boolean isValidPrivilegeLevel(int test) {
        return test >= PRIVILEGE_LEVEL_GUEST
            && test <= PRIVILEGE_LEVEL_LIBRARIAN;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrivilegeLevel that = (PrivilegeLevel) o;
        return privilegeLevel == that.privilegeLevel;
    }

    @Override
    public int hashCode() {

        return Objects.hash(privilegeLevel);
    }


    @Override
    public int compareTo(PrivilegeLevel o) {
        return this.privilegeLevel - o.privilegeLevel;
    }
}
```
###### /src/main/java/seedu/address/model/account/MatricNumber.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;

/**
 * Represents a set of username and password
 */
public class MatricNumber implements Serializable {
    public static final String MESSAGE_MATRIC_NUMBER_CONSTRAINTS =
        "Matriculation number should start with \"A\", followed by 7 digits and end with uppercase letter.";

    public static final String MATRIC_NUMBER_VALIDATION_REGEX = "A[0-9]{7}[A-Z]";


    private final String matricNumber;

    /**
     * Constructs a {@code Credential}
     *
     * @param matricNumber A valid matric number
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        this.matricNumber = matricNumber;
    }

    /**
     * Returns true if a given string is a valid MatricNumber.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(MATRIC_NUMBER_VALIDATION_REGEX);
    }

    /**
     * Returns MatricNumber.
     */
    public String getMatricNumber() {
        return matricNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof MatricNumber // short circuit if same obj
            && this.getMatricNumber().equals(((MatricNumber) other).getMatricNumber()) //check status
            );
    }

    @Override
    public String toString() {
        return matricNumber;
    }

    @Override
    public int hashCode() {
        return matricNumber.hashCode();
    }
}
```
###### /src/main/java/seedu/address/model/account/Username.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;

/**
 * Represents the username of an account
 */
public class Username implements Serializable {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
        "Username should be at least 5 characters long.";
    public static final String USERNAME_VALIDATION_REGEX = "\\w{5,}";

    private final String username;

    /**
     * Constructs a Username
     *
     * @param username
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);

        this.username = username;
    }

    /**
     * Returns true if a given string is a valid Username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }


    /**
     * Returns username.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Username // short circuit if same obj
            && this.username.equals(((Username) other).username) // check username
            );
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "Username{"
            + "username='" + username + '\''
            + '}';
    }
}
```
###### /src/main/java/seedu/address/model/account/exceptions/AccountNotFoundException.java
``` java
package seedu.address.model.account.exceptions;

/**
 * Signals that the operation is unable to find the specified account.
 */
public class AccountNotFoundException extends Exception {
}
```
###### /src/main/java/seedu/address/model/account/exceptions/DuplicateAccountException.java
``` java
package seedu.address.model.account.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Book objects.
 */
public class DuplicateAccountException extends DuplicateDataException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate books");
    }
}
```
###### /src/main/java/seedu/address/model/account/Account.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an account in the accountBook
 */
public class Account implements Serializable {
    private final Name name;
    private final Credential credential;
    private final MatricNumber matricNumber;
    private final PrivilegeLevel privilegeLevel;

    /**
     * Constructs an Account
     *
     * @param name
     * @param credential
     * @param matricNumber
     * @param privilegeLevel
     */
    public Account(Name name, Credential credential, MatricNumber matricNumber, PrivilegeLevel privilegeLevel) {
        requireNonNull(name);
        requireNonNull(credential);
        requireNonNull(matricNumber);
        requireNonNull(privilegeLevel);
        this.name = name;
        this.credential = credential;
        this.matricNumber = matricNumber;
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Returns a sample guest account
     * @return
     */
    public static final Account createGuestAccount() {
        Name name = new Name("Guest");
        Credential credential = new Credential("Guest", "Guest");
        MatricNumber matricNumber = new MatricNumber("A0000000X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(0);
        Account guest = new Account(name, credential, matricNumber, privilegeLevel);
        return guest;
    }

    /**
     * Returns a sample admin account
     * @return
     */
    public static final Account createDefaultAdminAccount() {
        Name name = new Name("Alice");
        Credential credential = new Credential("admin", "admin");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(2);
        Account admin = new Account(name, credential, matricNumber, privilegeLevel);
        return admin;
    }

    /**
     * Returns a sample student account
     * @return
     */
    public static final Account createDefaultStudentAccount() {
        Name name = new Name("Bob");
        Credential credential = new Credential("student", "student");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account student = new Account(name, credential, matricNumber, privilegeLevel);
        return student;
    }

    /**
     * Returns the name of the account
     * @return
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the credential
     * @return
     */
    public Credential getCredential() {
        return credential;
    }

    /**
     * Returns the MatricNumber
     * @return
     */
    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    /**
     * Returns the privilegeLevel of this account
     * @return
     */
    public PrivilegeLevel getPrivilegeLevel() {
        return privilegeLevel;
    }

    /**
     * Returns a boolean indicating whether a given credential matches with that of this account
     * @param c
     * @return
     */
    public boolean credentialMatches(Credential c) {
        return c.equals(this.credential);
    }

    /**
     * Returns true if this account's username is the same as the username provided
     * @param username
     * @return
     */
    public boolean usernameMatches(Username username) {
        return this.credential.usernameEquals(username);
    }

    /**
     * Returns true if this account's username is the same as that of the credential provided
     * @param c
     * @return
     */
    public boolean usernameMatches(Credential c) {
        return usernameMatches(c.getUsername());
    }

    /**
     * Returns true if this account's username is the same as that of the account provided
     * @param a
     * @return
     */
    public boolean usernameMatches(Account a) {
        return usernameMatches(a.getCredential());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(name, account.name)
            && Objects.equals(credential, account.credential)
            && Objects.equals(matricNumber, account.matricNumber)
            && Objects.equals(privilegeLevel, account.privilegeLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, credential, matricNumber, privilegeLevel);
    }
}
```
###### /src/main/java/seedu/address/model/account/Credential.java
``` java
package seedu.address.model.account;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a set of username and password
 */
public class Credential implements Serializable {

    private Username username;
    private Password password;
    /**
     * Constructs a {@code Credential}
     *
     * @param username A valid username
     * @param password A valid password
     */
    public Credential(String username, String password) {
        this.username = new Username(username);
        this.password = new Password(password);
    }

    /**
     * Returns username
     */
    public Username getUsername() {
        return username;
    }

    /**
     * Returns password
     */
    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if the username provided equals to this.username
     * @param username
     * @return
     */
    public boolean usernameEquals(Username username) {
        return this.username.equals(username);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Credential // short circuit if same obj
            && this.username.equals(((Credential) other).username) // check username
            && this.password.equals(((Credential) other).password) //check password
            );
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Credential{"
            + "username=" + username
            + ", password=" + password
            + '}';
    }
}

```
###### /src/main/java/seedu/address/model/account/Password.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;

/**
 * Represents a password
 */
public class Password implements Serializable {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
        "Password should be at least 5 characters long.";
    public static final String PASSWORD_VALIDATION_REGEX = "\\w{5,}";

    private final String password;


    /**
     * Construct a password
     *
     * @param password
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);

        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Password{"
            + "password='" + password + '\''
            + '}';
    }

    /**
     * Returns password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Password // short circuit if same obj
            && this.password.equals(((Password) other).password)); //check password
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
```
###### /src/main/java/seedu/address/model/Model.java
``` java
    void addAccount(Account account) throws DuplicateAccountException;

    void deleteAccount(Account account) throws AccountNotFoundException;

    void updateAccount(Account account, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException;

    PrivilegeLevel authenticate(Credential credential);

    void logout();

    PrivilegeLevel getPrivilegeLevel();
```
