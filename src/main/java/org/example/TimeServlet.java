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

        String timezone = request.getParameter("timezone");
        TimeZone timeZoneInstance;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (timezone == null){
            timezone = "UTC";
            timeZoneInstance = TimeZone.getTimeZone(timezone);
        } else {
            timeZoneInstance = TimeZone.getTimeZone(timezone);
        }

        dateFormat.setTimeZone(timeZoneInstance);
        String currentTime = dateFormat.format(new Date());

        request.setAttribute("currentTime", currentTime);
        request.setAttribute("timezone", timezone);
        request.getRequestDispatcher("/WEB-INF/jsp/time.jsp").forward(request, response);
    }
}