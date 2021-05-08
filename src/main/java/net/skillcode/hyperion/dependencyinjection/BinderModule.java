package net.skillcode.hyperion.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import net.skillcode.hyperion.annotations.PostConstruct;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BinderModule extends AbstractModule implements TypeListener {

    private final JavaPlugin javaPlugin;

    public BinderModule(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    protected void configure() {
        super.bindListener(Matchers.any(), this);

        super.bind(JavaPlugin.class).toInstance(javaPlugin);
        super.bind(PluginManager.class).toInstance(javaPlugin.getServer().getPluginManager());
    }

    @Override
    public <I> void hear(final @NotNull TypeLiteral<I> typeLiteral, final @NotNull TypeEncounter<I> typeEncounter) {
        typeEncounter.register((InjectionListener<I>) i ->
                Arrays.stream(i.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PostConstruct.class))
                        .forEach(method -> invokeMethod(method, i)));
    }

    private void invokeMethod(final @NotNull Method method, final @NotNull Object object) {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
