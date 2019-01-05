package de.xXraxFraeyXx.MelonCraft.rpgClaim;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xXraxFraeyXx.MelonCraft.rpgClaim.Map.MapManager;
import de.xXraxFraeyXx.MelonCraft.rpgClaim.Utils.IsAllowed;

public class EventListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ConfigManager.config.set("player." + e.getPlayer().getUniqueId().toString() + ".name", e.getPlayer().getName());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof ArmorStand || e.getEntity() instanceof ItemFrame) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (MapManager.isInSpawn(e.getLocation())) {
			e.setCancelled(true);
		}
	}
	
//	@EventHandler
//	public void onDamageByEntity(EntityDamageByEntityEvent e) {
//		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
//			e.setCancelled(true);
//		}
//	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
		if (!IsAllowed.is(e.getPlayer(), e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent e) {
		if (e.getBlockPlaced().getType() == Material.TNT) {
			e.setCancelled(true);
		}
		
		if (!IsAllowed.is(e.getPlayer(), e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteractAt(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked() instanceof ArmorStand || e.getRightClicked() instanceof ItemFrame) {
			if (!IsAllowed.is(e.getPlayer(), e.getRightClicked().getLocation())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem().getType() == Material.FLINT_AND_STEEL) {
				e.setCancelled(true);
			}
		}
	}
}
