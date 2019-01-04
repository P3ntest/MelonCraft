package de.rasflor.meloncraft.snowBallFfa.main;

import com.connorlinfoot.titleapi.TitleAPI;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

public class SnowBallFfa extends JavaPlugin implements Listener {

    public static String prefix = "§bSnowballFFA §7| ";

    public File file = new File(getDataFolder(), "config.yml");
    public YamlConfiguration config = new YamlConfiguration();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.isSneaking()) {
                        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL));
                    }
                }
            }
        }, 20L, 20L); //0 Tick initial delay, 20 Tick (1 Second) between repeats

        try {
            config.load(file);
        } catch (Exception e) {
            Bukkit.broadcastMessage("FILE NOT LOADED");
        }

    }

    @EventHandler
    public void onJoin (PlayerJoinEvent e) {
        e.setJoinMessage(prefix + e.getPlayer().getName() + " joined the game.");

        e.getPlayer().getInventory().clear();

        e.getPlayer().teleport(Spawns.getRandom());

        e.getPlayer().setHealth(2);
        e.getPlayer().setMaxHealth(2);
        e.getPlayer().setFireTicks(0);
        e.getPlayer().setExp(0);
        e.getPlayer().setLevel(0);
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().setFoodLevel(20);

        TitleAPI.sendFullTitle(e.getPlayer(), 0, 80, 20, "§7Snowball Fight!", "§eHold Sneak to get Snowballs");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(prefix + "The player " + e.getPlayer().getName() + " left the game.");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {

        e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(3).setY(2));

        if (e.getDamager() instanceof Snowball) {
            Snowball b = (Snowball) e.getDamager();
            Player shooter = (Player) b.getShooter();
            shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 10, 1);
            e.setDamage(10);
        }

        if (e.getDamager() instanceof Player) {
            e.setDamage(7);
            Player pp = (Player) e.getDamager();
            pp.playSound(pp.getLocation(), Sound.ORB_PICKUP, 10, 1);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {

        Player dead = event.getPlayer();
        dead.setMaxHealth(2);
        dead.setHealth(2);
        dead.spigot().respawn();

        event.setRespawnLocation(Spawns.getRandom());

        dead.setMaxHealth(2);
        dead.setHealth(2);

        dead.setMaxHealth(2);
        dead.setFireTicks(0);
        dead.setExp(0);
        dead.setLevel(0);
        dead.setGameMode(GameMode.ADVENTURE);
        dead.setFoodLevel(20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    dead.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 250));
                }
            }
        },10L); //0 Tick initial delay, 20 Tick (1 Second) between repeats

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);

        e.setDroppedExp(0);
        e.setKeepInventory(true);
        e.getEntity().getInventory().clear();

        if (e.getEntity().getKiller() != null) {
            if(e.getEntity() != e.getEntity().getKiller()) {

                Player dead = e.getEntity();
                Player killer = dead.getKiller();

                Bukkit.broadcastMessage(prefix + dead.getName() + " was hit by " + killer.getName());
                //ConfigManager.addCoins(killer.getUniqueId().toString(), 5);
                killer.sendMessage(prefix + "You killed " + dead.getName() + " and got §6§l5 Melons§r§3.");
                killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10, 1);
                killer.setLevel(killer.getLevel() + 1);
                if (killer.getMaxHealth() <= 40) {
                    killer.setMaxHealth(killer.getMaxHealth() + 2);
                }
                killer.setHealth(killer.getHealth() + 4);

                dead.spigot().respawn();
                ((CraftPlayer) dead).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));


            } else {

                Player dead = e.getEntity();

                Bukkit.broadcastMessage(prefix + dead.getName() + " is stupid");
                //ConfigManager.addCoins(killer.getUniqueId().toString(), -20);
                dead.setLevel(0);

            }
        }else {
            Bukkit.broadcastMessage(prefix + e.getEntity().getName() + " died.");
        }
    }

    @EventHandler
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event) {

        event.setCancelled(true);

    }


    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {

        event.setCancelled(true);
        event.getWorld().setStorm(false);

    }

}
