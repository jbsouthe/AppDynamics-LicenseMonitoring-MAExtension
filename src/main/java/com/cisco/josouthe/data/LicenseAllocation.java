package com.cisco.josouthe.data;
/*
{
"accountId":795,
"packages":[
    {"name":"ENTERPRISE",
    "unitUsages":[
        {"usageType":"LICENSE_UNIT","granularityInMinutes":5,
        "data":[
            {"timestamp":"2021-10-11T17:15:00Z",
            "provisioned":{
                "min":500,"max":500,"avg":500,"count":1
            },
            "used":{
                "min":238,"max":238,"avg":238,"count":1
            },
            "registrations":[
                {"entityType":"Database Collector",
                "registered":{
                    "min":5,"max":5,"avg":5,"count":1
                    }
                },
        {"entityType":"Machine Agent (SIM)","
            registered":{"min":4,"max":4,"avg":4,"count":1}
        }
    ]
    },
    {"timestamp":"2021-10-11T17:20:00Z",
    "provisioned":{
        "min":500,"max":500,"avg":500,"count":1
    },
    "used":{"min":238,"max":238,"avg":238,"count":1},
    "registrations":[
        {"entityType":"Database Collector",
        "registered":{
            "min":5,"max":5,"avg":5,"count":1
        }
    },{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:25:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:30:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:35:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:40:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:45:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]},{"timestamp":"2021-10-11T17:50:00Z","provisioned":{"min":500,"max":500,"avg":500,"count":1},"used":{"min":238,"max":238,"avg":238,"count":1},"registrations":[{"entityType":"Database Collector","registered":{"min":5,"max":5,"avg":5,"count":1}},{"entityType":"Machine Agent (SIM)","registered":{"min":4,"max":4,"avg":4,"count":1}}]}]}]}],"licenseRule":{"id":"d18167b1-fcd6-45d1-8686-3d91a3987999","name":"Default","licenseKey":"915dde84-dd65-4bb4-ae76-7d53ae4881a2"}}

 */
public class LicenseAllocation {
    public int accountId;
    public LicenseRule licenseRule;
    public LicensePackage[] packages;
}
