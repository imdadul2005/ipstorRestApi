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


public class MultiTest {


    public static final Logger logger = LogManager.getLogger(MultiTest.class.getName());

    @Test
    public void apiTest() throws IOException {

        int runTime = CommonAPI.runTime();
        JsonPath jsonRespond = null;
        int start = 0;
        String totalLocation = CommonAPI.totalLocation();
                String ifEnabled= CommonAPI.findFor();
                String get = CommonAPI.get();

        while(start!=runTime) {

            jsonRespond = restCall(uri(), getPayLoadFile());
            List<Integer> getParsedIntgerList= new ArrayList<Integer>();
            getParsedIntgerList = CommonAPI.getParsedIntgerList(jsonRespond,totalLocation,ifEnabled,get);
            logger.info("List of device number "+ getParsedIntgerList.toString());
            for (int x:getParsedIntgerList) {
                String uriNew = CommonAPI.uri2()+"/"+x;
                restCall(uriNew,getPayLoadFile());
            }
            //  jsonRespond.prettyPrint();
            start++;
        }
    }
    public JsonPath restCall(String uri, File getPayLoadFile) throws IOException {
        if (type().equalsIgnoreCase("post")) {
            return commonPost(uri, getPayLoadFile);

        }
        if (type().equalsIgnoreCase("get")) {
            return commonGet(uri);

        }
        if (type().equalsIgnoreCase("delete")) {
            return commonDelete(uri, getPayLoadFile);

        }
        if (type().equalsIgnoreCase("put")) {
            return commonPut(uri, getPayLoadFile);
        }
        return null;
    }
}


