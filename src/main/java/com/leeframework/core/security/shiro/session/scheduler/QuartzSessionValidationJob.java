package com.leeframework.core.security.shiro.session.scheduler;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * A quartz job that basically just calls the {@link org.apache.shiro.session.mgt.ValidatingSessionManager#validateSessions()} method on a configured
 * session manager. The session manager will automatically be injected by the superclass if it is in the job data map or the scheduler map.
 * <p>
 * 来源：shiro-quartz-1.2.3源码,并改进
 * </p>
 * @author 李志亮 (Lee) <279683131(@qq.com),18538086795@163.com>
 * @date Date:2016年6月9日 Time: 上午12:55:49
 * @version 1.0
 * @since version 1.0
 * @update
 */
public class QuartzSessionValidationJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);

    /**
     * Key used to store the session manager in the job data map for this job.
     */
    static final String SESSION_MANAGER_KEY = "sessionManager";

    /**
     * Called when the job is executed by quartz. This method delegates to the <tt>validateSessions()</tt> method on the associated session manager.
     * @param context the Quartz job execution context for this execution.
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);
        log.info("Executing session validation Quartz job...");
        sessionManager.validateSessions();
        log.info("Session validation Quartz job complete.");
    }
}
