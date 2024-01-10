package org.example.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 만약 controller에 있는 메서드에서 반환하는 model에 redirect:: 가 붙어있다면
 *      ㄴ RedirectView 의 render 실행해서 redirect 시킨다.
 *          - redirect는 브라우저로 응답을 보낸 후 다시 redirect 주소로 요청을 보내게 한다.
 *          - 따라서 request값과 reponse 값이 변하게 된다.
 * 없다면
 *      ㄴ JspView의 render 실행해서 forward 시킨다.
 *      - 브라우저로 갔다가 가는게 아닌 서버 내부에서 바로 간다.
 *      - request와 response가 똑같다.
 */
public interface View {
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}