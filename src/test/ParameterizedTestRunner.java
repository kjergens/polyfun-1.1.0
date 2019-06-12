import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ParameterizedTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ParameterizedTestSuite.class);

        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString() + "\n");
        }

        System.out.println(result.getFailureCount() + " failed out of " + result.getRunCount());
    }
}
