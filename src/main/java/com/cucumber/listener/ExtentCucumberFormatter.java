package com.cucumber.listener;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Cucumber custom format listener which generates ExtentsReport html file
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {

    public LinkedList<Step> testSteps;
    private Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;

    private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap() {
        {
            this.put("image/bmp", "bmp");
            this.put("image/gif", "gif");
            this.put("image/jpeg", "jpg");
            this.put("image/png", "png");
            this.put("image/svg+xml", "svg");
            this.put("video/ogg", "ogg");
        }
    };


    public void before(Match match, Result result) {

    }

    public void result(Result result) {
        if ("passed".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.PASS, testSteps.poll().getName(), "PASSED");
        } else if ("failed".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, testSteps.poll().getName(), result.getError());
            //ExtentTestManager.getTest().log(LogStatus.INFO,ExtentTestManager.getTest().addScreenCapture("/Users/saikrisv/Desktop/ActivityScreen.png"));
        } else if ("skipped".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, testSteps.poll().getName(), "SKIPPED");
        } else if ("undefined".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.UNKNOWN, testSteps.poll().getName(), "UNDEFINED");
        }
    }

    public void after(Match match, Result result) {

    }

    public void match(Match match) {

    }

    public void embedding(String s, byte[] bytes) {
 /*       String extension = (String)MIME_TYPES_EXTENSIONS.get(s);
        String fileName = "screenshot-" + System.currentTimeMillis() + "." + extension;
        this.writeBytesAndClose(bytes, this.reportFileOutputStream(fileName));
        ExtentTestManager.getTest().log(LogStatus.INFO, scenarioTest.addScreenCapture(fileName));*/
    }

    public void write(String s) {
        // ExtentTestManager.endTest(parent);
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {

    }

    public void uri(String s) {

    }

    public void feature(Feature feature) {
        for (Tag tag : feature.getTags()) {
            parent = ExtentTestManager.startTest(feature.getName()).assignCategory(Thread.currentThread().getName(),tag.getName());
        }
        parentContext.put(Thread.currentThread().getId(), parent);
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    public void examples(Examples examples) {

    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        this.testSteps = new LinkedList<Step>();
        System.out.println(testSteps);
            child = ExtentTestManager.startTest(scenario.getName()).assignCategory(Thread.currentThread().getName());

    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {
        testSteps.add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        parentContext.get(Thread.currentThread().getId()).appendChild(child);
        ExtentManager.getInstance().flush();
    }

    public void done() {

    }

    public void close() {

    }

    public void eof() {
        ExtentManager.getInstance().endTest(parent);
        ExtentManager.getInstance().flush();
    }


}
