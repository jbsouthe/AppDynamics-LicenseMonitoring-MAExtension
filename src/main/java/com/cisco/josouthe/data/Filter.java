package com.cisco.josouthe.data;
/*
{"id":"3f2b49ae-a5d2-4f0b-a558-45d8a4cdd6ed","type":"APPLICATION","operator":"ID_EQUALS","value":"377086"},
{"id":"855fc969-df19-44be-891b-45ff386d95ba","type":"APPLICATION","operator":"EQUALS","value":"UAT-UBS"}
 */
public class Filter {
    public String id, type, operator, value;
    public boolean matches( Application application ) throws Exception {
        if( type == null || operator == null || value == null ) return false;
        if( !type.equals("APPLICATION") ) return false;
        switch( operator ) {
            case "ID_EQUALS": {
                if( this.value.equals(application.id) ) return true;
                break;
            }
            case "EQUALS": {
                if( this.value.equals(application.name) ) return true;
                break;
            }
            default: {
                throw new Exception("Unknown operator type: "+ operator);
            }
        }
        return false;
    }

    public String toString() {
        return String.format("{\"id\":\"%s\",\"type\":\"%s\",\"operator\":\"%s\",\"value\",\"%s\"",id,type,operator,value);
    }
}
