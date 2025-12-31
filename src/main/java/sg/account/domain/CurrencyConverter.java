package sg.account.domain;

import sg.account.dto.AmountDto;
import sg.account.dto.Currency;
import sg.account.ports.out.FxRatePort;


class CurrencyConverter {

    private final FxRatePort fxRatePort;

    CurrencyConverter(FxRatePort fxRatePort) {
        this.fxRatePort = fxRatePort;
    }

    AmountDto convert(Currency accountCurrency, AmountDto amount){
        var rate = fxRatePort.rate(accountCurrency, amount.currency());
        var convertedAmount = amount.amount().multiply(rate);
        return new AmountDto(convertedAmount, amount.currency());
    }

}
