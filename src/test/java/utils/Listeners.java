package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseTest;

public class Listeners extends BaseTest implements ITestListener {
    
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();

    @Override
    public void onTestStart(ITestResult result) {
        // Test shuru hote hi report mein entry banegi
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable()); // Error message report mein dikhega
        
        try {
            // Screenshot capture karna aur uska path lena
            String filePath = captureScreenshot(result.getMethod().getMethodName());
            // Screenshot ko report mein chipkana (Embed)
            test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Ye line sabse zaroori hai, isse report file generate hoti hai
        extent.flush();
    }
}