package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password can only contain numbers, and should be at least 5 digits long";
    public static final String PASSWORD_VALIDATION_REGEX = "\\d{3,}";
    public final String fullPassword;

    /**
     * Constructs a {@code Phone}.
     *
     * @param password A valid phone number.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.fullPassword = password;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.fullPassword.equals(((Password) other).fullPassword)); // state check
    }

    @Override
    public int hashCode() {
        return fullPassword.hashCode();
    }
}
