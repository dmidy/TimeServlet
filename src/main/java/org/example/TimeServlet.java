package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezone = request.getParameter("timezone");
        DateTimeZone timeZone;

        if (timezone == null || timezone.isEmpty()){
            timezone = "UTC";
            timeZone = DateTimeZone.UTC;
        } else {
            int offsetHours = 0;
            int offsetMinutes = 0;

            if (timezone.startsWith("UTC+")) {
                offsetHours = Integer.parseInt(timezone.substring(4));
            } else if (timezone.startsWith("UTC-")) {
                offsetHours = -Integer.parseInt(timezone.substring(4));
            }

            timeZone = DateTimeZone.forOffsetHoursMinutes(offsetHours, offsetMinutes);
        }

        DateTime dateTime = DateTime.now(timeZone);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTime.toString(formatter);

        request.setAttribute("currentTime", currentTime);
        request.setAttribute("timezone", timezone);
        request.getRequestDispatcher("/WEB-INF/jsp/time.jsp").forward(request, response);
    }
}