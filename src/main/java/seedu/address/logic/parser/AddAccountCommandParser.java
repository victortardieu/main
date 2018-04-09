package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAccountCommand;

public class AddAccountCommandParser implements Parser<AddAccountCommand> {
    /**
     * Returns true if none of the prefixes contains empty(@code Optional) value in the given
     * (@code ArgumentMultimap).
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap.Prefix...prefixes) {
        return Stream.of(prefixes).allMatch((prefix -> argumentMultimap.getValue(prefix).isPresent()));
    }
    
}
