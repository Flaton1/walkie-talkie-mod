package fr.flaton.walkietalkie.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ModConfig {

    private static final Path CONFIG_FOLDER = FabricLoader.getInstance().getConfigDir();

    public static int maxCanal;
    public static int woodenWalkieTalkieRange;
    public static int stoneWalkieTalkieRange;
    public static int ironWalkieTalkieRange;
    public static int diamondWalkieTalkieRange;
    public static int netheriteWalkieTalkieRange;

    public static void registerModConfig() {
        loadConfig();
    }

    private static void loadConfig() {
        File configFile = new File(CONFIG_FOLDER.toString(), "WalkieTalkie.properties");
        Properties properties = new Properties();

        if (configFile.exists()) {
            try {
                FileInputStream stream = new FileInputStream(configFile);
                properties.load(stream);
                stream.close();

                // Set values from loaded config
                maxCanal = Integer.parseInt(properties.getProperty("max-canal", "16"));
                woodenWalkieTalkieRange = Integer.parseInt(properties.getProperty("wooden-walkie-talkie-range", "128"));
                stoneWalkieTalkieRange = Integer.parseInt(properties.getProperty("stone-walkie-talkie-range", "256"));
                ironWalkieTalkieRange = Integer.parseInt(properties.getProperty("iron-walkie-talkie-range", "512"));
                diamondWalkieTalkieRange = Integer.parseInt(properties.getProperty("diamond-walkie-talkie-range", "1024"));
                netheriteWalkieTalkieRange = Integer.parseInt(properties.getProperty("netherite-walkie-talkie-range", "2048"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create default config file
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        File configFile = new File(CONFIG_FOLDER.toString(), "WalkieTalkie.properties");
        Map<String, String> config = new LinkedHashMap<>();

        config.put("# Walkie-Talkie Config File", "");
        config.put("\n# Channel settings", "");
        config.put("max-canal", "16");
        config.put("\n# Walkie-Talkie settings", "");
        config.put("wooden-walkie-talkie-range", "128");
        config.put("stone-walkie-talkie-range", "256");
        config.put("iron-walkie-talkie-range", "512");
        config.put("diamond-walkie-talkie-range", "1024");
        config.put("netherite-walkie-talkie-range", "2048");

        try {
            FileOutputStream output = new FileOutputStream(configFile);

            for (Map.Entry<String, String> entry : config.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String line;
                if (value.matches("")) {
                    line = String.format("%s%s\n", key, value);
                } else {
                    line = String.format("%s=%s\n", key, value);
                }
                output.write(line.getBytes());
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
