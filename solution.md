# Currency Converter
<br />

## Functional Requirements:

* The Currency converter application allows users to view the current localized price of a cryptocurrency.
* The locale should be retrieved from an optional user provided IP, in case it's not provided the IP from the request should be used

## Functional Scope, Assumptions & Discovery:

From a user perspective, first use case is to list all cryptocurrencies available in the market by the means of a dropdown in UI. This could have been implemented in a number of ways as follows:

   - Implementing an 3rd party API to fetch all the coin metadata like coin code and name to populate the data in dropdown. But this data almost never gets changed. Unless a caching mechanism is implemented on UI level (which is out of scope here), calling a 3rd party API repeatedly is badly off.
   - Keeping a static list in UI
   - Storing the data in db and retreiving it. I am going ahead with this approach since I am comfortable with databases and providing an API to retreive the cryptocurrency list.
 
 
Once the use selects a currency, he has the option to provide an Ip address which can be used to map the locale (country) to obtain the local currency. But this is an optional field. So a user can send a request to find the rate without sending an IP address. In that case, the application has to determine the IP address through which it communicates to the internet. For this use case, I plan on hitting a url which can retreive us back our IP address, which then could be used as a locale reference.

The next part is to find the country/locale to which the IP address is being used. This can be done in two ways

  - There are websites which provide us information about range of IPs and their corresponding countries.
  We can store this csv file and map the IP from the request/application host to the correct locale.
  - Also we could hit already available 3rd part IP lookup websites and obtain the locale information.I  would be going through this approach since we can get other metadata from the response which could be useful to us. The application would also be storing the currency code against the country code. So once locale is identified and application could map it against the currency code saved in the database.
  
The last part is to obtain the localized market price. Here, we have to make a decision whether to fetch the currency rate in USD and convert it to the local currency, which would probably lead to calling two distinct 3rd party APIs one after another two get the coin market value and exchange rates. So as part of the discovery, I have identified publicly available APIs hosted by Coinbase which could be used upon freely. Their pricing API accepts request as currency/crypto pair and retreives the value in the localized form. Another assumption that should be made is on whether to round the decimal places for the price. We can plan in such a way as to pass a int value and locking its decimal places on that. Meanwhile whether to round the decimal places or just use a substring is something to be decided upon, since every decimal place matters in a crypto price. But since we just want to know the price and do not use it to buy, it would not be a real impact.

## DISCOVERY
During the discovery phase, I had to find an API documentation that fits our purpose. So went through and compared a lot of different API documentations provided by platforms like Coingecko, CoinSwitch, CoinMarketCp, CoinAPI. After going through parameters like rate limiting, api key authentication, paid service and the aspect of fetching by currency and coin pair in one go, decided to go with CoinBase which provides a good documentation along with other advantages such as increased rate limit of 10,000 per hour, No API authentication/authorization for the APIs we need to consume.

Coinbase API Documentation
https://developers.coinbase.com/api/v2#data-endpoints

I have identified the [Spot price API](https://developers.coinbase.com/api/v2#get-spot-price) from the documentation for our requirement
Sample Request - curl https://api.coinbase.com/v2/prices/BTC-USD/spot

Sample Response - {
  "data": {
    "amount": "1015.00",
    "currency": "USD"
  }
}

I have identified the [Get all currencies API](https://api.pro.coinbase.com/currencies) to identify all the cryptocurrencies we need to choose from the UI for the request. I have mapped all those currencies with type crypto to obtain the list. The application will store this information in database and UI can request for it to populate the list.


I have also downloaded and mapped ISO 4217 format for this [link](https://gist.github.com/HarishChaudhari/4680482) which countains all relevant information for us like country code, name, currency code and name. The data looks outdated though will compare and change old references.

All these data will be mapped onto databse through the a csv file which I intend to achieve through liquibase migration

### Database

Data storage is achieved through the h2 database which integrated along with Liquibase allows easy data migration. Application will lookup two tables one containing country-currency mapping and other containing cryptocurrency list.





