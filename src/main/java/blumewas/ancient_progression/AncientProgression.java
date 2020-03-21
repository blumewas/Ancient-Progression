package blumewas.ancient_progression;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(AncientProgression.MODID)
public class AncientProgression
{

    public static final String MODID = "ancient_progression";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public AncientProgression() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(ModEventSubscriber.class);

        LOGGER.debug("Hello from Ancient Progression!");
    }
}
