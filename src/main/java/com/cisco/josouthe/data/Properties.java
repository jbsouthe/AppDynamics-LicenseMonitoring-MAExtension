package com.cisco.josouthe.data;

public class Properties {
    public Property[] properties;
    public String get(String name) {
        if( name == null || properties == null ) return null;
        for( Property property : properties)
            if( name.equals(property.name) ) return property.value;
        return null;
    }
}
