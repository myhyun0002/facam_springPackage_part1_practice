package com.example.was_practice;

import com.example.was_practice.calculate.Calculator;
import com.example.was_practice.calculate.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);

    private final Socket clientSocket;

    public ClientRequestHandler(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        logger.info("[ClientRequestHandler] new client {} started", Thread.currentThread().getName());
        try (InputStream inputStream = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
            // http://localhost:8080 가 들어오면 그에 따라 파싱되는 값들을 bufferReader로 읽어온다.
                    /*
                        GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
                        Host: localhost:8080
                        Connection: Keep-Alive
                        User-Agent: Apache-HttpClient/4.5.13 (Java/17.0.4.1)
                        Accept-Encoding: gzip,deflate

                        위의 값이 bufferReader로 들어온다.
                        저 값들을 HttpRequest 객체에 저장할 것이다.

                        HttpRequest
                            - RequestLine (GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
                                - HttpMethod    (GET)
                                - path          (/calculate)
                                - queryString   (operand1=11&operator=*&operand2=55)
                            - Header
                            - Body
                     */
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            DataOutputStream dataOutputStream = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(br);

            // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
            // method가 GET 이고 path가 /calculate라면 그 때 queryString인 operand1=11&operator=*&operand2=55 이걸 가지고 온다.
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                QueryStrings queryStrings = httpRequest.getQueryStrings();

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                byte[] body = String.valueOf(result).getBytes();

                HttpResponse response = new HttpResponse(dataOutputStream);
                response.response200Header("application/json", body.length);
                response.responseBody(body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}