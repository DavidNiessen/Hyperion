package net.skillcode.hyperion;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.skillcode.hyperion.command.CommandInitializer;
import net.skillcode.hyperion.config.ConfigInitializer;
import net.skillcode.hyperion.dependencyinjection.BinderModule;
import net.skillcode.hyperion.listener.ListenerInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public class Hyperion extends JavaPlugin {

    @Inject
    private ConfigInitializer configInitializer;
    @Inject
    private ListenerInitializer listenerInitializer;
    @Inject
    private CommandInitializer commandInitializer;

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new BinderModule(this));
        injector.injectMembers(this);

        listenerInitializer.init(injector);
        configInitializer.init(injector);
        commandInitializer.init(injector);
    }

}
