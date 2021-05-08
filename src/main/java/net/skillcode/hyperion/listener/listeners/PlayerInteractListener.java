package net.skillcode.hyperion.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.skillcode.hyperion.config.configs.MainConfig;
import net.skillcode.hyperion.utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class PlayerInteractListener implements Listener {

    private final List<String> teleportList = new CopyOnWriteArrayList<>();
    private final List<String> abilityDelay = new CopyOnWriteArrayList<>();
    private final List<String> witherShieldDelay = new CopyOnWriteArrayList<>();

    @Inject
    private JavaPlugin plugin;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private ItemUtils itemUtils;

    @EventHandler
    public void onInteract(final @NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getItemInHand();

        if (itemUtils.isValidItem(itemStack)
                && itemStack.getItemMeta().getDisplayName().equals(mainConfig.getString(MainConfig.ITEM_NAME))
                && (event.getAction().equals(Action.RIGHT_CLICK_AIR)
                || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {

            if (!abilityDelay.contains(player.getName())
            && !teleportList.contains(player.getName())) {

                abilityDelay.add(player.getName());
                teleportList.add(player.getName());

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> abilityDelay.remove(player.getName()),
                        mainConfig.getInt(MainConfig.ABILITY_DELAY));

                final HashSet hashSet = new HashSet<Material>();
                hashSet.add(Material.AIR);


                final Block block = player.getTargetBlock((Set<Material>) hashSet, mainConfig.getInt(MainConfig.TELEPORT_WIDE));

                final Location playerLocation = player.getLocation();
                final Location teleportLocation = new Location(block.getWorld(), block.getX(),
                        block.getY(), block.getZ(), playerLocation.getYaw(), playerLocation.getPitch());

                if (teleportLocation.getBlock().getType() != Material.AIR) {
                    teleportLocation.setY(teleportLocation.getY() + 1);
                }

                if (new Location(teleportLocation.getWorld(), teleportLocation.getX(), teleportLocation.getY() + 1,
                        teleportLocation.getZ()).getBlock().getType() == Material.AIR
                        && player.getLocation().add(player.getLocation().getDirection()).getBlock().getType() == Material.AIR) {
                    teleportLocation.setX(teleportLocation.getX() - 0.5D);
                    teleportLocation.setZ(teleportLocation.getZ() - 0.5D);
                } else {
                    teleportLocation.setX(teleportLocation.getX() + 0.5D);
                    teleportLocation.setZ(teleportLocation.getZ() + 0.5D);
                }


                player.teleport(teleportLocation);

                if (mainConfig.getBoolean(MainConfig.PLAY_SOUND)) {
                    if (!witherShieldDelay.contains(player.getName())) {
                        player.playSound(teleportLocation, Sound.ZOMBIE_REMEDY, 1, 0.7F);

                        witherShieldDelay.add(player.getName());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> witherShieldDelay.remove(player.getName()),
                                mainConfig.getInt(MainConfig.WITHER_SHIELD_DELAY));
                    }
                    player.playSound(teleportLocation, Sound.EXPLODE, 1, 1F);
                }

                if (mainConfig.getBoolean(MainConfig.PLAY_EFFECT)) {

                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                            EnumParticle.EXPLOSION_LARGE, true, (float) teleportLocation.getX(),
                            (float) teleportLocation.getY(), (float) teleportLocation.getZ(),
                            0, 0, 0, 7, 6
                    );

                    for(Player online : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
                    }
                }

                final double radius = mainConfig.getDouble(MainConfig.IMPLOSION_RADIUS);

                final Random random = new Random();

                final int lowerRange = mainConfig.getInt(MainConfig.IMPLOSION_DAMAGE_LOWER_RANGE);
                final int higherRange = mainConfig.getInt(MainConfig.IMPLOSION_DAMAGE_HIGHER_RANGE);

                final int damage = random.nextInt(higherRange - lowerRange) + lowerRange;

                final List<String> excludedEntities = mainConfig.getArrayList(MainConfig.EXCLUDED_ENTITY_TYPES);

                int enemies = 0;

                for (final Entity entity : player.getNearbyEntities(radius, radius, radius)) {
                    if (entity instanceof LivingEntity) {
                        final LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity.equals(player)
                                || excludedEntities.contains(entity.getType().name())) continue;

                        livingEntity.damage(damage);
                        enemies++;
                    }
                }

                final String damageString = String.format("%,d", enemies * damage);

                player.sendMessage(String.format(mainConfig.getString(MainConfig.ABILITY_MESSAGE), enemies, damageString));

                teleportList.remove(player.getName());
            }
        }
    }

}
