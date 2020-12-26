package com.springcloud.client.excel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * The <code>ExcelLogs</code>
 *
 * @author
 * @date
 */
public class ExcelLogs {
    private Boolean hasError;
    private List<ExcelLog> logList;

    /**
     * 
     */
    public ExcelLogs() {
        super();
        hasError = false;
    }

    /**
     * @return the hasError
     */
    public Boolean getHasError() {
        return hasError;
    }

    /**
     * @param hasError
     *            the hasError to set
     */
    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the logList
     */
    public List<ExcelLog> getLogList() {
        return logList;
    }

    public List<ExcelLog> getErrorLogList() {
        List<ExcelLog> errList = new ArrayList<>();
        for (ExcelLog log : this.logList) {
            if (log != null && StringUtils.isNotBlank(log.getLog())) {
                errList.add(log);
            }
        }
        return errList;
    }

    /**
     * @param logList
     *            the logList to set
     */
    public void setLogList(List<ExcelLog> logList) {
        this.logList = logList;
    }

    /**
     *
     * @return
     */
    public List<ExcelLog> getSuccessLogList() {
        List<ExcelLog> succList = new ArrayList<>();
        for (ExcelLog log : this.logList) {
            if (log == null || StringUtils.isBlank(log.getLog())) {
                succList.add(log);
            }
        }
        return succList;
    }

}
