package com.cisco.josouthe;

import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class LicenseMonitor extends AManagedMonitor {
    private static final Logger logger = LogManager.getFormatterLogger();
    private Controller controller =null;

    @Override
    public TaskOutput execute(Map<String, String> configMap, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        try {
            String controllerURLString = configMap.get("controllerURL");
            if( !controllerURLString.endsWith("/") ) controllerURLString+="/";
            this.controller = new Controller( new URL(controllerURLString), configMap.get("apiClientId"), configMap.get("apiClientSecret") );
        } catch (MalformedURLException exception) {
            throw new TaskExecutionException(String.format("License Monitor configuration could not accept controllerURL: %s Exception: %s", configMap.get("controllerURL"), exception.getMessage()));
        }

        return new TaskOutput("License Monitor Metric Upload Complete");
    }
}
