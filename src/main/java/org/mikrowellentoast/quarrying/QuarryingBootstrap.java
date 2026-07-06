package org.mikrowellentoast.quarrying;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class QuarryingBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY.newHandler(
            (event) -> {
                try {
                    URI uri = this.getClass().getResource("/Quarrying").toURI();

                    event.registrar().discoverPack(uri, "provided");
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }

            }
        ));
    }
}
