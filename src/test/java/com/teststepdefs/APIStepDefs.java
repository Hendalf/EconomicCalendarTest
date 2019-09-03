package com.teststepdefs;

import com.restapirequest.LongToShortUrlRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.IOException;

import static io.restassured.RestAssured.get;

public class APIStepDefs {
    private static final Logger log = Logger.getLogger(APIStepDefs.class);
    LongToShortUrlRequest postRequest;
    LongToShortUrlRequest deleteRequest;
    Response response;

    {
        try {
            postRequest = new LongToShortUrlRequest();
            deleteRequest = new LongToShortUrlRequest();
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    @Given("^User post long link to \"([^\"]*)\"$")
    public void userPostLongLinkTo(String url) throws Throwable {
        postRequest.createRequest(postRequest.createRequestHeaders(), postRequest.createRequestBody());
        response = postRequest.sendRequest(url);
    }

    @Then("^User get response with short link$")
    public void userGetResponseWithShortLink() throws Throwable {
        if (response.statusCode() == 200) {
            log.info("Request was taken successfully by server!");
        } else {
            switch (response.statusCode()) {
                case 400:
                    log.error("Server returns Bad postRequest response");
                    break;
                case 401:
                    log.error("Server returns Unauthorized response");
                    break;
                case 403:
                    log.error("Server returns Forbidden response");
                    break;
                case 404:
                    log.error("Server returns Not Found response");
                    break;
                default:
                       log.error("Something were wrong!");
            }
        }
    }

    @And("^Put response DTO to log file$")
    public void putResponseDTOToLogFile() throws Throwable {
        JsonPath jsonPathEvaluator = response.jsonPath();
        jsonPathEvaluator.get("data.short");
        log.info("The short url is: "+jsonPathEvaluator.get("data.short"));
        log.info("The body of response is: "+response.body().asString());
    }

    @Then("^Delete short link using \"([^\"]*)\"$")
    public void deleteShortLink(String url) throws Throwable {
        deleteRequest.createRequest(deleteRequest.createRequestHeaders());
        response = deleteRequest.sendRequest(url);
        if (response.statusCode() == 200) {
            log.info("Request was taken successfully by server!");
        } else log.info("Request was failed!");
    }
}