package net.skillcode.hyperion.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.hyperion.listener.listeners.PlayerInteractListener;
import net.skillcode.hyperion.utils.Initializer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ListenerInitializer implements Initializer {

    private final JavaPlugin plugin;
    private final PluginManager pluginManager;

    @Inject
    public ListenerInitializer(final @NotNull JavaPlugin plugin, final @NotNull PluginManager pluginManager) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        pluginManager.registerEvents(injector.getInstance(PlayerInteractListener.class), plugin);
    }
}
