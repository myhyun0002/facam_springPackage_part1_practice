package org.example.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RedirectView implements View {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 만약 home.jsp 파일 안에 name이라는 값을 넣어야 한다면
        // request.setAttribute("name","sldkfj");
        // 이렇게 넣었어야 하는데 그거를 model이라는 파라미터로 받아와서 넣는다.
        model.forEach(request::setAttribute);
        response.sendRedirect(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}