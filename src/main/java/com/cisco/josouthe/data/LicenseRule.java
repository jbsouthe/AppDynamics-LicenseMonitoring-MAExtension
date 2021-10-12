package com.cisco.josouthe.data;

import java.util.ArrayList;
import java.util.List;

/*
[
{"id":"6c81c618-e2ff-4433-90ca-912ca3149f73","accountId":795,"name":"Gateway","licenseKey":"5edb8863-b7dd-41dd-9673-abffc4ffe97c",
    "filters":[
        {"id":"62d937df-3fa4-4631-8c5f-9338ae96a4e2","type":"APPLICATION","operator":"ID_EQUALS","value":"376999"},
        {"id":"742b49d5-ef2b-4874-a302-39d3a8b87fc0","type":"APPLICATION","operator":"ID_EQUALS","value":"377028"},
        {"id":"7f59d3c4-0832-491a-84b0-144e2668a4b9","type":"APPLICATION","operator":"ID_EQUALS","value":"377597"},
        {"id":"8168169d-930f-481a-a762-1277730d9ba5","type":"APPLICATION","operator":"ID_EQUALS","value":"376474"},
        {"id":"9491754d-9c9b-48b1-9bc7-2a223db1e87a","type":"APPLICATION","operator":"ID_EQUALS","value":"377589"},
        {"id":"c440c1cd-c433-4eff-8810-87c287b90454","type":"APPLICATION","operator":"ID_EQUALS","value":"376840"}
    ],
    "limits":[
        {"id":"7180f916-410c-4372-9e25-7685fc5afb62","package":"ENTERPRISE","units":1}
    ],
    "tags":[],
    "createdDate":"2021-07-16T19:44:11Z",
    "lastUpdatedDate":"2021-08-13T21:21:04Z","disabled":false},
{"id":"e5d2a133-b011-45af-8d44-54716acb4c0b","accountId":795,"name":"UBS","licenseKey":"2ba22e42-c2b7-4b83-8c9a-297e23e608ba",
    "filters":[
    {"id":"3f2b49ae-a5d2-4f0b-a558-45d8a4cdd6ed","type":"APPLICATION","operator":"ID_EQUALS","value":"377086"},
    {"id":"855fc969-df19-44be-891b-45ff386d95ba","type":"APPLICATION","operator":"EQUALS","value":"UAT-UBS"}
    ],
    "limits":[],
    "tags":[],
    "createdDate":"2021-07-16T19:44:11Z","lastUpdatedDate":"2021-07-16T19:44:11Z","disabled":false},


 */
public class LicenseRule {
    public String id, name, description, account_id, access_key, peak_usage, licenseKey;
    public int version, total_licenses;
    public boolean disabled;
    public Limit[] limits;
    public Filter[] filters;
    public List<Application> applications = new ArrayList<>();
    public LicensePackage[] packages;

    public int getTotalLimits() {
        int total=0;
        for( Limit limit : limits ) total+=limit.units;
        return total;
    }

    public long getUsedLicenses() {
        long count=0;
        for( LicensePackage licensePackage : packages ) {
            for( LicenseUnitUsages licenseUnitUsages : licensePackage.unitUsages ) {
                count += licenseUnitUsages.data[0].used.avg;
            }
        }
        return count;
    }
    public long getProvisionedLicenses() {
        long count=0;
        for( LicensePackage licensePackage : packages ) {
            for( LicenseUnitUsages licenseUnitUsages : licensePackage.unitUsages ) {
                count += licenseUnitUsages.data[0].provisioned.avg;
            }
        }
        return count;
    }

    public long getFreeLicenses() {
        return getProvisionedLicenses() - getUsedLicenses();
    }

    public double getUsedPercentageLicenses() {
        long total = getProvisionedLicenses();
        if( total == 0 ) return 0.0;
        return (double)getUsedLicenses() / (double)getProvisionedLicenses() * 100;
    }
}
