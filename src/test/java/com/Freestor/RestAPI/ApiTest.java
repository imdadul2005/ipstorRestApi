package com.Freestor.RestAPI;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static com.Freestor.RestAPI.CommonAPI.*;


import java.io.IOException;


public class ApiTest {

    public static final Logger logger = LogManager.getLogger(ApiTest.class.getName());

    @Test
    public void apiTest() throws IOException {

        int runTime = CommonAPI.runTime();
        Response res = null;
        JsonPath jsonRespond = null;
        int start = 0;

        while(start!=runTime) {
            if(type().equalsIgnoreCase("post"))
                res= commonPost(uri(), getPayLoadFile());
            if (type().equalsIgnoreCase("get"))
                res= commonGet(uri());
            if(type().equalsIgnoreCase("delete"))
                res= commonDelete(uri(),getPayLoadFile());
            if(type().equalsIgnoreCase("put"))
                res = commonPut(uri(),getPayLoadFile());
            start++;
        }
    }
}


