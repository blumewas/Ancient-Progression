package blumewas.ancient_progression;

import blumewas.ancient_progression.block.APBlock;
import blumewas.ancient_progression.init.AncientProgressionBlocks;
import blumewas.ancient_progression.item.APBlockItem;
import blumewas.ancient_progression.item.APItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = AncientProgression.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {
  
  @SubscribeEvent
  public static void onRegisterItems(RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
      setup(new APItem(), "example_item"),
      setup(new APBlockItem(AncientProgressionBlocks.COPPER_ORE), "copper_ore")
    );
  }

  @SubscribeEvent
  public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll(
      setup(new APBlock(), "copper_ore")
    );
  }

  public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
    return setup(entry, new ResourceLocation(AncientProgression.MODID, name));
  }
  
  public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
    entry.setRegistryName(registryName);
    return entry;
  }
}