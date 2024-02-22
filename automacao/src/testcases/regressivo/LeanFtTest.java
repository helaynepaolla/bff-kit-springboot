package regressivo;

import org.junit.runner.RunWith;
import com.hpe.alm.octane.*;
import cucumber.api.CucumberOptions;

@RunWith(OctaneCucumber.class)
@CucumberOptions(plugin = {"junit:junitResult.xml"},
        features = "src/testcases/regressivo",
        glue = "regressivo")
public class LeanFtTest {

}
