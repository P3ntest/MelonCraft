package de.xXraxFraeyXx.MelonCraft.rpgClaim.Regions;

import org.bukkit.Location;

import de.xXraxFraeyXx.MelonCraft.rpgClaim.ConfigManager;

public class RegionManager {

	public static String getChunkOwner(Location l) {
		if (ConfigManager.config.contains("chunk." + l.getBlock().getChunk().getX() + "|" + l.getBlock().getChunk().getZ())) {
			return ConfigManager.config.getString("chunk." + l.getBlock().getChunk().getX() + "|" + l.getBlock().getChunk().getZ() + ".owner");
		}else {
			return null;
		}
	}
	
	public static xChunk getChunkAt(Location l) {
		String prefix = "chunk." + l.getBlock().getChunk().getX() + "|" + l.getBlock().getChunk().getZ();
		xChunk xc = new xChunk();
		xc.owner = ConfigManager.config.getString(prefix + ".owner");
		xc.team = ConfigManager.config.getStringList(prefix + ".team");
		xc.x = l.getBlock().getChunk().getX();
		xc.y = l.getBlock().getChunk().getZ();
		return xc;
	}
	
	public static void setChunkAt(xChunk xc) {
		String prefix = "chunk." + xc.x + "|" + xc.y;
		ConfigManager.config.set(prefix + ".owner", xc.owner);
		ConfigManager.config.set(prefix + ".team", xc.team);
		ConfigManager.config.set(prefix + ".x", xc.x);
		ConfigManager.config.set(prefix + ".z", xc.y);
	}
	
}
