# Assignment 4: Currency Converter

Previous assignments were designed to work with widgets, activities, and
fragments. Now, you should all be able to design and implement basic yet
commonly used user interfaces.

Last week, we were introduced to libraries and third-party APIs. The
objective of this assignment is to make you work with a third-party API:
Foreign Exchange Rates API.

## Description of the app

The design of this app is quite simple. Implementing the following
components is a minimum:

- Two `Spinners` that allow users to select two currencies. The data
  displayed by these `Spinners` should come from a list of currencies
  retrieved from Foreign Exchange Rates API.
- An `EditText` with an adequate `inputType` which is used to type in
  the amount in the original currency.
- A `TextView` that should display the converted amount in the other
  currency.
- A `Button` that calls the API in order to convert the currencies.

## Technical description of the app

The app will use the Foreign Exchange Rates API. You can find a brief
documentation here: http://exchangeratesapi.io/. This API is free and
does not require the developer to generate a key.

**Note**: To get a list of all currencies, you can create a GET request
to https://api.exchangeratesapi.io/latest, and parse all the currencies
in the JSON response. Be careful to also add "EUR" to your list of
currencies.

## Bonus

For bonus points, you can try to implement the following:

- Make the app work even offline by saving currency rates locally
  (using, for example, `SharedPreferences`);
- Add a `Button` to quickly swap the two currencies used for a
  conversion (for example, EUR -> USD now becomes USD -> EUR).

## Deadline for submission

Deadline: November 18, 2020 23:59 CET.

This is your last chance to use your lateness policy if you wish to.
Issues with configuring the IDE or working with Git/GitHub will not
warrant any deadline extension.
