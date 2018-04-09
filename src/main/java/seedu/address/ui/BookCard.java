package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;
import java.util.Random;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Catalogue level 4</a>
     */

    public final Book book;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label id;
    @FXML
    private Label isbn;
    @FXML
    private Label avail;
    @FXML
    private FlowPane tags;

    public static final String[] COLOR_TAGS =
            {"blue", "brown", "green", "grey", "orange", "pink", "red", "yellow"};

    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        this.book = book;
        id.setText(displayedIndex + ". ");
        title.setText(book.getTitle().fullTitle);
        author.setText(book.getAuthor().value);
        isbn.setText(book.getIsbn().value);
        avail.setText(book.getAvail().value);
        //book.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        colorTags(book);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
                && book.equals(card.book);
    }

    private String getTagColor(Tag tag) {
        Random rand = new Random();
        int sCase = rand.nextInt(COLOR_TAGS.length);
        switch (sCase) {
            case 0:
                return COLOR_TAGS[0];
            case 1:
                return COLOR_TAGS[1];
            case 2:
                return COLOR_TAGS[2];
            case 3:
                return COLOR_TAGS[3];
            case 4:
                return COLOR_TAGS[4];
            case 5:
                return COLOR_TAGS[5];
            case 6:
                return COLOR_TAGS[6];
            default:
                return COLOR_TAGS[6];
        }
    }

    private void colorTags(Book book) {
        book.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tags.getChildren().add(tagLabel);
            //tagLabel.getStyleClass().add(getTagColor(tag));
            tagLabel.getStyleClass().add("green");
        });
    }
}
