package ua.company.controller.command;

import org.apache.log4j.Logger;
import ua.company.controller.config.ConfigManager;
import ua.company.service.logger.MyLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * StartCreateTestCommand.java -
 *
 * @author Ruslan Omelchenko
 * @version 1.0 16.01.2018
 */
public class StartCreateTestCommand implements ICommand {
    private static final Logger LOGGER = MyLogger.getLOGGER(StartCreateTestCommand.class);
    private String page;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("StartCreateTest command.");
        page = ConfigManager.getInstance().getProperty(ConfigManager.getCONSTRUCTOR());
        return page;
    }
}
