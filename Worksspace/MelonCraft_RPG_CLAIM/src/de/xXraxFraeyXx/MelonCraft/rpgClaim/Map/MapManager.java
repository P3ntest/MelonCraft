package de.xXraxFraeyXx.MelonCraft.rpgClaim.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;

public class MapManager {

	public static Location getNoGroupSpawn() {
		if (Bukkit.getWorld("world") == null) {
			WorldCreator wc = new WorldCreator("world");
			Bukkit.createWorld(wc);
		}
		
		return new Location(Bukkit.getWorld("world"), 402.5f, 39f, -147.5f, -8.8f, -3.1f);
	}
	
	public static Location getNormalSpawn() {
		if (Bukkit.getWorld("world") == null) {
			WorldCreator wc = new WorldCreator("world");
			Bukkit.createWorld(wc);
		}
		
		return new Location(Bukkit.getWorld("world"), 453.5f, 68f, -156.5f, -165.7f, -3.8f);
	}
	
	
	public static boolean isInSpawn(Location l) {
		//x: 250 - 700
		//z: -400 - 100

		if (l.getWorld() != Bukkit.getWorld("world")) {
			return false;
		}
		if ((l.getBlockX() >= 250) && (l.getBlockX() <= 700)) {
		
			if ((l.getBlockZ() >= -400) && (l.getBlockZ() <= 100)) {
			
				return true;
			}
		}
		return false;
	}
	
}
