package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a set of username and password
 */
public class Credential {
    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username should be at least 5 characters long.";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should be at least 5 characters long.";
    public static final String USERNAME_VALIDATION_REGEX = "\\w{5,}";
    public static final String PASSWORD_VALIDATION_REGEX = "\\w{5,}";

    private final String username;
    private final String password;

    /**
     * Constructs a {@code Credential}
     *
     * @param username A valid username
     * @param password A valid password
     */
    public Credential(String username, String password){
        requireNonNull(username);
        requireNonNull(password);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.username = username;
        this.password = password;
    }

    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Credential // short circuit if same obj
                && this.username.equals(((Credential) other).username) // check username
                && this.password.equals(((Credential) other).password)); //check password
    }

    @Override
    public int hashCode() {
        return (username + password).hashCode();
    }
}
