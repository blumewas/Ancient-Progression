package blumewas.ancient_progression.item;

import blumewas.ancient_progression.init.AncientProgressionItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class APBlockItem extends BlockItem
{
  public APBlockItem(Block block) {
    this(block, getProps());
  }

  public APBlockItem(Block block, Properties props) {
    super(block, props);
  }

  private static Properties getProps() {
    Properties props = new Properties();
    props.group(AncientProgressionItemGroups.MOD_ITEM_GROUP);
    return props;
  }
}