package com.Freestor.RestAPI;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import static com.Freestor.RestAPI.CommonAPI.*;

import java.io.IOException;


public class ApiTest {


    public static final Logger logger = LogManager.getLogger(ApiTest.class.getName());

    @Test
    public void apiTest() throws IOException {

        int runTime = runTime();
        JsonPath jsonRespond =null;
        int start = 0;
        while(start!=runTime) {
            if(type().equalsIgnoreCase("post"))
                jsonRespond = commonPost(uri(), getPayLoadFile());
            if (type().equalsIgnoreCase("get"))
                jsonRespond= commonGet(uri());
            jsonRespond.prettyPrint();
            start++;
        }
    }
}


