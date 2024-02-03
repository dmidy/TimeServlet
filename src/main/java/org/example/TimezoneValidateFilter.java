package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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
        if (timezoneParam.matches("^UTC[+%2B-](1[0-4]|[0-9])$")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/invalid-timezone.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
