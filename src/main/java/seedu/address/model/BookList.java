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
import seedu.address.model.book.UniqueBookList;
import seedu.address.model.book.DuplicateBookException;
import seedu.address.model.book.BookNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class BookList implements ReadOnlyBookList {

    private final UniqueBookList books;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        books = new UniqueBookList();
        tags = new UniqueTagList();
    }

    public BookList() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public BookList(ReadOnlyBookList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setBooks(List<Book> books) throws DuplicateBookException {
        this.books.setBooks(books);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code BookList} with {@code newData}.
     */
    public void resetData(ReadOnlyBookList newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Book> syncedBookList = newData.getBookList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setBooks(syncedBookList);
        } catch (DuplicateBookException e) {
            throw new AssertionError("BookList should not have duplicate book");
        }
    }

    //// book-level operations

    /**
     * Adds a book to the BookList.
     * Also checks the new book's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the book to point to those in {@link #tags}.
     *
     * @throws DuplicateBookException if an equivalent book already exists.
     */
    public void addBook(Book b) throws DuplicateBookException {
        Book book = syncWithMasterTagList(b);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        books.add(book);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicateBookException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws BookNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Book)
     */
    public void updateBook(Book target, Book editedBook)
            throws DuplicateBookException, BookNotFoundException {
        requireNonNull(editedBook);

        Book syncedEditedBook = syncWithMasterTagList(editedBook);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        books.setBook(target, syncedEditedBook);
    }

    /**
     *  Updates the master tag list to include tags in {@code book} that are not in the list.
     *  @return a copy of this {@code book} such that every tag in this book points to a Tag object in the master
     *  list.
     */
    private Book syncWithMasterTagList(Book book) {
        final UniqueTagList bookTags = new UniqueTagList(book.getTags());
        tags.mergeFrom(bookTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        bookTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Book(
                book.getTitle(), book.getAuthor(), book.isBorrowed(), book.isReserved(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code BookList}.
     * @throws BookNotFoundException if the {@code key} is not in this {@code BookList}.
     */
    public boolean removeBook(Book key) throws BookNotFoundException {
        if (books.remove(key)) {
            return true;
        } else {
            throw new BookNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return books.asObservableList().size() + " books, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookList // instanceof handles nulls
                && this.books.equals(((BookList) other).books)
                && this.tags.equalsOrderInsensitive(((BookList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(books, tags);
    }
}
