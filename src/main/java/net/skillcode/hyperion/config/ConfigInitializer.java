package net.skillcode.hyperion.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.hyperion.config.configs.MainConfig;
import net.skillcode.hyperion.utils.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ConfigInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(MainConfig.class);
    }
}
