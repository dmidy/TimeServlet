package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    private TemplateEngine engine;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        engine = new TemplateEngine();

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String timezoneParam = servletRequest.getParameter("timezone");
        if (timezoneParam == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (timezoneParam.matches("^UTC[+-](1[0-4]|[0-9])$")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            Context ctx = new Context();
            ctx.setVariable("title", "Invalid Timezone");
            ctx.setVariable("fishText", "The timezone parameter provided is invalid. Please provide a valid timezone.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            engine.process("timeTemplate", ctx, resp.getWriter());
        }
    }
}
