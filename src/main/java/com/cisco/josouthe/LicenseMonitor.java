package com.cisco.josouthe;

import com.cisco.josouthe.data.*;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
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
    private String metricPrefix = "Custom Metrics|License Monitor";

    @Override
    public TaskOutput execute(Map<String, String> configMap, TaskExecutionContext taskExecutionContext) throws TaskExecutionException {
        try {
            String controllerURLString = configMap.get("controllerURL");
            if( !controllerURLString.endsWith("/") ) controllerURLString+="/";
            this.controller = new Controller( new URL(controllerURLString), configMap.get("apiClientId"), configMap.get("apiClientSecret") );
            if( configMap.containsKey("metricPrefix") ) metricPrefix = "Custom Metrics|"+ configMap.get("metricPrefix");
        } catch (MalformedURLException exception) {
            throw new TaskExecutionException(String.format("License Monitor configuration could not accept controllerURL: %s Exception: %s", configMap.get("controllerURL"), exception.getMessage()));
        }

        printMetric("up", 1,
                MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                MetricWriter.METRIC_TIME_ROLLUP_TYPE_SUM,
                MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
        );

        for(Application application : controller.getApplications()) {
            printMetric("Applications|"+application.name +"|vCpuTotal", application.vCpuTotal,
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
            );
            for(HostObject hostContainer : application.licenseUsage.hosts ) {
                for(HostItemObject host : hostContainer.items ) {
                    String baseName="Applications|"+ application.name + "|" + host.host + "|";
                    printMetric(baseName+"vCpu", host.vCpu,
                            MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                            MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                            MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
                    );
                    printMetric(baseName+"nodeCount", host.nodes.length,
                            MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                            MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                            MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
                    );
                }
            }
        }

        for( LicenseRule licenseRule : controller.getAllLicenseAllocations() ) {
            //(licenseRule.getTotalLimits() == 0 ? 0 : licenseRule.usedLicenses /  licenseRule.getTotalLimits() * 100)
            //logger.info("Rule %s Total Provisioned: %d Used: %d Available: %d",licenseRule.name, licenseRule.getProvisionedLicenses(), licenseRule.getUsedLicenses(), licenseRule.getProvisionedLicenses() - licenseRule.getUsedLicenses() );
            String baseName="Rules|"+ licenseRule.name +"|";
            printMetric(baseName+"provisioned", licenseRule.getProvisionedLicenses(),
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
            );
            printMetric(baseName+"inUseCount", licenseRule.getUsedLicenses(),
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
            );
            printMetric(baseName+"inUsePercentage", licenseRule.getUsedPercentageLicenses(),
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
            );
            printMetric(baseName+"freeCount", licenseRule.getFreeLicenses(),
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
            );
        }

        for( LicenseModule licenseModule : controller.getLicenseModules() ) {
            //logger.info("License Module: %s number of provisioned licenses: %s", licenseModule.name, licenseModule.properties.get("number-of-provisioned-licenses"));
            String provisionedCount = licenseModule.properties.get("number-of-provisioned-licenses");
            if( provisionedCount != null ) {
                printMetric("Modules|"+ licenseModule.name +"|provisioned", provisionedCount,
                        MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                        MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT,
                        MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_COLLECTIVE
                );
            }
        }

        return new TaskOutput("License Monitor Metric Upload Complete");
    }

    public void printMetric(String metricName, Object metricValue, String aggregation, String timeRollup, String cluster)
    {
        MetricWriter metricWriter = getMetricWriter(this.metricPrefix + metricName,
                aggregation,
                timeRollup,
                cluster
        );

        metricWriter.printMetric(String.valueOf(metricValue));
    }
}
