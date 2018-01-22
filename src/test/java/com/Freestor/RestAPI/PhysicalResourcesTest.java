package com.Freestor.RestAPI;

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

    @Test

    public void PhysicalResources() throws IOException {
        Response res = CommonAPI.commonGet(ApiResource.getenumAdapters());
        JsonPath getPhyDevices = DataParser.rawToJSON(res);
        //getPhyDevices.
        List<String> vendor = getPhyDevices.getList("data.physicaladapters.vendor");
        List<String> id = getPhyDevices.getList("data.physicaladapters.id");
        List<String> mode = getPhyDevices.getList("data.physicaladapters.mode");
        List<String> type = getPhyDevices.getList("data.physicaladapters.type");
        List<String> wwpn = getPhyDevices.getList("data.physicaladapters.wwpn");
        List<String> paths = getPhyDevices.getList("data.physicaladapters.paths");

        Assert.assertEquals(vendor.size(),id.size());
        Assert.assertEquals(mode.size(),type.size());
        Assert.assertEquals(wwpn.size(),paths.size());

        for (String a: wwpn) {
            if(a.length()>1)
            System.out.println(a);
        }

    }
}
