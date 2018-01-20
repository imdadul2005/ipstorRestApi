package com.Freestor.RestAPI;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;

public class CommonAPI {

    public static final Logger logger = LogManager.getLogger(CommonAPI.class.getName());

    static String baseURI;
    static String currentSessionID = null;

    public static StringWriter writer;
    public static PrintStream captor;

    public static void filterlog(){

    }

    public static Header header(){
        return new Header("Content-Type","application/json");
    }
    public static void setBaseURI() throws IOException {
        Properties properties = property();
        RestAssured.baseURI = "http://"+ properties.getProperty("server")+"/ipstor/" ;
        baseURI = RestAssured.baseURI;

        // Added this part to redirect the logging information to a printStream.
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer), true);
        RestAssured.requestSpecification= RestAssured.given().config(RestAssured.config().logConfig(new LogConfig(captor,true)));

        // Setting the configuration file to receive the session_id for session id.
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("session_id"));

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
    public static String getSessionID() throws IOException {


        Properties p = property();
        String server = p.getProperty("server");
        String username = p.getProperty("username");
        String password = p.getProperty("password");
        String loginBody = "{\"server\": \"" +server+"\",\"username\":\"" +username +"\",\"password\":\"" +password +"\"}";

        if(currentSessionID==null) {
            Response loginResponse = given()
                    .header(CommonAPI.header()).log().all()
                    .body(loginBody)
                    .post(ApiResource.postLogin())
                    .then().log().all()
                    .extract().response();
            JsonPath auth =  DataParser.rawToJSON(loginResponse);
            currentSessionID = auth.get("id");

        }
        return currentSessionID;

    }

    public static JsonPath commonGet(String getApi) throws IOException {
        setBaseURI();
        String sessionID  = getSessionID();
        Response res = given().log().all()
                .sessionId(sessionID)
                .get(getApi)
                .then().log().all()
                .extract().response();
        System.out.println(writer.toString());
        logger.info(writer.toString());
        return DataParser.rawToJSON(res);
    }

    public static JsonPath commonPost(String postApi, File body) throws IOException {
        setBaseURI();
        filterlog();
        Response res = given()
                .cookie("session_id", getSessionID()).contentType("application/json")
                .body(body)
                .log().all()
                .post(postApi)
                .then().extract().response();

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
