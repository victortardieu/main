package seedu.address.model.account;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a set of username and password
 */
public class Credential implements Serializable {

    /**
     * Constructs a {@code Credential}
     *
     * @param username A valid username
     * @param password A valid password
     */

    Username username;
    Password password;

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

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Credential // short circuit if same obj
            && this.username.equals(((Credential) other).username) // check username
            && this.password.equals(((Credential) other).password)
        ); //check password
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Credential{" +
            "username=" + username +
            ", password=" + password +
            '}';
    }
}
