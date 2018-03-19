package seedu.address.model.person;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ID {

    public static final String MESSAGE_ID_CONSTRAINTS =
            "Person ID should start with the letter s if student or l if librarian";

    public static final String ID_VALIDATION_REGEX = "\\d{3,}";
    public final String fullID;

    public ID(String id) {
        requireNonNull(id);
        checkArgument(isValidID(id), MESSAGE_ID_CONSTRAINTS);
        this.fullID = id;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidID(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.fullID.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return fullID.hashCode();
    }
}
