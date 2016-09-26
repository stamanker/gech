package com;

public class Msg {

    public String data;
    public String key;

    public Msg() {
    }

    public Msg(String data, String key) {
        this.data = data;
        this.key = key;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "data='" + data + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
