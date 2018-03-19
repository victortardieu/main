package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.book.DuplicateBookException;
import seedu.address.model.book.BookNotFoundException;

public class UniqueBookList implements Iterable<Book> {

    private final ObservableList<Book> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Book toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateBookException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Book toAdd) throws DuplicateBookException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedbook}.
     *
     * @throws DuplicateBookException if the replacement is equivalent to another existing person in the list.
     * @throws BookNotFoundException if {@code target} could not be found in the list.
     */
    public void setBook(Book target, Book editedbook)
            throws DuplicateBookException, BookNotFoundException {
        requireNonNull(editedbook);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }

        if (!target.equals(editedbook) && internalList.contains(editedbook)) {
            throw new DuplicateBookException();
        }

        internalList.set(index, editedbook);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws BookNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Book toRemove) throws BookNotFoundException {
        requireNonNull(toRemove);
        final boolean bookFoundAndDeleted = internalList.remove(toRemove);
        if (!bookFoundAndDeleted) {
            throw new BookNotFoundException();
        }
        return bookFoundAndDeleted;
    }

    public void setBooks(UniqueBookList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setBooks(List<Book> books) throws DuplicateBookException {
        requireAllNonNull(books);
        final UniqueBookList replacement = new UniqueBookList();
        for (final Book book : books) {
            replacement.add(book);
        }
        setBooks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Book> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookList // instanceof handles nulls
                && this.internalList.equals(((UniqueBookList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
