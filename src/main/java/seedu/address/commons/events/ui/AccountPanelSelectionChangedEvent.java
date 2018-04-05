package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.AccountCard;

public class AccountPanelSelectionChangedEvent extends BaseEvent{

    private final AccountCard newSelection;

    public AccountPanelSelectionChangedEvent(AccountCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public AccountCard getNewSelection() {
        return newSelection;
    }

}
