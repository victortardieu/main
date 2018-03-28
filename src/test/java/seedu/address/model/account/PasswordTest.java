package seedu.address.model.account;

import static org.junit.Assert.*;
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
        assertFalse(Password.isValidPassword("abcde"));

        //valid
        assertTrue(Password.isValidPassword("abcde"));
        assertTrue(Password.isValidPassword("banana"));
        assertTrue(Password.isValidPassword("addressbook"));
        assertTrue(Password.isValidPassword("abcde123"));
        assertTrue(Password.isValidPassword("FHAIgasjd123987514"));
        assertTrue(Password.isValidPassword("123123123123"));
        assertTrue(Password.isValidPassword(""));

    }

    @Test
    public void getPassword() {
    }

    @Test
    public void equals() {
    }
}