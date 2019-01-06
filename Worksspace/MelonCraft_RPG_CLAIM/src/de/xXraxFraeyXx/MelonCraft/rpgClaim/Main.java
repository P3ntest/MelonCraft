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
				if (args.length == 0) {
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
				}else if (args[0].equalsIgnoreCase("add")) {
					if (args.length >= 2) {
						if (RegionManager.getChunkAt(p.getLocation()).owner.equalsIgnoreCase(p.getUniqueId().toString())) {
							xChunk xc = RegionManager.getChunkAt(p.getLocation());
							if (Bukkit.getPlayer(args[1]) == null) {
								sender.sendMessage("§cChunk §7| That player is not online!");
								return true;
							}
							Player add = Bukkit.getPlayer(args[1]);
							if (xc.team.contains(add.getUniqueId().toString())) {
								sender.sendMessage("§cChunk §7| That player is already in your team!");
								return true;
							}
							xc.team.add(add.getUniqueId().toString());
							add.sendMessage("§cChunk §7| You have been added to " + p.getName() + "'s Chunk!");
							p.sendMessage("§cChunk §7| You added " + add.getName() + " to your Chunk-Team");
							RegionManager.setChunkAt(xc);
						}else {
							sender.sendMessage("§cChunk §7| This is not your chunk!");
							return true;
						}
					}else {
						sender.sendMessage("§cChunk §7| Please select a player to add!");
					}
				}else if (args[0].equalsIgnoreCase("remove")) {
					if (args.length >= 2) {
						if (RegionManager.getChunkOwner(p.getLocation()).equalsIgnoreCase(p.getUniqueId().toString())) {
							xChunk xc = RegionManager.getChunkAt(p.getLocation());
							if (Bukkit.getPlayer(args[1]) == null) {
								sender.sendMessage("§cChunk §7| That player is not online!");
								return true;
							}
							Player add = Bukkit.getPlayer(args[1]);
							if (add.getUniqueId().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
								p.sendMessage("§cChunk §7| You can't remove yourself!");
							}
							if (!xc.team.contains(add.getUniqueId().toString())) {
								sender.sendMessage("§cChunk §7| That player is not in your team!");
								return true;
							}
							xc.team.remove(add.getUniqueId().toString());
							p.sendMessage("§cChunk §7| You removed" + add.getName() + " from your Chunk-Team");
						}else {
							sender.sendMessage("§cChunk §7| This is not your chunk!");
						}
					}else {
						sender.sendMessage("§cChunk §7| Please select a player to remove");
					}
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
					xChunk xc = RegionManager.getChunkAt(p.getLocation());
					p.sendMessage("§cChunk §7| This chunk is owned by §c" + name);
					p.sendMessage("§cChunk §7| Team:");
					for (String s : xc.team) {
						p.sendMessage("§7  - " + ConfigManager.config.getString("player." + s + ".name"));
					}
					return true;
				}else {
					if (MapManager.isInSpawn(p.getLocation())) {
						p.sendMessage("§cChunk §7| This is the spawn area. Nobody can claim here");
						return true;
					}else {
						p.sendMessage("§cChunk §7| This chunk is not claimed");
						return true;
					}
					
				}
			}
		}
		return false;
	}
}