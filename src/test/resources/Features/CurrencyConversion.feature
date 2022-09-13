Feature: Verify the currency conversion feature of the Fixer API

  Scenario Outline: Verify that Given two valid currencies and valid amount converts successfully with data from Fixer API

    Given I have Initialized API Service call for Currency Conversion API to convert "<amount>" "<fromCurrency>" to "<toCurrency>"
    When Currency Conversion API is Invoked for given data
    Then Verify that the response after conversion is valid
    Examples:
      | amount | fromCurrency | toCurrency |
      | 1587   | USD          | NOK        |
      | 169000 | NOK          | EUR        |
      | 89000  | NOK          | SEK        |

  Scenario Outline: Verify that Given two currencies,with invalid currency and valid amount API gives response saying invalid currency
    Given I have Initialized API Service call for Currency Conversion API to convert "<amount>" "<fromCurrency>" to "<toCurrency>"
    When Currency Conversion API is Invoked for given data
    Then  verify the response shows as invalid currency with message "<expectedInfo>" and "<expectedType>" in the response
    Examples:
      | amount | fromCurrency | toCurrency | expectedInfo                                                       | expectedType          |
      | 1587   | USD          | NOO        | You have entered an invalid \"to\" property. [Example: to=GBP]     | invalid_to_currency   |
      | 169000 | NOO          | EUR        | You have entered an invalid \"from\" property. [Example: from=EUR] | invalid_from_currency |
      | 89000  | NOO          | SEE        | You have entered an invalid \"from\" property. [Example: from=EUR] | invalid_from_currency |

  Scenario Outline: Verify that Given two valid currencies and with invalid amount API gives response saying invalid amount
    Given I have Initialized API Service call for Currency Conversion API to convert "<amount>" "<fromCurrency>" to "<toCurrency>"
    When Currency Conversion API is Invoked for given data
    Then  verify the response shows as invalid amount
    Examples:
      | amount | fromCurrency | toCurrency |
      | 0      | USD          | INR        |
      | -41    | NOK          | EUR        |

  Scenario Outline: Verify that Given two valid currencies and valid amount, but an invalid apiKey

    Given I have Initialized API Service call for Currency Conversion API to convert "<amount>" "<fromCurrency>" to "<toCurrency>"
    When Currency Conversion API is Invoked for given data but invalid api key
    Then Verify that the response gives unauthorised error
    Examples:
      | amount | fromCurrency | toCurrency |
      | 1587   | USD          | NOK        |