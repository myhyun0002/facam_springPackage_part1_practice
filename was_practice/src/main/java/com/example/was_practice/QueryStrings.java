package com.example.was_practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryStrings {
    private List<QueryString> queryStrings = new ArrayList<>();

    /*
        queryStringLine = "operand1=11&operator=*&operand2=55"

        이걸
        new QueryString("operand1","11");
        new QueryString("operator","*");
        new QueryString("operand2","55");
        로 나눠야 한다.
     */
    public QueryStrings(String queryStringLine) {
        String[] queryStringTokens = queryStringLine.split("&");
        Arrays.stream(queryStringTokens)
                .forEach(queryStringToken -> {
                    String[] values = queryStringToken.split("=");
                    if(values.length != 2){
                        throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열입니다.");
                    }
                    QueryString queryString = new QueryString(values[0],values[1]);
                    this.queryStrings.add(queryString);
                });
    }

    public String getValue(String key) {
        return this.queryStrings.stream()
                .filter(queryString -> queryString.exists(key))
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }
}
