package com.cisco.josouthe;

import com.cisco.josouthe.data.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private static final Logger logger = LogManager.getFormatterLogger();

    private URL url;
    private String hostname, clientId, clientSecret;
    private AccessToken accessToken = null;
    private Gson gson = null;
    private Application[] applications = null;
    private Account account = null;
    private Map<String,String> links = null;

    public Controller(URL url, String clientId, String clientSecret ) {
        this.url=url;
        this.hostname= url.getHost();
        this.clientId=clientId;
        this.clientSecret=clientSecret;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public String getAccountId() {
        if( this.account == null ) {
            String json = getRequest("controller/api/accounts/myaccount");
            this.account = gson.fromJson(json, Account.class);
            this.links = new HashMap<>();
            for( Link link : account.links )
                this.links.put( link.name, link.href);
        }
        return account.id;
    }

    public Application[] getApplications() {
        if( this.applications == null ) {
            getAccountId();
            ApplicationAndLinks applicationAndLinks = gson.fromJson(getRawRequest(links.get("applications")), ApplicationAndLinks.class);
            this.applications = applicationAndLinks.applications;
            for( Application application : this.applications ) {
                application.nodes = gson.fromJson(getRequest("controller/api/accounts/%s/applications/%s/nodes", getAccountId(), application.id), Nodes.class).nodes;
                application.vCpuTotal = gson.fromJson(getRequest("controller/licensing/v1/account/%s/grouped-usage/application/by-id?appId=%s&includeAgents=false", getAccountId(), application.id), LicenseUsage.class).vCpuTotal;
                /* no value
                if( application.nodes != null )
                    for( Node node : application.nodes ) {
                        getRequest("controller/api/accounts/%s/applications/%s/nodes/%s", getAccountId(), application.id, node.id);
                    }

                 */
            }
        }
        return applications;
    }

    public LicenseModule[] getLicenseModules() {
        LicenseModule[] modules =  gson.fromJson(getRawRequest( links.get("licensemodules") ), Modules.class).modules;
        for( LicenseModule module : modules ) {
            module.usages = gson.fromJson( getRawRequest( module.getLink("usages")), LicenseUsage.class);
            if( module.usages == null ) module.usages = new LicenseUsage();
            module.properties = gson.fromJson( getRawRequest( module.getLink("properties")), Properties.class);
            if( module.properties == null ) module.properties = new Properties();
        }
        return modules;
    }

    public LicenseRule[] getAllLicenseAllocations() {
        LicenseRule[] rules = gson.fromJson(getRequest("controller/licensing/v1/account/%s/allocation", getAccountId()), LicenseRule[].class);
        for( LicenseRule rule : rules ) {
            LicenseAllocation licenseAllocation = gson.fromJson(
                    getRequest("controller/licensing/v1/usage/account/%s/allocation/%s?dateFrom=%s&dateTo=%s&granularityMinutes=%d&includeEntityTypes=true",
                            getAccountId(), rule.licenseKey, Util.getEncodedDateString(-10), Util.getEncodedDateString(0), 5 //must be divisible by 5!!
                    ), LicenseAllocation.class);
            if( licenseAllocation != null && licenseAllocation.packages != null ) rule.packages = licenseAllocation.packages;
            if( rule.filters != null )
                for( Filter filter : rule.filters ) {
                    for( Application application : this.applications ) {
                        try {
                            if (filter.matches(application)) {
                                rule.applications.add(application);
                            }
                        } catch (Exception e) {
                            logger.warn("Unsupported rule filter? %s", filter);
                        }
                    }
                }
        }
        return rules;
    }

    public void getLicenseUsage() {
        getRequest("controller/licensing/v1/usage/account/%s/", getAccountId());
    }

    public String getBearerToken() {
        if( isAccessTokenExpired() && !refreshAccessToken()) return null;
        return "Bearer "+ accessToken.access_token;
    }

    private boolean isAccessTokenExpired() {
        long now = new Date().getTime();
        if( accessToken == null || accessToken.expires_at < now ) return true;
        return false;
    }

    private boolean refreshAccessToken() { //returns true on successful refresh, false if an error occurs
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(clientId, clientSecret);
        logger.debug("credentials configured: %s",credentials.toString());
        provider.setCredentials(AuthScope.ANY, credentials);
        logger.debug("provider configured: %s",provider.toString());

        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        logger.trace("HttpClient created");

        HttpPost request = new HttpPost(url.toString()+"/controller/api/oauth/access_token");
        //request.addHeader(HttpHeaders.CONTENT_TYPE,"application/vnd.appd.cntrl+protobuf;v=1");
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add( new BasicNameValuePair("grant_type","client_credentials"));
        postParameters.add( new BasicNameValuePair("client_id",clientId));
        postParameters.add( new BasicNameValuePair("client_secret",clientSecret));
        try {
            request.setEntity(new UrlEncodedFormEntity(postParameters,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("Unsupported Encoding Exception in post parameter encoding: %s",e.getMessage());
        }

        if( logger.isDebugEnabled()){
            logger.debug("Request to run: %s",request.toString());
            for( Header header : request.getAllHeaders())
                logger.debug("with header: %s",header.toString());
        }

        HttpResponse response = null;
        try {
            response = client.execute(request);
            logger.debug("Response Status Line: %s",response.getStatusLine());
        } catch (IOException e) {
            logger.error("Exception in attempting to get access token, Exception: %s",e.getMessage());
            return false;
        }
        if( response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            logger.warn("Access Key retreival returned bad status: %s", response.getStatusLine());
            return false;
        }
        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());
        String json = null;
        try {
            json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            logger.trace("JSON returned: %s",json);
        } catch (IOException e) {
            logger.warn("IOException parsing returned encoded string to json text: "+ e.getMessage());
            return false;
        }
        this.accessToken = gson.fromJson(json, AccessToken.class); //if this doesn't work consider creating a custom instance creator
        this.accessToken.expires_at = new Date().getTime() + (accessToken.expires_in*60000); //hoping this is enough, worry is the time difference
        return true;
    }

    private String getRequest( String formatOrURI, Object... args ) {
        if( args == null || args.length == 0 ) return getRequest(formatOrURI);
        return getRequest( String.format(formatOrURI,args));
    }

    private String getRequest( String uri ) {
        return getRawRequest(String.format("%s%s", this.url.toString(), uri));
    }

    private String getRawRequest( String url ) {
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, getBearerToken());
        logger.debug("HTTP Method: %s",request);
        HttpClient client = HttpClientBuilder.create()
                .build();
        HttpResponse response = null;
        try {
            response = client.execute(request);
            logger.debug("Response Status Line: %s",response.getStatusLine());
        } catch (IOException e) {
            logger.error("Exception in attempting to get controller data, Exception: %s",e.getMessage());
            return null;
        }
        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());
        String json = null;
        try {
            json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            logger.trace("JSON returned: %s",json);
            if( response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.warn("request %s returned bad status: %s message: %s", request, response.getStatusLine(), json);
                return null;
            }
        } catch (IOException e) {
            logger.warn("IOException parsing returned encoded string to json text: "+ e.getMessage());
            return null;
        }
        return json;
    }

    public static void main( String ... args ) {
        try {
            logger.info(Util.getDateString(-1));
            Controller controller = new Controller(new URL("https://svbtest.saas.appdynamics.com/"), "LicenseMonitor@svbtest", "dabb1982-d550-41ab-bf3c-0860cb213b51");
            logger.info("Account ID: %s",controller.getAccountId());
            for (Application app : controller.getApplications() ) {
                logger.info("Application: %s id: %s node count: %d license usage: %d", app.name, app.id, app.getNodeCount(), app.vCpuTotal);
            }

            for( LicenseRule licenseRule : controller.getAllLicenseAllocations() ) {
                //(licenseRule.getTotalLimits() == 0 ? 0 : licenseRule.usedLicenses /  licenseRule.getTotalLimits() * 100)
                logger.info("Rule %s Total Provisioned: %d Used: %d Available: %d",licenseRule.name, licenseRule.getProvisionedLicenses(), licenseRule.getUsedLicenses(), licenseRule.getProvisionedLicenses() - licenseRule.getUsedLicenses() );
            }
            for( LicenseModule licenseModule : controller.getLicenseModules() ) {
                logger.info("License Module: %s number of provisioned licenses: %s", licenseModule.name, licenseModule.properties.get("number-of-provisioned-licenses"));
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
