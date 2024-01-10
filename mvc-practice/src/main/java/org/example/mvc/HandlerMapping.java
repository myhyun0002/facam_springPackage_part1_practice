package org.example.mvc;

/**
 * HandlerMapping
 *  - 각 클래스들을 초기화할 때 controller와 <url,HttpMethod>를 매핑해서 mapping이라는 변수에 저장한다.
 *  - 만약 /user,GET에 해당하는 값이 UserController에 있다면 UserController를 반환해준다. (findHandler()를 실행했을 때)
 *      ㄴ RequestMappingHandlerMapping
 *      ㄴ AnnotationHandlerMapping
 */
public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}