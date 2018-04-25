package Enums;

public enum Commands {

    GRACE("grace");

    private String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
