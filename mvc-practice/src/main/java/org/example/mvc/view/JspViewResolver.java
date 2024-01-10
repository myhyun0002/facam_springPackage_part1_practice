package org.example.mvc.view;

import static org.example.mvc.view.RedirectView.DEFAULT_REDIRECT_PREFIX;

public class JspViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName) {

        // 만약에 메서드에 return 하는게 redirect::home 이렇게 redirect라면 redirect를 시켜준다.
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }

        // 그게 아니라면 forwarding을 시켜준다.
        return new JspView(viewName + ".jsp");
    }
}