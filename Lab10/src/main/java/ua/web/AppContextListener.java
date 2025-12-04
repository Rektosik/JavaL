package ua.web;

import ua.model.Course;
import ua.model.Student;
import ua.repository.CourseRepository;
import ua.repository.StudentRepository;
import ua.util.SerializerService;
import ua.util.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.util.concurrent.CompletableFuture;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Utils.getLogger().info(">>> [LIFECYCLE] Web Application Starting...");

        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();

        sce.getServletContext().setAttribute("studentRepo", studentRepo);
        sce.getServletContext().setAttribute("courseRepo", courseRepo);

        CompletableFuture.runAsync(() -> {
            loadRepoFromJson(studentRepo, "students.json", Student.class);
        });

        CompletableFuture.runAsync(() -> {
            loadRepoFromJson(courseRepo, "courses.json", Course.class);
        });
    }

    private <T extends Comparable<T>> void loadRepoFromJson(ua.repository.GenericRepository<T> repo, String fileName, Class<T> clazz) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                Utils.getLogger().info("Loading " + fileName + "...");
                var list = SerializerService.loadJson(fileName, clazz);
                list.forEach(repo::add);
                Utils.getLogger().info("Loaded " + list.size() + " items into " + fileName);
            } catch (Exception e) {
                Utils.getLogger().severe("Error loading " + fileName + ": " + e.getMessage());
            }
        } else {
            Utils.getLogger().info("File " + fileName + " not found. Starting empty.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Utils.getLogger().info(">>> [LIFECYCLE] Web Application Stopped.");
    }
}