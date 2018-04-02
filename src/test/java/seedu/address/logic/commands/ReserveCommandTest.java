package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

public class ReserveCommandTest {
    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIFTH_BOOK.getZeroBased());
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIFTH_BOOK);

        String expectedMessage = String.format(ReserveCommand.MESSAGE_SUCCESS, bookToReserve);
        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.reserveBook(bookToReserve);
        assertCommandSuccess (reserveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(ReserveCommand.MESSAGE_SUCCESS, bookToReserve);

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.reserveBook(bookToReserve);

        assertCommandSuccess(reserveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // reserve -> first book reserved
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book reserved again
        expectedModel.reserveBook(bookToReserve);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> reserveCommand not pushed into undoRedoStack
        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookReserved() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToReserve  = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());

        // reserve -> reserves second book in unfiltered book list / first book in filtered book list
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.reserveBook(bookToReserve);
        assertNotEquals(bookToReserve, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> reserves same second book in unfiltered book list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        ReserveCommand reserveFirstCommand = prepareCommand(INDEX_FIRST_BOOK);
        ReserveCommand reserveSecondCommand = prepareCommand(INDEX_SECOND_BOOK);

        // same values -> returns true
        ReserveCommand reserveFirstCommandCopy = prepareCommand(INDEX_FIRST_BOOK);
        assertTrue(reserveFirstCommand.equals(reserveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        reserveFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(reserveFirstCommand.equals(reserveFirstCommandCopy));

        // different types -> returns false
        assertFalse(reserveFirstCommand.equals(1));

        // different book -> returns false
        assertFalse(reserveFirstCommand.equals(reserveSecondCommand));
    }

    /**
     * Returns a {@code ReturnCommand} with the parameter {@code index}.
     */
    private ReserveCommand prepareCommand(Index index) {
        ReserveCommand reserveCommand = new ReserveCommand(index);
        reserveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reserveCommand;
    }
}
