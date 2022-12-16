package alghosproject.project;

public enum Auth_TYPE {
    LOGIN(1),
    REGISTER(2);

    private final int value;

    Auth_TYPE(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
