package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Phone;
import seedu.address.model.book.Title;

import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Book.
 */
public class XmlAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String author;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String avail;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedBook.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBook() {}

    /**
     * Constructs an {@code XmlAdaptedBook} with the given book details.
     */
    public XmlAdaptedBook(String title, String author, String phone, String avail, List<XmlAdaptedTag> tagged) {
        this.title = title;
        this.author = author;
        this.phone = phone;
        this.avail = avail;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Book into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBook
     */
    public XmlAdaptedBook(Book source) {
        title = source.getTitle().fullTitle;
        author = source.getAuthor().value;
        phone = source.getPhone().value;
        avail = source.getAvail().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted book object into the model's Book object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> bookTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            bookTags.add(tag.toModelType());
        }

        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(this.title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title title = new Title(this.title);

        if (this.author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(this.author)) {
            throw new IllegalValueException(Author.MESSAGE_AUTHOR_CONSTRAINTS);
        }
        final Author author = new Author(this.author);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.avail == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Avail.class.getSimpleName()));
        }
        if (!Avail.isValidAvail(this.avail)) {
            throw new IllegalValueException(Avail.MESSAGE_AVAIL_CONSTRAINTS);
        }
        final Avail avail = new Avail(this.avail);

        final Set<Tag> tags = new HashSet<>(bookTags);
        return new Book(title, author, phone, avail, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBook)) {
            return false;
        }

        XmlAdaptedBook otherBook = (XmlAdaptedBook) other;
        return Objects.equals(title, otherBook.title)
                && Objects.equals(author, otherBook.author)
                && Objects.equals(phone, otherBook.phone)
                && Objects.equals(avail, otherBook.avail)
                && tagged.equals(otherBook.tagged);
    }
}
