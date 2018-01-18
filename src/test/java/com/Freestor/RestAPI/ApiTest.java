package com.Freestor.RestAPI;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import  io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.io.IOException;


public class ApiTest {

    @Test

    public void replicationStatus() throws IOException {

        int runTime = CommonAPI.runTime();
        JsonPath jsonRespond =null;
        int start = 0;
        while(start!=runTime) {
            if(CommonAPI.type().equalsIgnoreCase("post"))
                jsonRespond = CommonAPI.commonPost(CommonAPI.uri(), CommonAPI.getPayLoadFile());
            if (CommonAPI.type().equalsIgnoreCase("get"))
                jsonRespond= CommonAPI.commonGet(CommonAPI.uri());
            jsonRespond.prettyPrint();
            start++;
        }
    }
}


