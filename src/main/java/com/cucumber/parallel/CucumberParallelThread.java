package com.cucumber.parallel;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by saikrisv on 09/03/16.
 */
public class CucumberParallelThread {

    List<Class> testCases = new ArrayList();



    public void distributeTests(int deviceCount) throws Exception {
        PackageUtil.getClasses("output").stream().forEach(s -> {
            if (s.toString().contains("IT")) {
                System.out.println("forEach: " + testCases.add((Class) s));
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);
        for (final Class testCase : testCases) {
            executorService.submit(new Runnable() {
                public void run() {
                    System.out.println("Running test file: " + testCase + Thread.currentThread().getId());
                    runTestCase(testCase);

                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runTestCase(Class testCase) {
        Result result = JUnitCore.runClasses(testCase);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

}
