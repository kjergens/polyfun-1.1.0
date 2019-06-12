import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ParameterizedPolynomialTest.class,
        ParameterizedAddPolynomials.class,
        ParameterizedAddTangents.class,
        ParameterizedEvaluateScalar.class,
        ParameterizedEvaluateCoefs.class,
        ParameterizedMultiplyPolynomials.class,
        ParameterizedSubtractPolynomials.class,
        ParameterizedTo.class,
        ParameterizedOf.class
})


public class ParameterizedTestSuite {
}
