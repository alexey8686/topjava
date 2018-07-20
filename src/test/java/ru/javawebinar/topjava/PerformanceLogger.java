package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceLogger implements TestRule {

    private static final Logger log = LoggerFactory.getLogger(PerformanceLogger.class);

    @Override
    public Statement apply(Statement base, Description description) {


        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long start = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    log.info("----------------------------------------------------------------------------");
                    log.info("Time taken for " + description.getDisplayName()
                            + ": " + (System.currentTimeMillis() - start) + " milli sec");
                    log.info("----------------------------------------------------------------------------");
                }
            }
        };
    }
}