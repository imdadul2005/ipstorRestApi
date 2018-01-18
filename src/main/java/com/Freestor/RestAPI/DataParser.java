package com.Freestor.RestAPI;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class DataParser {
    public static XmlPath rawToXML(Response res ) {
        String x = res.asString();
        XmlPath xml = new XmlPath(x);
        return xml;
    }

    public static JsonPath rawToJSON(Response res ) {
        String x = res.asString();
        JsonPath json= new JsonPath(x);
        return json;
    }


}
