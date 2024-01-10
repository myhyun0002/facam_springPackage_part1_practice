package org.example.mvc.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * - view
 *      - controller 안에 있는 Url과 HttpMethod가 일치하는 메서드를 실행 후 return 하는 model name을 저장한다.
 * - model
 *      - 만약 home.jsp같이 그 파일 안에 어떤 값이 필요하다면 필요한 값을 key,value 형식으로 저장한다.
 */
public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }
}