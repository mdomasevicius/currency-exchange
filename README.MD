# Currency Exchange

Currency exchange **app** REST API provides currency amount conversion and 
historical currency rate data of 10 days (highest of the day).

## Running app

Pre-requisites:

- JDK8
- NodeJS 7.7.3+
- docker 1.12.0+
- docker-compose 1.11.2+


## Design notes

- Separation of services and REST API.
- Separation of backend and UI
- Prefer package by feature (vs layer)
- For real world data for currencies Yahoo Finance API was used as it is public (no registration required). 

## Acknowledgments

To minimize the scope, the following were not implemented:

- Paging
- Security
- Database support
- Proper immutability on representations, etc.
