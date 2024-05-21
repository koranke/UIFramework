package configuration;

import enums.TargetBrowser;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
	"system:env",
	"classpath:common.properties",
	"classpath:${env.id}.properties"
})
public interface Props extends Config {

	@Key("env.id")
	TargetEnvironment envId();

	@Key("ui.slowTime")
	@DefaultValue("0")
	Integer slowTime();

	@Key("ui.browser")
	@DefaultValue("EDGE")
	TargetBrowser targetBrowser();

	@Key("ui.implicitWait")
	@DefaultValue("0")
	Integer implicitWait();

	@Key("ui.visibilityWaitInSeconds")
	@DefaultValue("0")
	Integer visibilityWaitInSeconds();

	@Key("ui.headless")
	@DefaultValue("false")
	Boolean headless();

	@Key("ui.visualTest.maxDiffPixels")
	@DefaultValue("0")
	Integer maxDiffPixels();

	@Key("ui.useBiDi")
	@DefaultValue("false")
	Boolean useBiDi();
}
