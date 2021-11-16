AppDynamics License Monitoring Machine Agent Extension
=========================================================

## Use Cases ##

This extension monitors the AppDynamics Controller Licenses and License Rules.

## Prerequisites ##

1. Before the extension is installed, the prerequisites mentioned [here](https://community.appdynamics.com/t5/Knowledge-Base/Extensions-Prerequisites-Guide/ta-p/35213) need to be met. Please do not proceed with the extension installation if the specified prerequisites are not met.
2. Make sure that the machine agent server has access to the Controller URL to be monitored, if different from the machine agent registration controller.

## Configuration ##

1. Unzip/Untar the distribution archive into the Machine Agent ./monitors subdirectory
2. Edit the config.xml within the monitors/LicenseMonitor/ subdirectory
3. Add the following configuration options:

    ```
   <task-arguments>
            <argument name="controllerURL" is-required="true" default-value="https://customer.saas.appdynamics.com/" />
            <argument name="apiClientId" is-required="true" default-value="APIUser@customer" />
            <argument name="apiClientSecret" is-required="true" default-value="theBigCrazySecretString:)" />
    </task-arguments>
    ```

## Metrics Provided ##

* Custom Metrics|License Monitor|Applications|< App Name>|vCpuTotal
* Custom Metrics|License Monitor|Applications|< App Name>|< Host Name>|vCPU
* Custom Metrics|License Monitor|Applications|< App Name>|< Host Name>|nodeCount
* Custom Metrics|License Monitor|Rules|< License Rule Name>|provisioned
* Custom Metrics|License Monitor|Rules|< License Rule Name>|inUseCount
* Custom Metrics|License Monitor|Rules|< License Rule Name>|freeCount
* Custom Metrics|License Monitor|Modules|< License Module Name>|provisioned