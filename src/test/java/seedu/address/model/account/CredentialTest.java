package seedu.address.model.account;

import static org.junit.Assert.*;
import org.junit.Test;
import seedu.address.testutil.Assert;

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
}