package seedu.address.model.account;

import java.util.Objects;

public class Account {
    private final Name name;
    private final Credential credential;
    private final MatricNumber matricNumber;
    private final PrivilegeLevel privilegeLevel;

    /**
     *  Constructs an Account
     * @param name
     * @param credential
     * @param matricNumber
     * @param privilegeLevel
     */
    public Account(Name name, Credential credential, MatricNumber matricNumber, PrivilegeLevel privilegeLevel) {
        this.name = name;
        this.credential = credential;
        this.matricNumber = matricNumber;
        this.privilegeLevel = privilegeLevel;
    }

    public Name getName() {
        return name;
    }

    public Credential getCredential() {
        return credential;
    }

    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    public PrivilegeLevel getPrivilegeLevel() {
        return privilegeLevel;
    }

    public boolean credentialMatches(Credential c) {
        return c.equals(this.credential);
    }

    public static final Account createGuestAccount() {
        Name name = new Name("Guest");
        Credential credential = new Credential("Guest", "Guest");
        MatricNumber matricNumber = new MatricNumber("A0000000X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(0);
        Account guest = new Account(name, credential, matricNumber, privilegeLevel);
        return guest;
    }

    public static final Account createDefaultAdminAccount() {
        Name name = new Name("Alice");
        Credential credential = new Credential("admin", "admin");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(2);
        Account admin = new Account(name, credential, matricNumber, privilegeLevel);
        return admin;
    }

    public static final Account createDefaultStudentAccount() {
        Name name = new Name("Bob");
        Credential credential = new Credential("student", "student");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account admin = new Account(name, credential, matricNumber, privilegeLevel);
        return admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) &&
                Objects.equals(credential, account.credential) &&
                Objects.equals(matricNumber, account.matricNumber) &&
                Objects.equals(privilegeLevel, account.privilegeLevel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, credential, matricNumber, privilegeLevel);
    }
}
