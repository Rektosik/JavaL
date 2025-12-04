package ua.web;

import ua.model.Course;
import ua.repository.CourseRepository;
import ua.util.JsonUtil;
import ua.util.SerializerService;
import ua.util.Utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@WebServlet("/api/courses/*")
public class CourseServlet extends HttpServlet {

    private CourseRepository courseRepo;
    private final String FILE_NAME = "courses.json";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        courseRepo = (CourseRepository) config.getServletContext().getAttribute("courseRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonUtil.writeJson(resp, courseRepo.getAll());
        Utils.getLogger().info("GET all courses");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            Course c = JsonUtil.parseJson(body, Course.class);
            courseRepo.add(c);

            CompletableFuture.runAsync(() -> SerializerService.saveToJson(FILE_NAME, courseRepo.getAll()));

            resp.setStatus(201);
            JsonUtil.writeJson(resp, c);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}