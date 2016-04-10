package com.cucumber.cukesrunner;

import com.cucumber.parallel.CucumberParallelThread;
import org.junit.Test;

/**
 * Created by saikrisv on 30/03/16.
 */
public class RunnerCukes {

    @Test
    public  void testCukesRunner() throws Exception {
        CucumberParallelThread cucumberParallelThread = new CucumberParallelThread();
        cucumberParallelThread.distributeTests(2);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
}
