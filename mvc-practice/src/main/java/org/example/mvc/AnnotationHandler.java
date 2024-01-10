package org.example.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationHandler {

    // controller annotation이 붙은 class
    private final Class<?> clazz;

    // 해당 class에서 실행하고픈 메서드
    private final Method targetMethod;

    public AnnotationHandler(Class<?> clazz, Method targetMethod) {
        this.clazz = clazz;
        this.targetMethod = targetMethod;
    }

    public String handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
        Object targetObject = defaultConstructor.newInstance();

        // class 객체에 있는 메서드를 실행한다.
        return (String) targetMethod.invoke(targetObject, request, response);
    }
}
