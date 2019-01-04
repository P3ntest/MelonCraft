package de.xXraxFraeyXx.MelonCraft.SnowBallFFA.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Spawns {

	public static List<Location> spawns = new ArrayList<Location>();
	
	static List<Location> used = new ArrayList<Location>();
	
	public static String world = "world";
	
	static {
		
		spawns.add(new Location(Bukkit.getWorld(world), 7.5, 106, 71.5));
		spawns.add(new Location(Bukkit.getWorld(world), -6.5, 106, 70.5));
		spawns.add(new Location(Bukkit.getWorld(world), 2.5, 106, 60.5));
		spawns.add(new Location(Bukkit.getWorld(world), 7.5, 106, 71.5));
		spawns.add(new Location(Bukkit.getWorld(world), 8.5, 106, 54.5));
		spawns.add(new Location(Bukkit.getWorld(world), -7.5, 106, 47.5));
		spawns.add(new Location(Bukkit.getWorld(world), 7.5, 106, 38.5));
		spawns.add(new Location(Bukkit.getWorld(world), 7.5, 106, 21.5));
		spawns.add(new Location(Bukkit.getWorld(world), -5.5, 106, 23.5));
		
		used = spawns;
	}
	
	public static Location getRandom() {
		Random r = new Random();
		return spawns.get(r.nextInt(8));
	}
	
}
