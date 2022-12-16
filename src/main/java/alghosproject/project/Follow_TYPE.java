package alghosproject.project;

public enum Follow_TYPE {
    UNFOLLOWED(1),
    FOLLOWED(2),
    BLOCK(3);

    final int type;

    Follow_TYPE(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
