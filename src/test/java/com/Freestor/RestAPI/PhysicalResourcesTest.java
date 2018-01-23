package com.Freestor.RestAPI;

import groovy.json.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import static com.Freestor.RestAPI.CommonAPI.*;


public class PhysicalResourcesTest {

    public static final Logger logger = LogManager.getLogger(PhysicalResourcesTest.class.getName());



    static List<Integer> adapterNumber;
    @Test
    public void PhysicalResources() throws IOException {
        Response res = CommonAPI.commonGet(ApiResource.getenumAdapters());
        JsonPath getPhyDevices = DataParser.rawToJSON(res);

        logger.info("==================FC PORT REPORT=============================");
        int size = getPhyDevices.get("data.physicaladapters.size()");
        adapterNumber = getPhyDevices.getList("data.physicaladapters.id");

        for(int i = 0; i<size;i ++){
            String temp = getPhyDevices.getString("data.physicaladapters["+i+"].mode");
            if(temp.length()>1) {
                logger.info("FC ID : "+ getPhyDevices.getString("data.physicaladapters["+i+"].id")+ " MODE : "+ temp+" WWPN : "+ getPhyDevices.getString("data.physicaladapters["+i+"].wwpn"));
            }
        }

            //Check that rest api return rc = 0 and status code is 200;
            AssertCheck(getPhyDevices,res,200,0);

    }

    @Test(dependsOnMethods = {"PhysicalResources"})
    public void PhysicalAdapter() throws IOException {
        for (int x:adapterNumber) {
            Response res = CommonAPI.commonGet(ApiResource.getPhysicaladapter(x));
            JsonPath physicalAdapter = DataParser.rawToJSON(res);
            AssertCheck(physicalAdapter,res,200,0,x);
        }
    }
}
