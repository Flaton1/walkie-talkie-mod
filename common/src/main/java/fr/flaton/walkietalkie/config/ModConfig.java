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
    public static boolean voiceDuplication = false;
    public static int woodenWalkieTalkieRange = 128;
    public static int stoneWalkieTalkieRange = 256;
    public static int ironWalkieTalkieRange = 512;
    public static int goldenWalkieTalkieRange = 1024;
    public static int diamondWalkieTalkieRange = 2048;
    public static int netheriteWalkieTalkieRange = 4096;
    public static boolean crossDimensionsEnabled = false;
    public static boolean applyDimensionScale = true;
    public static boolean applyRadioEffect = true;

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
                voiceDuplication = Boolean.parseBoolean(properties.getProperty("voice-duplication", "false"));
                woodenWalkieTalkieRange = Integer.parseInt(properties.getProperty("wooden-walkie-talkie-range", "128"));
                stoneWalkieTalkieRange = Integer.parseInt(properties.getProperty("stone-walkie-talkie-range", "256"));
                ironWalkieTalkieRange = Integer.parseInt(properties.getProperty("iron-walkie-talkie-range", "512"));
                goldenWalkieTalkieRange = Integer.parseInt(properties.getProperty("golden-walkie-talkie-range", "1024"));
                diamondWalkieTalkieRange = Integer.parseInt(properties.getProperty("diamond-walkie-talkie-range", "2048"));
                netheriteWalkieTalkieRange = Integer.parseInt(properties.getProperty("netherite-walkie-talkie-range", "4096"));
                crossDimensionsEnabled = Boolean.parseBoolean(properties.getProperty("cross-dimensions-enabled", "false"));
                applyDimensionScale = Boolean.parseBoolean(properties.getProperty("apply-dimension-scale", "true"));
                applyRadioEffect = Boolean.parseBoolean(properties.getProperty("apply-radio-effect", "true"));

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
        config.put("voice-duplication", String.valueOf(voiceDuplication));
        config.put("\n# Walkie-Talkie settings", "");
        config.put("wooden-walkie-talkie-range", String.valueOf(woodenWalkieTalkieRange));
        config.put("stone-walkie-talkie-range", String.valueOf(stoneWalkieTalkieRange));
        config.put("iron-walkie-talkie-range", String.valueOf(ironWalkieTalkieRange));
        config.put("golden-walkie-talkie-range", String.valueOf(goldenWalkieTalkieRange));
        config.put("diamond-walkie-talkie-range", String.valueOf(diamondWalkieTalkieRange));
        config.put("netherite-walkie-talkie-range", String.valueOf(netheriteWalkieTalkieRange));
        config.put("\n# Cross dimensions settings", "");
        config.put("cross-dimensions-enabled", String.valueOf(crossDimensionsEnabled));
        config.put("apply-dimension-scale", String.valueOf(applyDimensionScale));
        config.put("\n# Misc settings", "");
        config.put("apply-radio-effect", String.valueOf(applyRadioEffect));

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
