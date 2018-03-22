package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Catalogue;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Catalogue objects.
 * Example usage: <br>
 *     {@code Catalogue ab = new CatalogueBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class CatalogueBuilder {

    private Catalogue catalogue;

    public CatalogueBuilder() {
        catalogue = new Catalogue();
    }

    public CatalogueBuilder(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Adds a new {@code Person} to the {@code Catalogue} that we are building.
     */
    public CatalogueBuilder withPerson(Person person) {
        try {
            catalogue.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Catalogue} that we are building.
     */
    public CatalogueBuilder withTag(String tagName) {
        try {
            catalogue.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Catalogue build() {
        return catalogue;
    }
}
