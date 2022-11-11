package net.openio.fastproto.wrapper;

public enum FiledLabel {

    Required("required"),
    Optional("optional"),
    Repeated("repeated");

    String label;

    FiledLabel(String label){
        this.label=label;
    }

    public String getLabel() {
        return label;
    }
}
