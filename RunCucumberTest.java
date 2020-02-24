package cucumber.InitialProject;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/Features/",
		glue = {"cucumber.InitialProject"},
		plugin = { "pretty", "html:target/cucumber-reports"},
		dryRun=false
		)
public class RunCucumberTest {
}