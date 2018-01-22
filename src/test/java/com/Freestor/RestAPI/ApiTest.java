package com.Freestor.RestAPI;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static com.Freestor.RestAPI.CommonAPI.*;
import static io.restassured.RestAssured.given;

import java.io.IOException;


public class ApiTest {


    public static final Logger logger = LogManager.getLogger(ApiTest.class.getName());

    @Test
    public void apiTest() throws IOException {

        int runTime = CommonAPI.runTime();
        JsonPath jsonRespond = null;
        int start = 0;

        while(start!=runTime) {
            if(type().equalsIgnoreCase("post"))
                jsonRespond = commonPost(uri(), getPayLoadFile());
            if (type().equalsIgnoreCase("get"))
                jsonRespond= commonGet(uri());
            if(type().equalsIgnoreCase("delete"))
                jsonRespond = commonDelete(uri(),getPayLoadFile());
            if(type().equalsIgnoreCase("put"))
                jsonRespond = commonPut(uri(),getPayLoadFile());
           //  jsonRespond.prettyPrint();
            start++;
        }
    }
}


