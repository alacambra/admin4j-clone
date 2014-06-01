package net.admin4j.vo;

import java.io.Serializable;

public class LoggerVO extends BaseVO implements Serializable, Comparable<LoggerVO> 
{
	private static final long serialVersionUID = -8576322840966371121L;

	private String loggerName;
	private String level;
	private String type;
	
	public LoggerVO(String loggerName, String level, String type) {
		this.loggerName = loggerName;
		this.level = level;
		this.type = type;
	}
	
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(LoggerVO other) {
		if (other == null)
			return -1;
		if (other.getLoggerName() == null)
			return -1;
		if (this.getLoggerName() == null)
			return 1;
		return this.getLoggerName().compareTo(other.getLoggerName());
	}

    /* (non-Javadoc)
     * @see net.admin4j.vo.BaseVO#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new LoggerVO(this.loggerName, this.level, this.type);
    }
}
