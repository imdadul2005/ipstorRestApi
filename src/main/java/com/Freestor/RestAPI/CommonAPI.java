package com.Freestor.RestAPI;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

import static io.restassured.RestAssured.given;

public class CommonAPI {




    public static final Logger logger = LogManager.getLogger(CommonAPI.class.getName());

    static String baseURI;
    static String currentSessionID = null;

    public static StringWriter requestWriter;
    public static PrintStream requestCapture;

    public static StringWriter responseWriter;
    public static PrintStream responseCapture;


    public static void filterlog(){
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));

        responseWriter = new StringWriter();
        responseCapture = new PrintStream(new WriterOutputStream(responseWriter));

    }


    public static String getSessionID() throws IOException {

        Properties p = property();
        String server = p.getProperty("server");
        String username = p.getProperty("username");
        String password = p.getProperty("password");

        String loginBody = "{\"server\": \"" +server+"\",\"username\":\"" +username +"\",\"password\":\"" +password +"\"}";
        filterlog();
        if(currentSessionID==null) {
            Response loginResponse = given().header(CommonAPI.header())
                    .body(loginBody)
                    .filter(new RequestLoggingFilter(requestCapture))
                    .filter(new ResponseLoggingFilter(responseCapture))
                    .post(ApiResource.postLogin())
                    .then().extract().response();
            logger.info(loginResponse);
            JsonPath auth =  DataParser.rawToJSON(loginResponse);
            currentSessionID = auth.get("id");
            logger.info(requestWriter.toString());
            logger.info(responseCapture.toString());
        }
        return currentSessionID;

    }
    public static Header header(){
        return new Header("Content-Type","application/json");
    }
    public static void setBaseURI() throws IOException {
        Properties properties = property();
        RestAssured.baseURI = "http://"+ properties.getProperty("server")+"/ipstor/" ;
                //properties.getProperty("url");
        baseURI = RestAssured.baseURI;

    }
    public static Properties property() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("target/classes/my.properties");
        prop.load( fis);
        return prop;
    }

    public static String uri() throws IOException {
        Properties p = property();
        return p.getProperty("uri");

    }

    public static String type() throws IOException {
        Properties p = property();
        return p.getProperty("method");
    }

    public static int runTime() throws IOException {
        Properties p = property();
        return Integer.parseInt(p.getProperty("repeat"));
    }

    public static File getPayLoadFile() throws IOException {
        Properties p = property();
        String basedir = p.getProperty("basedir");
        String payload = p.getProperty("payload");
        File payloadFile = new File(basedir+"/"+payload);
        return payloadFile;
    }

    public static JsonPath commonGet(String getApi) throws IOException {
        setBaseURI();
        filterlog();
        Response res = given()
                .cookie("session_id", getSessionID())
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .log().all()
                .get(getApi)
                .then().extract().response();

        logger.info(requestWriter.toString());
        logger.info(responseCapture.toString());
        System.out.println(requestCapture);
        return DataParser.rawToJSON(res);
    }

    public static JsonPath commonPost(String postApi, File body) throws IOException {
        setBaseURI();
        filterlog();
        Response res = given()
                .cookie("session_id", getSessionID()).contentType("application/json")
                .body(body)
                .filter(new RequestLoggingFilter(requestCapture))
                .filter(new ResponseLoggingFilter(responseCapture))
                .log().all()
                .post(postApi)
                .then().extract().response();

        logger.info(requestWriter.toString());
        logger.info(responseCapture.toString());

        return DataParser.rawToJSON(res);
    }

    public static List<String> getParsedStringList(String getAPI,String totalLocation,String ifEnabled, String find) throws IOException {
        JsonPath sanResouce= CommonAPI.commonGet(getAPI);

        StringTokenizer tokenizer = new StringTokenizer(ifEnabled, "*");
        String preifEnabled = tokenizer.nextToken();
        String postifEnabled = tokenizer.nextToken();

        StringTokenizer tokenizer2 = new StringTokenizer(find, "*");
        String preFind = tokenizer2.nextToken();
        String postFind= tokenizer2.nextToken();

        List<String> sanResourceList = new ArrayList<String>();
        for(int i = 0; i< sanResouce.getInt(totalLocation);i++){
            if(sanResouce.getBoolean(preifEnabled+i+postifEnabled))
              sanResourceList.add(sanResouce.getString(preFind+i+postFind));
        }
        return sanResourceList;
    }
    public static List<Integer> getParsedIntgerList(String getAPI,String totalLocation,String ifEnabled, String find) throws IOException {
        JsonPath sanResouce= CommonAPI.commonGet(getAPI);

        StringTokenizer tokenizer = new StringTokenizer(ifEnabled, "*");
        String preifEnabled = tokenizer.nextToken();
        String postifEnabled = tokenizer.nextToken();

        StringTokenizer tokenizer2 = new StringTokenizer(find, "*");
        String preFind = tokenizer2.nextToken();
        String postFind= tokenizer2.nextToken();

        List<Integer> sanResourceList = new ArrayList<Integer>();
        for(int i = 0; i< sanResouce.getInt(totalLocation);i++){
            if(sanResouce.getBoolean(preifEnabled+i+postifEnabled))
                sanResourceList.add(sanResouce.getInt(preFind+i+postFind));
        }
        return sanResourceList;
    }

}
