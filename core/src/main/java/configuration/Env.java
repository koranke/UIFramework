package configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
	"system:env",
	"classpath:env.properties",
})
public interface Env extends Config {
	@Key("env.id")
	TargetEnvironment envId();
}
