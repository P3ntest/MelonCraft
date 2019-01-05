package de.xXraxFraeyXx.MelonCraft.rpgClaim;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class ConfigManager {

	static File file = new File(Main.instance.getDataFolder(), "config.yml");
	
	public static YamlConfiguration config = new YamlConfiguration();
	
	public static void load() {
		Main.instance.getDataFolder().mkdir();
		try {
			config.load(file);
		}catch (Exception e) { 
			System.out.println("CONFIGURATION FILE NOT LOADED!");
		}
	}
	
	public static void save() {
		try {
			config.save(file);
		}catch (Exception e) { 
			System.out.println("CONFIGURATION FILE NOT SAVED!");
		}
	}
	

	
	
}
