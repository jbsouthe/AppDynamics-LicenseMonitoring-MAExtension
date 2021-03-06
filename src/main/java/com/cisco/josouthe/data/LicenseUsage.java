package com.cisco.josouthe.data;
/*
{"vCpuTotal":102,
"items":{
    "377412":{
        "vCpu":102,
        "nodes":[
            {"nodeName":"chd-vp1-olbapp8-OLBOFXServer3"},
            {"nodeName":"chd-vp1-olbapp2-LoginServer1"},
            {"nodeName":"chd-vp1-olbapp3-SecServer2"},
            {"nodeName":"chd-vp1-olbap10-CredCardServer2"},
            {"nodeName":"chd-vp1-olbapp4-LoginServer3"},
            {"nodeName":"chd-vp1-olbapp2-ManagedServer1"},
            {"nodeName":"chd-vp1-olbapp9-CredCardServer1"},
            {"nodeName":"chd-vp1-olbapp4-ManagedServer3"},
            {"nodeName":"chd-vp1-olbapp4-SecServer3"},
            {"nodeName":"chd-vp1-olbapp6-ActRepServer1"},
            {"nodeName":"chd-vp1-olbap11-CredCardServer3"},
            {"nodeName":"chd-vp1-olbapp2-SecServer1"},
            {"nodeName":"chd-vp1-olbapp7-ActRepServer2"},
            {"nodeName":"chd-vp1-olbapp4-econnectJMSServer1"},
            {"nodeName":"chd-vp1-olbapp8-ActRepServer3"},
            {"nodeName":"chd-vp1-olbapp6-OLBOFXServer1"},
            {"nodeName":"chd-vp1-olbapp5-econnectJMSServer2"},
            {"nodeName":"chd-vp1-olbapp7-OLBOFXServer2"}
            ],
        "containers":[],
        "agents":[
            {"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}
            ],
        "appId":377412,
        "appName":"PERF1-eConnect",
        "hosts":{
            "vCpuTotal":102,
            "items":{
                "CHD-VP1-OLBAPP8":{
                    "vCpu":12,
                    "nodes":[
                        {"nodeName":"chd-vp1-olbapp8-OLBOFXServer3"},
                        {"nodeName":"chd-vp1-olbapp8-ActRepServer3"}
                    ],
                    "containers":[],
                    "agents":[
                        {"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}
                    ],
                    "host":"CHD-VP1-OLBAPP8",
                    "fallbackLicenseUnits":{}
                },
                "CHD-VP1-OLBAPP9":{
                    "vCpu":6,
                    "nodes":[
                        {"nodeName":"chd-vp1-olbapp9-CredCardServer1"}
                    ],
                    "containers":[],
                    "agents":[
                        {"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}
                    ],
                    "host":"CHD-VP1-OLBAPP9","fallbackLicenseUnits":{}},"CHD-VP1-OLBAP10":{"vCpu":6,"nodes":[{"nodeName":"chd-vp1-olbap10-CredCardServer2"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAP10","fallbackLicenseUnits":{}},"CHD-VP1-OLBAP11":{"vCpu":6,"nodes":[{"nodeName":"chd-vp1-olbap11-CredCardServer3"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAP11","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP2":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp2-LoginServer1"},{"nodeName":"chd-vp1-olbapp2-SecServer1"},{"nodeName":"chd-vp1-olbapp2-ManagedServer1"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP2","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP3":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp3-SecServer2"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP3","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP4":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp4-SecServer3"},{"nodeName":"chd-vp1-olbapp4-econnectJMSServer1"},{"nodeName":"chd-vp1-olbapp4-LoginServer3"},{"nodeName":"chd-vp1-olbapp4-ManagedServer3"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP4","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP5":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp5-econnectJMSServer2"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP5","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP6":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp6-ActRepServer1"},{"nodeName":"chd-vp1-olbapp6-OLBOFXServer1"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP6","fallbackLicenseUnits":{}},"CHD-VP1-OLBAPP7":{"vCpu":12,"nodes":[{"nodeName":"chd-vp1-olbapp7-ActRepServer2"},{"nodeName":"chd-vp1-olbapp7-OLBOFXServer2"}],"containers":[],"agents":[{"type":"APP_AGENT","version":null,"licenseKey":"0d5154f4-8212-4cbe-96e1-56ce21c62d04"}],"host":"CHD-VP1-OLBAPP7","fallbackLicenseUnits":{}}}}}}}
 */
public class LicenseUsage {
    public int vCpuTotal, appId;
    public String appName;
    public AgentObject[] agents;
    public HostObject[] hosts;
}
