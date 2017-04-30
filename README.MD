# Currency Exchange

Currency exchange **app** REST API provides currency amount conversion and 
historical currency high rate data of 10 days (intentional to simplify API).

## Running app

Pre-requisites:

- JDK8
- NodeJS 7.7.3+
- docker 1.12.0+
- docker-compose 1.11.2+

```
$ ./gradlew build shadow
$ docker-compose up --build
```

## Design notes

- Separation of services and REST API.
- Separation of backend and UI ([react-slingshot](https://github.com/coryhouse/react-slingshot) boilerplate)
- Prefer package by feature (vs layer)
- For real world data for currencies Yahoo Finance API was used as it is public (no registration required).
- Use spring cache for caching result
- independent API acceptance test execution

## Acknowledgments

To minimize the scope, the following were not implemented:

- Paging
- Security
- Database support (caching is done in memory)
- Proper immutability on representations, etc.
- Not all currencies have recent historical data. In such case no will be displayed.
- Mock yahoo finance application for development environment
- UI tests
- More appropriate separation of concerns
- Currency rate history is always between `baseCurrency` and USD (API limitations)

## TODO
- clean up dependencies
- Backwards conversion
- Custom tooltips for history
- do not chain dispatchers in actions
- management.security.enabled
