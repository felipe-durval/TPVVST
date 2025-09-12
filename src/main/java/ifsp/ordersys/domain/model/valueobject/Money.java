package ifsp.ordersys.domain.model.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money {

    private final BigDecimal value;

    public Money(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("O valor monetário não pode ser negativo.");
        }
        // Mantém duas casas decimais
        this.value = BigDecimal.valueOf(value).setScale(2);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "Money a ser adicionado não pode ser nulo");
        return new Money(this.value.add(other.value).doubleValue());
    }

    public Money multiply(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity)).doubleValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return value.equals(money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "R$ " + value.toPlainString();
    }
}
