package net.admin4j.ui.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin4j.config.Admin4JConfiguration;
import net.admin4j.deps.commons.lang3.StringUtils;
import net.admin4j.log.LogManager;
import net.admin4j.log.LogManagerRegistry;
import net.admin4j.util.Admin4jRuntimeException;
import net.admin4j.vo.LoggerProductVO;


public class LogLevelServlet extends AdminDisplayServlet 
{
	private static final long serialVersionUID = -2723639589464533505L;
	
	public static final String CHANGE_TYPE_SHOW_MORE = "showMore";
	public static final String CHANGE_TYPE_SHOW_LESS = "showLess";
	public static final String CHANGE_TYPE_CLEAR = "clear";
    public static final String NONE = "None";
    
    public static final String PUBLIC_HANDLE="logLevel";
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		List<LoggerProductVO> logList = new ArrayList<LoggerProductVO>();
		Map<String, LogManager> logManagerMap = new HashMap<String, LogManager>();
		for (LogManager logManager: LogManagerRegistry.getAvailableLogManagerSet()) {
		    logList.add( new LoggerProductVO(logManager.getLoggerSoftwareProductName(), logManager.findLoggers()));
		    logManagerMap.put(logManager.getLoggerSoftwareProductName(), logManager);
		}
		
		String message = performLogLevelChange(request, logManagerMap);
		
		logList = new ArrayList<LoggerProductVO>();
		logManagerMap = new HashMap<String, LogManager>();
		for (LogManager logManager: LogManagerRegistry.getAvailableLogManagerSet()) {
            logList.add( new LoggerProductVO(logManager.getLoggerSoftwareProductName(), logManager.findLoggers()));
            logManagerMap.put(logManager.getLoggerSoftwareProductName(), logManager);
        }
		
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("message", message);
        variableMap.put("loggerList", logList);
        variableMap.put("configuration", new Admin4JConfiguration());
		
        displayFreeMarkerPage(request, response, "logLevelServletDisplay.ftl", variableMap);
	}
	

	private String performLogLevelChange(HttpServletRequest request, Map<String, LogManager> logManagerMap)
	{
		String requestedLoggerType = request.getParameter("loggerType");
		String requestedLoggerName = request.getParameter("loggerName");
		String requestedChangeType = request.getParameter("changeType");

		if (StringUtils.isBlank(requestedLoggerType))
			return "";
		
		LogManager logManager = logManagerMap.get(requestedLoggerType);
		if (logManager == null) {
		    logger.error("Something's wrong -- Log Manager not listed.", 
		            new Admin4jRuntimeException("Something's wrong -- Log MAnager not listed.")
		            .addContextValue("requestedLoggerType", requestedLoggerType));
		    return "Something's wrong -- Log Manager not listed.";
		}
		
		if (CHANGE_TYPE_SHOW_MORE.equals(requestedChangeType)) {
		    try {
                return requestedLoggerName + ":Changed to level: " + logManager.performLogLevelChange(requestedLoggerName, LogManager.LogIncrementType.SHOW_MORE);
            } catch (RuntimeException e) {
                logger.error("Error increasing log level", e);
                return "Error changing log level.  More details in the log.";
            }
		}
		else if (CHANGE_TYPE_SHOW_LESS.equals(requestedChangeType)) {
		    try {
		        return requestedLoggerName + ":Changed to level: " + logManager.performLogLevelChange(requestedLoggerName, LogManager.LogIncrementType.SHOW_LESS);
		    } catch (RuntimeException e) {
                logger.error("Error decreasing log level", e);
                return "Error changing log level.  More details in the log.";
            }
		}
		else if (CHANGE_TYPE_CLEAR.equals(requestedChangeType)) {
		    try {
    		    logManager.performLogLevelChange(requestedLoggerName, LogManager.LogIncrementType.CLEAR);
    		    return requestedLoggerName + ": cleared";
		    } catch (RuntimeException e) {
                logger.error("Error clearing log level", e);
                return "Error changing log level.  More details in the log.";
            }
        }
		else
			return "The loggerType requested was not found: "+requestedLoggerType;
	}
	
	/* (non-Javadoc)
     * @see net.admin4j.ui.servlets.Admin4JServlet#getServletLabel()
     */
    @Override
    public String getServletLabel() {
        return "Log Level Manager";
    }
}
