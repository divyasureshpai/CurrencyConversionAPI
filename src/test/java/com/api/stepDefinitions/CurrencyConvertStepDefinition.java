package com.api.stepDefinitions;

import com.api.testcases.CurrencyConvertValidTC;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CurrencyConvertStepDefinition {

    private CurrencyConvertValidTC currencyConvertValidTC;

    public CurrencyConvertStepDefinition(CurrencyConvertValidTC currencyConvertValidTC){
        this.currencyConvertValidTC=currencyConvertValidTC;
    }

    @Given("I have Initialized API Service call for Currency Conversion API to convert {string} {string} to {string}")
    public void iHaveInitializedAPIServiceCallForCurrencyConversionAPIToConvertTo(String amount, String fromCurrency, String toCurrency) {

        currencyConvertValidTC.initializeServiceCall(amount,fromCurrency,toCurrency);
        //currencyConvertValidTC.generateRateAPIResponse();
    }

    @When("Currency Conversion API is Invoked for given data")
    public void currencyConversionAPIIsInvokedForGivenData() {

        currencyConvertValidTC.generateCurrencyConvertAPICall(true);
    }

    @Then("Verify that the response after conversion is valid")
    public void verifyThatTheResponseAfterConversionIsValid() {

        currencyConvertValidTC.validateResponseWithValidInput();
    }

    @Then("verify the response shows as invalid currency")
    public void verifyTheResponseShowsAsInvalidCurrency() {



    }

    @Then("verify the response shows as invalid amount")
    public void verifyTheResponseShowsAsInvalidAmount() {

        currencyConvertValidTC.validateResponseWithInvalidAmount();
    }


    @When("Currency Conversion API is Invoked for given data but invalid api key")
    public void currencyConversionAPIIsInvokedForGivenDataButInvalidApiKey() {

        currencyConvertValidTC.generateCurrencyConvertAPICall(false);
    }

    @Then("Verify that the response gives unauthorised error")
    public void verifyThatTheResponseGivesUnauthorisedError() {

        currencyConvertValidTC.validateResponseWithInvalidKey();
    }

    @Then("verify the response shows as invalid currency with message {string} and {string} in the response")
    public void verifyTheResponseShowsAsInvalidCurrencyWithMessageAndInTheResponse(String info, String type) {

        currencyConvertValidTC.validateResponseWithInvalidCurrency(info,type);
    }
}
