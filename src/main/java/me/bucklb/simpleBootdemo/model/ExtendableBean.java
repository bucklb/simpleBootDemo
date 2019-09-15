package me.bucklb.simpleBootdemo.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

// Can specify a specific order (by filed0) or just that they are alphabetically ordered.
// Not sure how this applies to the "sub" objects though - properties. Doesn't seem reliable :(
@JsonPropertyOrder(alphabetic = true)
public class ExtendableBean {

    // Looks like we can override the name associated with a property (and the JsonGetter gets precedence)
    @JsonProperty("butt")
    @JsonGetter("arse")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
    public Map<String, String> properties;

    public ExtendableBean() {
        properties = new HashMap<String, String>();
    }

    public ExtendableBean(final String name) {
        this.name = name;
        properties = new HashMap<String, String>();
    }




//    @JsonAnyGetter(enabled=false)
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
//    @JsonAnySetter
    public void add(final String key, final String value) {
        properties.put(key, value);
    }

}