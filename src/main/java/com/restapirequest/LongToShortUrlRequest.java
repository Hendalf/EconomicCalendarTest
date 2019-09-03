package com.restapirequest;

import com.auxiliary.EncryptDecryptStringWithDES;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
public class LongToShortUrlRequest {
    private List<Header> requestHeaders;
    private Headers linkHeaders;
    private JSONObject postRequestBody;
    RequestSpecification request;
    public static String longUrl;
    private final Properties properties;
    private EncryptDecryptStringWithDES eds;
    private String encodedAuthToken;
    private String decodedAuthToken;
    private String enryptedKey;

    public LongToShortUrlRequest() throws IOException {
        properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        enryptedKey = properties.getProperty("encrypted.key");
        encodedAuthToken = properties.getProperty("encrypted.authToken");
        eds = new EncryptDecryptStringWithDES(enryptedKey);
        decodedAuthToken = eds.decrypt(encodedAuthToken);
        requestHeaders = new ArrayList<>();
        postRequestBody = new JSONObject();
        request = RestAssured.given();
    }

    public Headers createRequestHeaders(){
        requestHeaders.add(new Header("X-AUTH-TOKEN", decodedAuthToken));
        requestHeaders.add(new Header("Content-Type", "application/json"));
        requestHeaders.add(new Header("User-Agent", properties.getProperty("user.agent")));
        linkHeaders = new Headers(requestHeaders);
        return linkHeaders;
    }

    public JSONObject createRequestBody(){
        return postRequestBody.put("type", "link").put("url", longUrl).put("utm", "utm_campaign=[domain]");
    }

    public void createRequest(Headers headers, JSONObject body){
        request.headers(headers);
        request.body(postRequestBody.toString());
    }

    public void createRequest(Headers headers){
        request.headers(headers);
    }

    public Response sendRequest(String restServerLink){
        return request.post(restServerLink);
    }
}
