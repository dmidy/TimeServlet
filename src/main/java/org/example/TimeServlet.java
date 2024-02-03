package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezone = (String) request.getParameter("timezone");

        TimeZone timeZoneInstance;
        if (timezone != null && !timezone.isEmpty()) {
            if (timezone.startsWith("UTC+")) {
                int offset = Integer.parseInt(timezone.substring(4));
                timeZoneInstance = TimeZone.getTimeZone("UTC+" + offset);
            } else if (timezone.startsWith("UTC-")) {
                int offset = Integer.parseInt(timezone.substring(4));
                timeZoneInstance = TimeZone.getTimeZone("UTC-" + offset);
            } else {
                timeZoneInstance = TimeZone.getTimeZone("UTC");
            }
        } else {
            timeZoneInstance = TimeZone.getTimeZone("UTC");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(timeZoneInstance);
        String currentTime = dateFormat.format(new Date());

        request.setAttribute("currentTime", currentTime);
        request.getRequestDispatcher("/WEB-INF/jsp/time.jsp").forward(request, response);
    }
}