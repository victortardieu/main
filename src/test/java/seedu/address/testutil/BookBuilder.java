package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Phone;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_TITLE = "Alice Pauline";
    public static final String DEFAULT_AUTHOR = "Pauline Alice";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_AVAIL = "Borrowed";
    public static final String DEFAULT_TAGS = "friends";

    private Title title;
    private Phone phone;
    private Avail avail;
    private Author author;
    private Set<Tag> tags;

    public BookBuilder() {
        title = new Title(DEFAULT_TITLE);
        author = new Author(DEFAULT_AUTHOR);
        phone = new Phone(DEFAULT_PHONE);
        avail = new Avail(DEFAULT_AVAIL);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        title = bookToCopy.getTitle();
        author = bookToCopy.getAuthor();
        phone = bookToCopy.getPhone();
        avail = bookToCopy.getAvail();
        tags = new HashSet<>(bookToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Book} that we are building.
     */
    public BookBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code Book} that we are building.
     */
    public BookBuilder withAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Book} that we are building.
     */
    public BookBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Avail} of the {@code Book} that we are building.
     */
    public BookBuilder withAvail(String avail) {
        this.avail = new Avail(avail);
        return this;
    }

    public Book build() {
        return new Book(title, author, phone, avail, tags);
    }

}
