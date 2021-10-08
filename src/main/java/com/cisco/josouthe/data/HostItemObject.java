package com.cisco.josouthe.data;

public class HostItemObject {
    public String host;
    public int vCpu;
    //public LicenseUnits[] fallbackLicenseUnits;
    public NodeObject[] nodes;
    public ContainerObject[] containers;
    public AgentObject[] agents;
    public Application[] application; //typo? https://docs.appdynamics.com/21.10/en/extend-appdynamics/appdynamics-apis/license-api#LicenseAPI-hostItemObject
}
