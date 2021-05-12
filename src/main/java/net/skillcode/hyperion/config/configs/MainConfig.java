package net.skillcode.hyperion.config.configs;

import net.skillcode.hyperion.annotations.PostConstruct;
import net.skillcode.hyperion.config.Configurable;
import net.skillcode.hyperion.utils.Constants;
import net.skillcode.hyperion.utils.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainConfig extends Configurable {

    public static final String COMMAND_PERMISSION = "command_permission";
    public static final String NO_PERMISSION_MESSAGE = "no_permission_message";
    public static final String PARTICLE_QUALITY = "particle_quality";
    public static final String TELEPORT_WIDE = "teleport_wide";
    public static final String IMPLOSION_RADIUS = "implosion_radius";
    public static final String IMPLOSION_DAMAGE_LOWER_RANGE = "implosion_damage_lower_range";
    public static final String IMPLOSION_DAMAGE_HIGHER_RANGE = "implosion_damage_higher_range";
    public static final String ABILITY_DELAY = "ability_delay";
    public static final String WITHER_SHIELD_DELAY = "wither_shield_delay";
    public static final String ABILITY_MESSAGE = "ability_message";
    public static final String PLAY_SOUND = "play_sound";
    public static final String PLAY_EFFECT = "play_effect";
    public static final String EXCLUDED_ENTITY_TYPES = "excluded_entity_types";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_LORE = "item_lore";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(COMMAND_PERMISSION, "hyperion.get"));
        list.add(new Pair<>(NO_PERMISSION_MESSAGE, "&cYou don't have permission to execute this command!"));
        list.add(new Pair<>(PARTICLE_QUALITY, 3));
        list.add(new Pair<>(TELEPORT_WIDE, 10));
        list.add(new Pair<>(IMPLOSION_RADIUS, 6));
        list.add(new Pair<>(IMPLOSION_DAMAGE_LOWER_RANGE, 1000000));
        list.add(new Pair<>(IMPLOSION_DAMAGE_HIGHER_RANGE, 15000000));
        list.add(new Pair<>(ABILITY_DELAY, 2));
        list.add(new Pair<>(WITHER_SHIELD_DELAY, 100));
        list.add(new Pair<>(ABILITY_MESSAGE, "&7Your Implosion hit &c%1$s &7enemies for &c%2$s.00 &7damage."));
        list.add(new Pair<>(PLAY_SOUND, true));
        list.add(new Pair<>(PLAY_EFFECT, true));
        list.add(new Pair<>(EXCLUDED_ENTITY_TYPES, new ArrayList<>(Arrays.asList("PLAYER", "ARMOR_STAND"))));
        list.add(new Pair<>(ITEM_NAME, "&dWithered Hyperion &c✪✪✪✪&6✪"));
        list.add(new Pair<>(ITEM_LORE, new ArrayList<>(Arrays.asList(
                "&7Gear Score: &d1102 &8(3189)",
                "&7Damage: &c+317 &e(+30) &8(+1,008.06)",
                "&7Strength: &c+382 &e(+30) &6[+5] &9(Withered +197) &8(+1,214.76)",
                "&7Crit Damage: &c+60% &8(+190.8%)",
                " ",
                "&7Intelligence: &a+404 &8(+1,284.72)",
                "&7Ferocity: &a+35 &8(+52.5)",
                " ",
                "&d&lChimera V, &9Critical VI",
                "&9Smite VII, First Strike IV, Giant Killer VI",
                "&9Execute V, Lethality VI, Ender Slayer VI",
                "&9Cubism V, Impaling III, Vampirism VI",
                "&9Life Steal IV, Looting IV, Luck VI",
                "&9Scavenger IV, Experience IV, Thunderlord VI",
                "&9Telekinesis I, Vicious V",
                " ",
                "&b⬧ Music Rune",
                "&8Requires level 15",
                " ",
                "&7Deals +&c50% &7damage to",
                "&7Withers. Grants &c+1 ❁ Damage",
                "&7and &a+2 &b✎ Intelligence",
                "&7per &cCatacombs &7level.",
                " ",
                "&7Your Catacombs Level: &c27",
                " ",
                "&aScroll Abilities:",
                "&6Item Ability: Wither Impact &e&lRIGHT CLICK",
                "&7Teleport &a10 Blocks &7ahead of",
                "&7you. Then implode dealing",
                "&c138,208.2 &7damage to nearby",
                "&7enemies. Also applies the wither",
                "&7shield scroll ability reducing",
                "&7damage taken and granting an",
                "&7absorption shield for &e5",
                "&7seconds.",
                "&8Mana Cost: &3300",
                " ",
                "&9Withered Bonus",
                "&7Grants &a+1 &c❁ Strength &7per",
                "&cCatacombs &7level.",
                " ",
                "&d&l&kA&a &d&lMYTHIC DUNGEON SWORD &kA"
        ))));
    }
}
