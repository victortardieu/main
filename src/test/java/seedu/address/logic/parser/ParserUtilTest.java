package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Phone;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_AUTHOR = " ";
    private static final String INVALID_AVAIL = "NOT SURE";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_AUTHOR = "Walker Rachel";
    private static final String VALID_AVAIL = "Borrowed";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_BOOK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_BOOK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((Optional<String>) null));
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(INVALID_TITLE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(Optional.of(INVALID_TITLE)));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTitle(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(VALID_TITLE)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(nameWithWhitespace));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parseAuthor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAuthor((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAuthor((Optional<String>) null));
    }

    @Test
    public void parseAuthor_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAuthor(INVALID_AUTHOR));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAuthor(Optional.of(INVALID_AUTHOR)));
    }

    @Test
    public void parseAuthor_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAuthor(Optional.empty()).isPresent());
    }

    @Test
    public void parseAuthor_validValueWithoutWhitespace_returnsAuthor() throws Exception {
        Author expectedAuthor = new Author(VALID_AUTHOR);
        assertEquals(expectedAuthor, ParserUtil.parseAuthor(VALID_AUTHOR));
        assertEquals(Optional.of(expectedAuthor), ParserUtil.parseAuthor(Optional.of(VALID_AUTHOR)));
    }

    @Test
    public void parseAuthor_validValueWithWhitespace_returnsTrimmedAuthor() throws Exception {
        String authorWithWhitespace = WHITESPACE + VALID_AUTHOR + WHITESPACE;
        Author expectedAuthor = new Author(VALID_AUTHOR);
        assertEquals(expectedAuthor, ParserUtil.parseAuthor(authorWithWhitespace));
        assertEquals(Optional.of(expectedAuthor), ParserUtil.parseAuthor(Optional.of(authorWithWhitespace)));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((Optional<String>) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(Optional.of(INVALID_PHONE)));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(VALID_PHONE)));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(phoneWithWhitespace)));

    }

    @Test
    public void parseAvail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAvail((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAvail((Optional<String>) null));
    }

    @Test
    public void parseAvail_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAvail(INVALID_AVAIL));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAvail(Optional.of(INVALID_AVAIL)));
    }

    @Test
    public void parseAvail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAvail(Optional.empty()).isPresent());
    }

    @Test
    public void parseAvail_validValueWithoutWhitespace_returnsAvail() throws Exception {
        Avail expectedAvail = new Avail(VALID_AVAIL);
        assertEquals(expectedAvail, ParserUtil.parseAvail(VALID_AVAIL));
        assertEquals(Optional.of(expectedAvail), ParserUtil.parseAvail(Optional.of(VALID_AVAIL)));
    }

    @Test
    public void parseAvail_validValueWithWhitespace_returnsTrimmedAvail() throws Exception {
        String availWithWhitespace = WHITESPACE + VALID_AVAIL + WHITESPACE;
        Avail expectedAvail = new Avail(VALID_AVAIL);
        assertEquals(expectedAvail, ParserUtil.parseAvail(availWithWhitespace));
        assertEquals(Optional.of(expectedAvail), ParserUtil.parseAvail(Optional.of(availWithWhitespace)));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
