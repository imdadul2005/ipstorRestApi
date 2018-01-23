package com.Freestor.RestAPI;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class UseListener implements IInvokedMethodListener {

    public static final Logger logger = LogManager.getLogger(UseListener.class.getName());

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && ITestResult.FAILURE == testResult.getStatus()) {
            Throwable throwable = testResult.getThrowable();
            String originalMessage = throwable.getMessage();
            String newMessage = originalMessage + "\n...Error please check the log for detail request and respond...";
            try {
                FieldUtils.writeField(throwable, "detailMessage", newMessage, true);
                logger.error(newMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
