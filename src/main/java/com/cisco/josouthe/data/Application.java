package com.cisco.josouthe.data;

/*
{"applications": [
    {"id": "603904","name": "Agent Proxy","description": ""},
    {"id": "555789","name": "Cloud Monitoring","description": ""},
    {"id": "674411","name": "mulesoft","description": ""},
    {"id": "786373","name": "RxConnect_Client","description": ""},
    {"id": "793403","name": "RxCStoreServices","description": ""},
    {"id": "672788","name": "SnapshotTest","description": ""},
    {"id": "559158","name": "SNMP-Test","description": ""},
    {"id": "612650","name": "URL Monitoring","description": ""}
    ],
"links": [
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"},
    {"href": "http://southerland-test.saas.appdynamics.com/controller/api/accounts/23511/applications/{applications.id}","name": "applications"}
    ]
}

 */
public class Application {
    public String name, description, id;
    public Node[] nodes;
    public int vCpuTotal;
    public int getNodeCount() {
        if( nodes != null ) return nodes.length;
        return 0;
    }
}
