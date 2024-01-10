package org.example.mvc;

import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * suuports 메서드의 매개변수로는 controller가 들어온다.
 * - 해당 controller가 Controller 어노테이션을 가지고 있다면
 *      ㄴ AnnotationHandlerAdapter
 * - 해당 controller가 Controller 인터페이스를 가지고 있다면
 *      ㄴ SimpleControlHandlerAdapter
 *
 * handle 메서드는 controller 안에 url과 HttpMethod에 맞는 메서드를 실행시키고 return하는 model name을 받아와서 ModelAndView 객체에 저장하고 반환
 */

public interface HandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}