package config;

/**
 * Created by yannis on 29/01/17.
 */
public final class ConfigManager {

    private static Config config = null;

    public final static void setConfig(Config config) {
        ConfigManager.config = config;
    }

    public final static Config getConfig() {
        if(ConfigManager.config == null) {
            ConfigManager.config = new ProductionConfig();
        }
        return ConfigManager.config;
    }
}
