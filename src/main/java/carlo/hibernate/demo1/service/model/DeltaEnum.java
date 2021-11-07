package carlo.hibernate.demo1.service.model;

import com.fasterxml.jackson.annotation.*;

public enum DeltaEnum {
    ONE("ONE"),

    TWO("TWO"),

    THREE("THREE");

    private String value;

    DeltaEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DeltaEnum fromValue(String text) {
        for (DeltaEnum b : DeltaEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
