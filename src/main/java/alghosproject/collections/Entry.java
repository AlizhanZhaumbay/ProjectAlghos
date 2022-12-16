package alghosproject.collections;

public class Entry {
    Object key;
    Object value;
    Entry next;

    public Entry(Object key,Object value){
        this.key=key;
        this.value=value;
    }

    public Entry(){}

    public Object GetKey(){
        return key;
    }
    public Object GetValue(){
        return value;
    }
}