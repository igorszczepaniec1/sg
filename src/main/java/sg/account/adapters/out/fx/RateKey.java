package sg.account.adapters.out.fx;

import sg.account.dto.Currency;

import java.util.Objects;


public class RateKey {

    public RateKey(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    private final Currency from;
    private final Currency to;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RateKey rateKey = (RateKey) o;
        return from == rateKey.from && to == rateKey.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
