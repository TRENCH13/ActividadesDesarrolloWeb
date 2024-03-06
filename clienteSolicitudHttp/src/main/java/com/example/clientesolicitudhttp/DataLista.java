package com.example.clientesolicitudhttp;

public class DataLista {
    private int intValue;
    private String stringValue;
    private int anotherIntValue;

    public DataLista(int intValue, String stringValue, int anotherIntValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.anotherIntValue = anotherIntValue;
    }

    // Getters y setters
    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getAnotherIntValue() {
        return anotherIntValue;
    }

    public void setAnotherIntValue(int anotherIntValue) {
        this.anotherIntValue = anotherIntValue;
    }
}
