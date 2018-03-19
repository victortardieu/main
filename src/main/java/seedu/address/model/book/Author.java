package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


public class Author {

    public static final String MESSAGE_AUTHOR_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AUTHOR_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullAuthor;

    /**
     * Constructs a {@code Author}.
     *
     * @param author A valid name.
     */
    public Author(String author) {
        requireNonNull(author);
        checkArgument(isValidName(author), MESSAGE_AUTHOR_CONSTRAINTS);
        this.fullAuthor = author;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(AUTHOR_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullAuthor;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && this.fullAuthor.equals(((Author) other).fullAuthor)); // state check
    }

    @Override
    public int hashCode() {
        return fullAuthor.hashCode();
    }


}
