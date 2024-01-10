package org.example.mvc;

import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping{
    private final Object[] basePackage;

    private final Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);

        // Controller라는 어노테이션이 붇인 클래스들을 reflection으로 순회하면서 가지고 온다.
        Set<Class<?>> clazzesWithControllerAnnotation = reflections.getTypesAnnotatedWith(org.example.mvc.annotation.Controller.class, true);

        // controller 어노테이션이 붙인 클래스들을 순회하면서 다음을 수행한다.
        clazzesWithControllerAnnotation.forEach(clazz ->

                // class 내에 선언된 모든 메서드들을 순회한다.
                Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {

                    // 각 메서드들을 돌아보면서 RequstMapping이라는 어노테이션이 있다면 해당 RequestMapping 객체를 반환한다.
                    RequestMapping requestMappingAnnotation = declaredMethod.getDeclaredAnnotation(RequestMapping.class);

                    // RequestMapping의 값들을 순회하면서 여러가지 HTTPMethod를 설정할 수 있도록 해놨기 때문에
                    // 하나씩 handlers에 저장한다.
                    Arrays.stream(getRequestMethods(requestMappingAnnotation))
                            .forEach(requestMethod -> handlers.put(
                                    new HandlerKey(requestMappingAnnotation.value(), requestMethod), new AnnotationHandler(clazz, declaredMethod)
                            ));

                })
        );
    }

    // RequstMapping 어노테이션의 값 중 method에 있는 값을 가지고 온다.
    private RequestMethod[] getRequestMethods(RequestMapping requestMappingAnnotation) {
        return requestMappingAnnotation.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
