package sg.account.domain;

import java.util.UUID;

class AccountId {

    private final UUID id;

    AccountId(UUID id) {
        this.id = id;
    }

    UUID getId() {
        return id;
    }
}
