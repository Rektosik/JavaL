package ua.util;

import java.io.IOException;
import java.util.logging.*;

public final class LoggerUtil {
    private static Logger logger;
    private LoggerUtil() {}
    public static Logger getLogger() {
        if (logger != null) return logger;
        logger = Logger.getLogger("SimpleApp");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());
        logger.addHandler(ch);
        try {
            FileHandler fh = new FileHandler("app.log", true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.WARNING, "No file logger: " + e.getMessage());
        }
        return logger;
    }
}
