package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Book;

/**
 * A utility class for Book.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code book}.
     */
    public static String getAddCommand(Book book) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(book);
    }

    /**
     * Returns the part of command string for the given {@code book}'s details.
     */
    public static String getPersonDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + book.getName().fullName + " ");
        sb.append(PREFIX_PHONE + book.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + book.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + book.getAddress().value + " ");
        book.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
