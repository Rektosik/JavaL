package ua.web;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.JsonUtil;
import ua.util.SerializerService;
import ua.util.Utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;

@WebServlet("/api/students/*")
public class StudentServlet extends HttpServlet {

    private StudentRepository studentRepo;
    private final String FILE_NAME = "students.json";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        studentRepo = (StudentRepository) config.getServletContext().getAttribute("studentRepo");
        Utils.getLogger().info(">>> [LIFECYCLE] StudentServlet initialized.");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Utils.getLogger().info(">>> [LIFECYCLE] Service method called: " + request.getMethod() + " " + request.getRequestURI());
        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                JsonUtil.writeJson(resp, studentRepo.getAll());
            } else {
                String email = pathInfo.substring(1);
                Student student = studentRepo.findByIdentity(email);
                if (student != null) JsonUtil.writeJson(resp, student);
                else resp.sendError(404, "Student not found");
            }
        } catch (Exception e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            Student s = JsonUtil.parseJson(body, Student.class);

            studentRepo.add(s);
            saveChangesAsync();

            resp.setStatus(201);
            JsonUtil.writeJson(resp, s);
            Utils.getLogger().info("Created student: " + s.email());
        } catch (Exception e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            Student s = JsonUtil.parseJson(body, Student.class);

            studentRepo.add(s);
            saveChangesAsync();

            JsonUtil.writeJson(resp, s);
            Utils.getLogger().info("Updated student: " + s.email());
        } catch (Exception e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() < 2) {
            resp.sendError(400, "Id required");
            return;
        }
        try {
            String email = pathInfo.substring(1);
            if (studentRepo.removeByIdentity(email)) {
                saveChangesAsync();
                resp.setStatus(204);
                Utils.getLogger().info("Deleted student: " + email);
            } else {
                resp.sendError(404, "Not found");
            }
        } catch (Exception e) {
            handleError(resp, e);
        }
    }

    private void saveChangesAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                SerializerService.saveToJson(FILE_NAME, studentRepo.getAll());
                Utils.getLogger().info("Changes saved to " + FILE_NAME);
            } catch (Exception e) {
                Utils.getLogger().severe("Failed to save changes: " + e.getMessage());
            }
        });
    }

    private void handleError(HttpServletResponse resp, Exception e) throws IOException {
        Utils.getLogger().log(Level.SEVERE, "Error", e);
        resp.sendError(500, e.getMessage());
    }

    @Override
    public void destroy() {
        Utils.getLogger().info(">>> [LIFECYCLE] StudentServlet destroyed.");
    }
}