package com.Freestor.RestAPI;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Freestor.RestAPI.CommonAPI.*;


public class MultiTest2 {


    public static final Logger logger = LogManager.getLogger(MultiTest2.class.getName());

    @Test
    public void apiTest() throws IOException {

        int runTime = CommonAPI.runTime();
        JsonPath jsonRespond = null;
        int start = 0;
        String totalLocation = CommonAPI.totalLocation();
                String ifEnabled= CommonAPI.findFor();
                String get = CommonAPI.get();

        while(start!=runTime) {

            jsonRespond = restCall(type(),uri(), getPayLoadFile());
            List<String> getParsedIntgerList= new ArrayList<String>();
            getParsedIntgerList = CommonAPI.getParsedStringList(jsonRespond,totalLocation,ifEnabled,get);
            logger.info("List of device number "+ getParsedIntgerList.toString());
            if(!uri2().equalsIgnoreCase("none")) {
                for (int x = 0; x < getParsedIntgerList.size(); x++) {
                    String uriNew = CommonAPI.uri2() + "/" + getParsedIntgerList.get(x);
                    restCall(type(), uriNew, getPayLoadFile2());
                }
            }
            //  jsonRespond.prettyPrint();
            start++;
        }
    }
    public JsonPath restCall(String type, String uri, File getPayLoadFile) throws IOException {
        if (type.equalsIgnoreCase("post")) {
            return commonPost(uri, getPayLoadFile);

        }
        if (type.equalsIgnoreCase("get")) {
            return commonGet(uri);

        }
        if (type.equalsIgnoreCase("delete")) {
            return commonDelete(uri, getPayLoadFile);

        }
        if (type.equalsIgnoreCase("put")) {
            return commonPut(uri, getPayLoadFile);
        }
        return null;
    }
}


