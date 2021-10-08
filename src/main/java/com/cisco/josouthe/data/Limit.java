package com.cisco.josouthe.data;

import com.google.gson.annotations.SerializedName;

//{"id":"7180f916-410c-4372-9e25-7685fc5afb62","package":"ENTERPRISE","units":1}
public class Limit {
    public String id;
    @SerializedName("package")
    public String packageString;
    public int units;
}
