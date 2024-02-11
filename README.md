# Challenge

## :computer: How to execute

1. Clone the project or download the file from location https://github.com/yaseenshar/delivery-payment-api/blob/master/wefox-challange-implementation.rar
2. Extract the download files wefox-challange-implementation.rar to your desired location.
3. To start the services run docker-compose up . Use -d to start in detached mode and run the containers in the background.
4. Newly implemented microservice named payment-api is accessible on location http://localhost:8081, need to check port is not occupied already.
5. Starts the technical test goto following URL http://localhost:9000/ and click on Start Test button.
6. After starting technical test logs can be viewed on location http://localhost:9000/logs.

## :memo: Notes

_Some notes or explanation of your solution..._

## :pushpin: Things to improve

1. Global exception handling can enhance the code by providing a centralized mechanism instead of individual try-catch blocks.

2. Consider organizing the RestAPI requests in a dedicated class to improve code organization and maintainability.

3. It might be beneficial to implement asynchronous handling for logging RestAPI requests. This way, the system won't be delayed while waiting for responses.
