package servlets;

import com.google.actions.api.App;
import intents.IntentsHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@WebServlet(name = "SimpleServlet", urlPatterns = {"/"})
public class DialogflowServlet extends HttpServlet {
    private App app = new IntentsHandler();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String requestBody = req.getReader().lines().collect(Collectors.joining());
        final Map<String, String> headersMap = Collections.list(req.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(name -> name, req::getHeader));
        try {
            final String responseBody = app.handleRequest(requestBody, headersMap).get();
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write(responseBody);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
