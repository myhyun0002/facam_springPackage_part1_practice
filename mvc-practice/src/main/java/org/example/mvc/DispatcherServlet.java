package org.example.mvc;

import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() {
        RequestMappingHandlerMapping rmhm = new RequestMappingHandlerMapping();
        rmhm.init();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("org.example");
        ahm.initialize();

        handlerMappings = List.of(rmhm, ahm);
        handlerAdapters = List.of(new SimpleControllerHandlerAdapter(), new AnnotationHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        // requestUrl과 method에 맞는 controller가 있는지 확인하고 있다면 controller를 반환
        // handler 변수에는 controller가 들어간다.
        Object handler = handlerMappings.stream()
                // requestUrl과 method에 맞는 controller가 있는지 확인
                .filter(hm -> hm.findHandler(new HandlerKey(requestURI, requestMethod)) != null)
                // 있다면 해당 controller를 반환
                .map(hm -> hm.findHandler(new HandlerKey(requestURI, requestMethod)))
                .findFirst()
                .orElseThrow(() -> new ServletException("No handler for [" + requestMethod + ", " + requestURI + "]"));

        try {
            // handler 변수에 들어있는 controller가
            // controller가 instance로 구현되어 있다면 - SimpleControllerHandlerAdapter
            // controller가 annotation으로 구현되어 있다면 - AnnotationHandlerAdapter
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            // 두 개의 adapter 중 해당하는 adapter에서 handle() 실행
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // ViewResolver는 viewName에 redirect::가 들어있다면 RedirectView
            // redirect가 없다면 JspViewResolver 반환
            for (ViewResolver viewResolver : this.viewResolvers) {
                View view = viewResolver.resolveViewName(modelAndView.getViewName());

                // 현재 여기에서는 .jsp 파일 안에 넣어줘야 하는 값을 받는 코드는 없다.
                // home.jsp 안에 name이라는 값을 넣어서 보여줘야 한다면
                // request.setAttribute("name","sdlfk"); 이렇게 넣어줘야 하는데
                // 그 코드는 없다. 그래서 일단 ModelAndView 객체에 위와 같은 값을 mapping해주는 map을 넣는 코드는 없다.
                view.render(modelAndView.getModel(), request, response);
            }
        } catch (Throwable e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
