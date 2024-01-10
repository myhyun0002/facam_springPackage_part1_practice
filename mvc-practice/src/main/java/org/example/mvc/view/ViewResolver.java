package org.example.mvc.view;

/**
 * controller 안에 메서드 반환값에 redirect:: 유무에 따라 실행시킬 view를 결정해주는 클래스
 */
public interface ViewResolver {
    View resolveViewName(String viewName);
}