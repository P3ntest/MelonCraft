package de.xXraxFraeyXx.MelonCraft.rpgClaim.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.xXraxFraeyXx.MelonCraft.rpgClaim.Map.MapManager;
import de.xXraxFraeyXx.MelonCraft.rpgClaim.Regions.RegionManager;
import de.xXraxFraeyXx.MelonCraft.rpgClaim.Regions.xChunk;

public class IsAllowed {

	public static boolean is(Player p, Location l) {
		if (MapManager.isInSpawn(l)) {
			return false;
		}
		if (l.getWorld() == Bukkit.getWorld("world")) {
			if (RegionManager.getChunkOwner(l) != null) {
				xChunk xc = RegionManager.getChunkAt(l);
				if (!xc.team.contains(p.getUniqueId().toString())) {
					return false;
				}
			}else {
				return false;
			}
		}
		return true;
	
	}
}
