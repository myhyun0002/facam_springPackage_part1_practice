package org.example.mvc;

import org.example.mvc.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller 인터페이스로 구현된 controller를 init()할 때 mappings에 넣어준다.
 * 만약 http://localhost:8080/user/form GET으로 들어왔다면 ForwardController가 실행되게끔 key,value로 미리 mapping해 놓는다.
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init() {
//        mappings.put(new HandlerKey("/", RequestMethod.GET), new HomeController());
        mappings.put(new HandlerKey("/user/form", RequestMethod.GET), new ForwardController("/user/form"));
        mappings.put(new HandlerKey("/users", RequestMethod.GET), new UserListController());
        mappings.put(new HandlerKey("/users", RequestMethod.POST), new UserCreateController());

        mappings.keySet().forEach(path ->
                logger.info("url path: [{}], controller: [{}]", path, mappings.get(path).getClass()));
    }

    @Override
    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }
}