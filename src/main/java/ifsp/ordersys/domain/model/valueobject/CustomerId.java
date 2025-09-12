package ifsp.ordersys.domain.model.valueobject;

import java.util.Objects;

public final class CustomerId {

    private final String value;

    public CustomerId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CustomerId não pode ser nulo ou vazio.");
        }

        if (!value.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("CustomerId inválido. Apenas letras e números são permitidos.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerId)) return false;
        CustomerId that = (CustomerId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
