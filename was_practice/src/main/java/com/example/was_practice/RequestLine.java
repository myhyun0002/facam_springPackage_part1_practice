package com.example.was_practice;

import java.util.Objects;

/**
 * HttpRequest
 *      - RequestLine (GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
 *          - HttpMethod
 *          - path
 *          - querystring
*       - Header
 *      - Body
 */
public class RequestLine {
    private final String method;        // GET
    private final String urlPath;       // /calculate
    private QueryStrings queryStrings;         // operand1=11&operator=*&operand2=55

    public RequestLine(String method, String urlPath, String queryString) {
        this.method = method;
        this.urlPath = urlPath;
        this.queryStrings = new QueryStrings(queryString);
    }

    public RequestLine(String requestLine) {

        /*
            처음에 requestLine으로는
            GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
            위가 들어오게 된다.

            공백 기준으로 나누면
            tokens[0] = GET     -> method
            tokens[1] = /calculate?operand1=11&operator=*&operand2=55
         */
        String[] tokens = requestLine.split(" ");
        this.method = tokens[0];

        String[] urlPathTokens = tokens[1].split("\\?");
        /*
                /calculate?operand1=11&operator=*&operand2=55

            이거를 ? 로 나누게 되면
            urlTokens[0] = /calculate
            urlTokens[1] = operand1=11&operator=*&operand2=55
         */

        this.urlPath = urlPathTokens[0];            // /calculate

        if(urlPathTokens.length == 2) {
            this.queryStrings = new QueryStrings(urlPathTokens[1]);    // operand1=11&operator=*&operand2=55
        }
    }

    public boolean isGetRequest() {
        return this.method.equals("GET");
    }

    public boolean matchPath(String path) {
        return this.urlPath.equals(path);
    }

    public QueryStrings getQueryStrings() {
        return this.queryStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(getQueryStrings(), that.getQueryStrings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, getQueryStrings());
    }
}
