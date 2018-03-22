package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicatePersonException;
import seedu.address.model.book.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyCatalogue newData);

    /** Returns the Catalogue */
    ReadOnlyCatalogue getCatalogue();

    /** Deletes the given book. */
    void deletePerson(Book target) throws PersonNotFoundException;

    /** Adds the given book */
    void addPerson(Book book) throws DuplicatePersonException;

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     *
     * @throws DuplicatePersonException if updating the book's details causes the book to be equivalent to
     *      another existing book in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Book target, Book editedBook)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredPersonList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Book> predicate);

}
