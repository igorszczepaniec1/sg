package sg.account.adapters.out.fx;

import sg.account.dto.Currency;
import sg.account.ports.out.FxRatePort;

import java.math.BigDecimal;
import java.util.Map;

import static sg.account.dto.Currency.*;

public class FxRate implements FxRatePort {


    private final Map<RateKey, BigDecimal> map = Map.ofEntries(
            Map.entry(new RateKey(EUR, USD), new BigDecimal("0.9000000000")),
            Map.entry(new RateKey(USD, EUR), new BigDecimal("1.1111111111"))
            );


    @Override
    public BigDecimal rate(Currency from, Currency to) {
        return map.get(new RateKey(from, to));
    }
}