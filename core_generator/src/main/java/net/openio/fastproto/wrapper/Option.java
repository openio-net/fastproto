package net.openio.fastproto.wrapper;

public class Option {

    private final String key;

    private final Object value;

    private final OptionType type;


    public Option(String key, Object value, OptionType optionType) {
        this.key = key;
        this.value = value;
        this.type = optionType;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public OptionType getObjectType() {
        return type;
    }
}
