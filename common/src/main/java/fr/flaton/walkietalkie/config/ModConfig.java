package fr.flaton.walkietalkie.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ModConfig {

    private final File CONFIG_FILE;

    public static int maxCanal = 16;
    public static int speakerDistance = 32;
    public static int woodenWalkieTalkieRange = 128;
    public static int stoneWalkieTalkieRange = 256;
    public static int ironWalkieTalkieRange = 512;
    public static int diamondWalkieTalkieRange = 1024;
    public static int netheriteWalkieTalkieRange = 2048;

    public ModConfig(Path ConfigFolder) {
        this.CONFIG_FILE = new File(ConfigFolder.toString(), "WalkieTalkie.properties");
    }

    public void loadModConfig() {
        Properties properties = new Properties();

        if (CONFIG_FILE.exists()) {
            try {
                FileInputStream stream = new FileInputStream(CONFIG_FILE);
                properties.load(stream);
                stream.close();

                // Set values from loaded config
                maxCanal = Integer.parseInt(properties.getProperty("max-canal", "16"));
                speakerDistance = Integer.parseInt(properties.getProperty("speaker-distance", "32"));
                woodenWalkieTalkieRange = Integer.parseInt(properties.getProperty("wooden-walkie-talkie-range", "128"));
                stoneWalkieTalkieRange = Integer.parseInt(properties.getProperty("stone-walkie-talkie-range", "256"));
                ironWalkieTalkieRange = Integer.parseInt(properties.getProperty("iron-walkie-talkie-range", "512"));
                diamondWalkieTalkieRange = Integer.parseInt(properties.getProperty("diamond-walkie-talkie-range", "1024"));
                netheriteWalkieTalkieRange = Integer.parseInt(properties.getProperty("netherite-walkie-talkie-range", "2048"));

                createConfig(mapConfig());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Create default config file
            createConfig(mapConfig());
        }
    }

    private static Map<String, String> mapConfig() {
        Map<String, String> config = new LinkedHashMap<>();

        config.put("# Walkie-Talkie Config File", "");
        config.put("\n# Channel settings", "");
        config.put("max-canal", String.valueOf(maxCanal));
        config.put("\n# Speaker setting", "");
        config.put("speaker-distance", String.valueOf(speakerDistance));
        config.put("\n# Walkie-Talkie settings", "");
        config.put("wooden-walkie-talkie-range", String.valueOf(woodenWalkieTalkieRange));
        config.put("stone-walkie-talkie-range", String.valueOf(stoneWalkieTalkieRange));
        config.put("iron-walkie-talkie-range", String.valueOf(ironWalkieTalkieRange));
        config.put("diamond-walkie-talkie-range", String.valueOf(diamondWalkieTalkieRange));
        config.put("netherite-walkie-talkie-range", String.valueOf(netheriteWalkieTalkieRange));

        return config;
    }

    private void createConfig(Map<String, String> config) {
        try {
            FileOutputStream output = new FileOutputStream(CONFIG_FILE);

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
