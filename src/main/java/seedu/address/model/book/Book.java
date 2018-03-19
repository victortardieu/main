package seedu.address.model.book;
import java.lang.String;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Book {

    private final String title;
    private final String author;
    private boolean isBorrowed;
    private boolean isReserved;

    private final UniqueTagList tags;

    public Book(String title, String author, boolean isBorrowed, boolean isReserved, Set<Tag> tags) {
        requireAllNonNull(title, author, isBorrowed, isReserved, tags);
        this.title=title;
        this.author=author;
        this.isBorrowed=isBorrowed;
        this.isReserved=isReserved;
        this.tags = new UniqueTagList(tags);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public boolean isReserved() { return isReserved; }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(this.getTitle())
                && otherBook.getAuthor().equals(this.getAuthor());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, author, isBorrowed, isReserved, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Author: ")
                .append(getAuthor())
                .append(" Borrowed: ")
                .append(isBorrowed())
                .append(" Reserved: ")
                .append(isReserved())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
