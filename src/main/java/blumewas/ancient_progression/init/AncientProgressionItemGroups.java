package blumewas.ancient_progression.init;

import java.util.function.Supplier;

import blumewas.ancient_progression.AncientProgression;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AncientProgressionItemGroups {
  
  public static final ItemGroup MOD_ITEM_GROUP = new AncientProgressionItemGroup(AncientProgression.MODID, () -> new ItemStack(AncientProgressionItems.CINDER_CATCHER));

  public static class AncientProgressionItemGroup extends ItemGroup {
    private final Supplier<ItemStack> iconSupplier;

    public AncientProgressionItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
      super(name);
      this.iconSupplier = iconSupplier;
    }
  
    @Override
    public ItemStack createIcon() {
      return iconSupplier.get();
    }
  }

}