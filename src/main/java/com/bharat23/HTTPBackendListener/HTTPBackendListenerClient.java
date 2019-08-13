package com.bharat23.HTTPBackendListener;

import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.backend.AbstractBackendListenerClient;
import org.apache.jmeter.visualizers.backend.BackendListenerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class HTTPBackendListenerClient extends AbstractBackendListenerClient {
    
    private long time = System.currentTimeMillis();
    private ArrayList<Object>dataObjectList = new ArrayList<Object>();

    @Override
    public void setupTest(BackendListenerContext context) throws Exception {

    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        // my arguments
        arguments.addArgument("protocol", "http");
        arguments.addArgument("hostName", "127.0.0.1");
        arguments.addArgument("url", "/senddata");
        arguments.addArgument("port", "80");
        arguments.addArgument("interval", "30"); //in seconds
        arguments.addArgument("compression", "false");
        return arguments;
    }

    //create a map of response object
    private Map<String, Object> getMap(SampleResult result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AllThreads", result.getAllThreads());
        map.put("Assertions", result.getAssertionResults());
        // map.put("BodySize", result.getBodySize()); //deprecated
        map.put("BodySize", result.getBodySizeAsLong());
        // map.put("Bytes", result.getBytes()); //deprecated
        map.put("Bytes", result.getBytesAsLong());
        map.put("ConnectTime", result.getConnectTime());
        map.put("ContentType", result.getContentType());
        map.put("DataType", result.getDataType());
        map.put("EndTime", result.getEndTime());
        map.put("ErrorCount", result.getErrorCount());
        map.put("IdleTime", result.getIdleTime());
        map.put("Latency", result.getLatency());
        map.put("ResponseCode", result.getResponseCode());
        map.put("ResponseMessage", result.getResponseMessage());
        map.put("ResponseTime", result.getTime());
        map.put("SampleCount", result.getSampleCount());
        map.put("SampleLabel", result.getSampleLabel());
        map.put("StartTime", result.getStartTime());
        map.put("Success", result.isSuccessful());
        map.put("ThreadName", result.getThreadName());
        map.put("URL", result.getURL());
        map.put("timestamp", result.getTimeStamp());
        return map;
    }

    @Override
    public void handleSampleResults(List<SampleResult> results, BackendListenerContext context) {
        for(SampleResult result : results) {
            Map<String,Object> jsonObject = getMap(result);
            long interval = Long.parseLong(context.getParameter("interval")) * 1000;
            long timeAgo = System.currentTimeMillis() - interval;
            this.dataObjectList.add(jsonObject);
            if (this.time < timeAgo) {
                // time passed
                Fetch fetch = new Fetch(context.getParameter("compression"));
                fetch.post((context.getParameter("protocol") + "://" + context.getParameter("hostName") + ":" + context.getParameter("port") + context.getParameter("url")), this.dataObjectList);
                this.time = System.currentTimeMillis();
                this.dataObjectList.clear();
            }
        }
    }

    @Override
    public void teardownTest(BackendListenerContext context) throws Exception {
        if (this.dataObjectList.size() > 0) {
            Fetch fetch = new Fetch(context.getParameter("compression"));
            fetch.post((context.getParameter("protocol") + "://" + context.getParameter("hostName") + ":" + context.getParameter("port") + context.getParameter("url")), this.dataObjectList);
            this.dataObjectList.clear();
        }
        super.teardownTest(context);
    }
}
