package ru.otus.servlet;

import jakarta.servlet.http.*;
import ru.otus.services.*;

import java.io.IOException;

public class UsersServlet extends HttpServlet {
    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, null));
    }
}
