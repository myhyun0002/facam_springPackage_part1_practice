package org.example.mvc;

import org.example.mvc.controller.RequestMethod;

import java.util.Objects;

/**
 * url이 같아도 HttpMethod가 다르면 다른 api기 때문에 그 두개를 분리해서 저장해 놓는다.
 */
public class HandlerKey {
    private String url;
    private RequestMethod requestMethod;

    public HandlerKey(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }

    @Override
    public String toString() {
        return "HandlerKey{" +
                "url='" + url + '\'' +
                ", requestMethod=" + requestMethod +
                '}';
    }
}