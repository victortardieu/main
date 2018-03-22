package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.UniquePersonList;
import seedu.address.model.book.exceptions.DuplicatePersonException;
import seedu.address.model.book.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Catalogue implements ReadOnlyCatalogue {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public Catalogue() {}

    /**
     * Creates an Catalogue using the Persons and Tags in the {@code toBeCopied}
     */
    public Catalogue(ReadOnlyCatalogue toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Book> books) throws DuplicatePersonException {
        this.persons.setPersons(books);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code Catalogue} with {@code newData}.
     */
    public void resetData(ReadOnlyCatalogue newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Book> syncedBookList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedBookList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Catalogue should not have duplicate persons");
        }
    }

    //// book-level operations

    /**
     * Adds a book to the catalogue.
     * Also checks the new book's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the book to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent book already exists.
     */
    public void addPerson(Book p) throws DuplicatePersonException {
        Book book = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        persons.add(book);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedPerson}.
     * {@code Catalogue}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the book's details causes the book to be equivalent to
     *      another existing book in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Book)
     */
    public void updatePerson(Book target, Book editedBook)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedBook);

        Book syncedEditedBook = syncWithMasterTagList(editedBook);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        persons.setPerson(target, syncedEditedBook);
    }

    /**
     *  Updates the master tag list to include tags in {@code book} that are not in the list.
     *  @return a copy of this {@code book} such that every tag in this book points to a Tag object in the master
     *  list.
     */
    private Book syncWithMasterTagList(Book book) {
        final UniqueTagList personTags = new UniqueTagList(book.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking book tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of book tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Book(
                book.getName(), book.getPhone(), book.getEmail(), book.getAddress(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code Catalogue}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code Catalogue}.
     */
    public boolean removePerson(Book key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Catalogue // instanceof handles nulls
                && this.persons.equals(((Catalogue) other).persons)
                && this.tags.equalsOrderInsensitive(((Catalogue) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
