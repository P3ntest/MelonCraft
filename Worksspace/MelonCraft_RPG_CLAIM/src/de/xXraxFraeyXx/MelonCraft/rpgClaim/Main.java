package de.xXraxFraeyXx.MelonCraft.rpgClaim;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.xXraxFraeyXx.MelonCraft.rpgClaim.Map.MapManager;
import de.xXraxFraeyXx.MelonCraft.rpgClaim.Regions.RegionManager;
import de.xXraxFraeyXx.MelonCraft.rpgClaim.Regions.xChunk;

public class Main extends JavaPlugin{
	
	static Main instance;

	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
		instance = this;
		ConfigManager.load();
	}
	
	@Override
	public void onDisable() {
		ConfigManager.save();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("claim")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Chunk c = p.getLocation().getChunk();
				if (RegionManager.getChunkOwner(p.getLocation()) != null) {
					String name = ConfigManager.config.getString("player." + RegionManager.getChunkOwner(p.getLocation()) + ".name");
					p.sendMessage("§cChunk §7| This chunk has alredy been claimed by §c" + name);
				}else {
					if (!MapManager.isInSpawn(p.getLocation()) && p.getWorld() == Bukkit.getWorld("world")) {
						xChunk xc = new xChunk();
						xc.owner = p.getUniqueId().toString();
						xc.team = new ArrayList<String>();
						xc.team.add(p.getUniqueId().toString());
						xc.x = p.getLocation().getChunk().getX();
						xc.y = p.getLocation().getChunk().getZ();
						RegionManager.setChunkAt(xc);
						p.sendMessage("§cChunk §7| You succesfully claimed a chunk!");
					}else {
						p.sendMessage("§cChunk §7| You are not allowed to claim in this region");
					}
					
				}
			}
			
			if (cmd.getName().equalsIgnoreCase("chunkinfo")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.getWorld() != Bukkit.getWorld("world")) {
						p.sendMessage("§cChunk §7| This is the wrong world for chunks!");
						return true;
					}
					
					if (RegionManager.getChunkOwner(p.getLocation()) != null) {
						String name = ConfigManager.config.getString("player." + RegionManager.getChunkOwner(p.getLocation()) + ".name");
						p.sendMessage("§cChunk §7| This chunk is owned by §c" + name);
					}else {
						if (MapManager.isInSpawn(p.getLocation())) {
							p.sendMessage("§cChunk §7| This is the spawn area. Nobody can claim here");
						}
						p.sendMessage("§cChunk §7| This chunk is not claimed");
					}
				}
			}
			return true;
		}
			
		return false;
	}
}