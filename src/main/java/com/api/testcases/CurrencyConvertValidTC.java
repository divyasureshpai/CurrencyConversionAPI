package com.api.testcases;

import com.api.context.Context;
import com.api.context.TestContext;
import com.api.utils.APIConstants;
import com.api.utils.BaseAPIActions;
import com.api.utils.ConfigReader;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;


import java.util.HashMap;
import java.util.Map;

public class CurrencyConvertValidTC {

    ConfigReader configReader;
    BaseAPIActions baseAPIActions;
    Map<String, String> headers = new HashedMap<>();
    Map<String, Object> queryParams = new HashMap<>();
    TestContext testContext;

    public CurrencyConvertValidTC(ConfigReader configReader, BaseAPIActions baseAPIActions, TestContext testContext) {

        this.baseAPIActions = baseAPIActions;
        this.configReader = configReader;
        this.testContext = testContext;
        RestAssured.config = RestAssured.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(DOUBLE));
    }

    /**
     * This method stores the values to be passed as path parameter in a test context
     *
     * @param amount       the input amount passed from scenario
     * @param fromCurrency from currency passed from scenario
     * @param toCurrency   to currency passed from scenario
     */
    public void initializeServiceCall(String amount, String fromCurrency, String toCurrency) {

        testContext.setContext(Context.AMOUNT, Integer.parseInt(amount));
        testContext.setContext(Context.FROM_CURRENCY, fromCurrency);
        testContext.setContext(Context.TO_CURRENCY, toCurrency);

    }

    /**
     * This method hits the API with all the inputs
     *
     * @param isKeyValid checks if a valid key is passed
     */
    public void generateCurrencyConvertAPICall(boolean isKeyValid) {

        if (isKeyValid) {
            headers.put(APIConstants.APIKEY, configReader.getProperties("apiKey"));
        } else {
            headers.put(APIConstants.APIKEY, configReader.getProperties("apiKey") + "eee");
        }
        queryParams.put(APIConstants.TO_CURRENCY_KEY, testContext.getContext(Context.TO_CURRENCY).toString());
        queryParams.put(APIConstants.FROM_CURRENCY_KEY, testContext.getContext(Context.FROM_CURRENCY).toString());
        queryParams.put(APIConstants.AMOUNT, testContext.getContext(Context.AMOUNT).toString());
        Response response = baseAPIActions.executeGETRequest(configReader.getProperties("currencyConverturl"), queryParams, headers);
        testContext.setContext(Context.CURRENCY_CONVERT_RESPONSE, response);
    }

    /**
     * Verify the response for a valid input
     */
    public void validateResponseWithValidInput() {

        Response response = (Response) testContext.getContext(Context.CURRENCY_CONVERT_RESPONSE);
        String s = response.jsonPath().getString("query.from");
        double amount = response.jsonPath().getDouble("info.rate");
        int rates = response.jsonPath().getInt("query.amount");
        double result = amount * rates;
        response.
                then().
                statusCode(200).
                body("success", equalTo(true),
                        "query.from", equalTo(testContext.getContext(Context.FROM_CURRENCY)),
                        "query.to", equalTo(testContext.getContext(Context.TO_CURRENCY)),
                        "query.amount", equalTo((Integer) testContext.getContext(Context.AMOUNT)),
                        "result", is(closeTo(result, 0.1))
                );
        response.
                then().
                body(matchesJsonSchemaInClasspath("\\Schema\\CurrencyConvertValidInputResponseSchema.json"));
    }

    /**
     * Verify the response with an invalid currency input
     */
    public void validateResponseWithInvalidCurrency(String info, String type) {

        Response response = (Response) testContext.getContext(Context.CURRENCY_CONVERT_RESPONSE);
        response.then().body(matchesJsonSchemaInClasspath("\\Schema\\CurrencyConvertInvalidInputResponse.json"));
        System.out.println(info);
        System.out.println(response.jsonPath().getString("error.info"));
        response.
                then().
                statusCode(200).
                body("success", equalTo(false),
                        "error.code", equalTo(402),
                        "error.type", equalTo(type),
                        "error.info", equalTo(info)
                );
    }

    /**
     * verify the response with an invalid amount
     */
    public void validateResponseWithInvalidAmount() {

        Response response = (Response) testContext.getContext(Context.CURRENCY_CONVERT_RESPONSE);
        response.then().body(matchesJsonSchemaInClasspath("\\Schema\\CurrencyConvertInvalidInputResponse.json"));
        response.
                then().
                statusCode(200).
                body("success", equalTo(false),
                        "error.code", equalTo(403),
                        "error.info", equalTo("You have not specified an amount to be converted. [Example: amount=5]"),
                        "error.type", equalTo("invalid_conversion_amount")
                );
    }

    /**
     * verify the response with invalid key in the header
     */
    public void validateResponseWithInvalidKey() {

        Response response = (Response) testContext.getContext(Context.CURRENCY_CONVERT_RESPONSE);
        response.then().body(matchesJsonSchemaInClasspath("\\Schema\\CurrencyConvertErrorResponse.json"));
        response.
                then().
                statusCode(401).
                body("message", equalTo("Invalid authentication credentials")
                );

    }
}
