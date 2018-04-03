package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.account.UniqueAccountList;

public class AccountListChangedEvent extends BaseEvent {

    public final UniqueAccountList data;

    public AccountListChangedEvent(UniqueAccountList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Number of accounts: " + data.size();
    }
}
