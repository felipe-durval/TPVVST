package br.ifsp.ordersys.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(double v) { return new Money(BigDecimal.valueOf(v)); }
    public static Money zero() { return new Money(BigDecimal.ZERO); }
    public Money add(Money other) { return new Money(this.value.add(other.value)); }
    public BigDecimal getValue() { return value; }
}
