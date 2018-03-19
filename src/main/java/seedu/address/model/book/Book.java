package seedu.address.model.book;
import java.lang.String;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class Book {

    private final String title;
    private final String author;
    private final boolean isBorrowed;

    private final UniqueTagList tags;

    public Book(String title, String author, boolean isBorrowed, Set<Tag> tags) {
        this.title=title;
        this.author=author;
        this.isBorrowed=isBorrowed;
        this.tags = new UniqueTagList(tags);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public UniqueTagList getTags() {
        return tags;
    }

    public boolean isBorrowed() {
        return isBorrowed;
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

}
