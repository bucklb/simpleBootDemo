package me.bucklb.simpleBootdemo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String type;
    private String value;

    public Quote() {
    }

    // Avoid messing around creating a new Quote object
    public Quote( String type,  String value ) {
        this.value  = value;
        this.type   = type;
    }

    public String getType() {
        return type;
    }
    public void   setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }
    public void   setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}