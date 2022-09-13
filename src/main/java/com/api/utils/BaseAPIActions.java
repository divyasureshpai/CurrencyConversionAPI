package com.api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

public class BaseAPIActions {

    /**
     * This method is common for all the GET requests
     * @param url is the base url
     * @param queryParams is the Map of query parameters
     * @param headers is the Map of header values
     * @return returns the response after hitting the API
     */
    public Response executeGETRequest(String url, Map<String ,Object> queryParams,Map<String,String> headers){

        RequestSpecification requestSpecification= RestAssured.given().contentType(ContentType.JSON);
        if(!MapUtils.isEmpty(queryParams)){
            requestSpecification.queryParams(queryParams);
        }
        if(!MapUtils.isEmpty(headers)){
            requestSpecification.headers(headers);
        }
        return requestSpecification.when().get(url);
    }
}
