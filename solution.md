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

Sample Response - {"data":{"base":"BTC","currency":"USD","amount":"29560.43"}}

I have identified the [Get all currencies API](https://api.pro.coinbase.com/currencies) to identify all the cryptocurrencies we need to choose from the UI for the request. I have mapped all those currencies with type crypto to obtain the list. The application will store this information in database and UI can request for it to populate the list.

I have also downloaded and mapped ISO 4217 format for this [link](https://gist.github.com/HarishChaudhari/4680482) which countains all relevant information for us like country code, name, currency code and name. The data looks outdated though will compare and change old references.

All these data will be mapped onto database through the a csv file which I intend to achieve through liquibase migration.

### Database

Data storage is achieved through the h2 database which integrated along with Liquibase allows easy data migration. Application will lookup two tables one containing country-currency mapping and other containing cryptocurrency list. 2 database files referenced from h2 will be created in the host system to store the country level information as well as cryptocurrency list. The h2 database is file based and will be available on the host system with files prefixed with 'currency-converter-dev'.
If any errors come due to databasechangelog, which is not so probable, please find and delete the files prefixed with 'currency-converter-dev' and restart application

### How to Run

* Go into the project root folder run
* mvn clean
* mvn spring-boot:run

To create an executable JAR - mvn package (available inside target folder)

Also you can import this as a maven project in eclipse and run. (Needs lombok configured for eclipse)

Default port for the application is **8080** by default.

Please note Java version used is 11
If not for over engineering would have created a container and setup a build using it.

Application can be loaded at localhost:8080/crypto


### API Documentation summary

* Basically designed 4 APIs.
1 Get available cryptocurrencies list - GET - localhost:8080/crypto
2 Return localized price information of a cryptocurrency - POST - localhost:8080/crypto

{
    "address": "85.214.132.117",
    "code": "SHIB"
}

### Thymeleaf

I have used Thymeleaf to integrate the UI with the backend APIs. I have used the most straightforward UI design to represent the functionality. I believe there is still scope of improvement there.

### TODO 

As I could focus on development activities mainly only over the weekend, there are a lot of improvements I wanted to to do, but could not make it in the raised PR. Some of the items listed are :

1. Cover exceptions in unit test
   I have caught many custom exceptions on various cases, mainly dealing with public APIs. If I get time, will get them covered too.
2. Dockerizing the application
   Initially I had the plan to dockerize the application, but wanted to do that at the end.
3. Moving items like exception strings, decimal places count to application properties
   Wanted to bring flexibility to the application to update items that are predetermined to application properties.
4.  Improving API performance
   While integrating thymeleaf, price request submit forces the fetch all cryptocurrency query to run again and load the data into the model. I     could not spend too much time removing that dependency. Wanted to explore different way to load the page without refreshing or just change the   message value only. Angular or use of javascript could have fixed this butuploaded could not get time to explore. Also since we are calling same data again, wanted to try caching, but since this is a simple application, decided not to.
5. Currency symbol Implementation
  Also I have used the java util Currency class to determine the symbol. Wanted to fetch all the currency symbols with respect to the currency codes in the database and map it across the same. But needed more time fetching that information
6. Updating latest currency data
  As I have mentioned I have used a csv sheet to upload the currency and country data. This data is obsolete and needs some updation. For example Malta and Cyprus. I removed these from the sheet now. So tracking their localised price will throw a custom error. I wish to update them when I get time.
7. Using slf4j to log important aspects in the application
   I had used sysouts temporarily to monitor the values in runtime. Wanted to replace it all with log information, but could not get time.
8. Rounding logic
   How to round the decimals is a one aspect I wanted to do it more accurately. Since problem statement did not define what exactly is the correct decimal places. Did not explore more on it. But I believe whatever change on that end can easily be udpated.
  
### Possible issues and data concerns
1. Since we are dealing with 3rd party public APIs there can be issues that we cannot expect, I have tried to catch all these, still I believe there are lots of other cases yet to be found and mapped properly with appropriate error messages.
For example, following API sometime does not return correct response even though XRP is a valid cryptocurrency. I decided to explore why this is occurring in detail later :

curl https://api.coinbase.com/v2/prices/XRP-USD/spot
{"errors":[{"id":"not_found","message":"Invalid base currency"}]}


