package tech.brtrndb.airproject.flightplan.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Slf4j
public class LoggerListener implements TestExecutionListener {

    private static final int SEPARATOR_LENGTH = 100;
    private static final String SEPARATOR_CLASS = "=".repeat(SEPARATOR_LENGTH);
    private static final String SEPARATOR_TEST = "-".repeat(SEPARATOR_LENGTH);

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        log.info(SEPARATOR_CLASS);
        log.info("[{}]: STARTING ALL TESTS.", testContext.getTestClass().getSimpleName());
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        log.info(SEPARATOR_TEST);
        log.info("[{}]: Preparing test.", testContext.getTestMethod().getName());
    }

    @Override
    public void beforeTestExecution(TestContext testContext) throws Exception {
        log.info("[{}]: Start.", testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {
        log.info("[{}]: Finished.", testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        log.info("[{}]: Cleaning.", testContext.getTestMethod().getName());
        log.info(SEPARATOR_TEST);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        log.info("[{}]: ALL TESTS DONE.", testContext.getTestClass().getSimpleName());
        log.info(SEPARATOR_CLASS);
    }

}
