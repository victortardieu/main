package seedu.address.model.book;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
            && this.keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
