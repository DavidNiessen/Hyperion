package net.skillcode.hyperion.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.hyperion.command.commands.HyperionCommand;
import net.skillcode.hyperion.utils.Initializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CommandInitializer implements Initializer {

    @Inject
    private JavaPlugin plugin;

    @Override
    public void init(final @NotNull Injector injector) {
        plugin.getCommand("hyperion").setExecutor(injector.getInstance(HyperionCommand.class));
    }
}
