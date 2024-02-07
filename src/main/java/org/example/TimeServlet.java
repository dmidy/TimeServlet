package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezone = request.getParameter("timezone");
        DateTimeZone timeZone;

        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            if (timezone == null || timezone.isEmpty()){
                timezone = "UTC";
            }
        } else {
            Cookie cookie = new Cookie("lastTimezone", timezone);
            response.addCookie(cookie);
        }
        if (timezone.startsWith("UTC+")) {
            int offsetHours = Integer.parseInt(timezone.substring(4));
            timeZone = DateTimeZone.forOffsetHours(offsetHours);
        } else if (timezone.startsWith("UTC-")) {
            int offsetHours = -Integer.parseInt(timezone.substring(4));
            timeZone = DateTimeZone.forOffsetHours(offsetHours);
        } else {
            timeZone = DateTimeZone.forID(timezone);
        }

        DateTime dateTime = DateTime.now(timeZone);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTime.toString(formatter);

        Context ctx = new Context();
        ctx.setVariable("title", "Current Time");
        ctx.setVariable("fishText", "Current time:");
        ctx.setVariable("currentTime", currentTime);
        ctx.setVariable("timezone", timezone);

        engine.process("timeTemplate", ctx, response.getWriter());
    }
}
