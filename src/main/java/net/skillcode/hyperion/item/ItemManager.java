package net.skillcode.hyperion.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.hyperion.annotations.PostConstruct;
import net.skillcode.hyperion.config.configs.MainConfig;
import net.skillcode.hyperion.item.builder.ItemBuilder;
import net.skillcode.hyperion.item.builder.MetaType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Optional;

@Singleton
public class ItemManager {

    private ItemStack hyperion;

    @Inject
    private MainConfig mainConfig;

    @PostConstruct
    public void init() {
        final ArrayList<String> lore = new ArrayList<>();
        mainConfig.getArrayList(MainConfig.ITEM_LORE).forEach(string -> lore.add(string.replace("&", "§")));
        hyperion = ItemBuilder.create(MetaType.ITEM_META, 0)
                .material(Material.IRON_SWORD)
                .name(mainConfig.getString(MainConfig.ITEM_NAME))
                .unbreakable()
                .hideUnbreakable()
                .hideEnchants()
                .hideAttributes()
                .enchantment(Enchantment.DAMAGE_ALL, 5)
                .lore(lore)
                .build();
    }

    public Optional<ItemStack> getHyperion() {
        return Optional.ofNullable(hyperion);
    }

}
