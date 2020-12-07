package edu.epam.fadeev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class AirportTestListener extends TestListenerAdapter {
    static Logger Logger = LogManager.getLogger();

    @Override
    public void onStart(ITestContext iTestContext) {
        Logger.info("Test Started...." + iTestContext.getName() + " " + iTestContext.getStartDate());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Logger.info("Test finished...." + iTestContext.getEndDate());
    }

    @Override
    public void onTestStart(ITestResult testResult) {
        Logger.info("test [" + testResult.getName() + "] start");
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        Logger.info("test [" + testResult.getName() + "] Success");
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        Logger.error(testResult.getStatus() + "Test [" + testResult.getName() + "] failed");
        Logger.error(testResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        Logger.warn(testResult.getStatus() + "test [" + testResult.getName() + "] skipped");
    }
}
