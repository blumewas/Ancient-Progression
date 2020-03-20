package blumewas.ancient_progression.item;

import blumewas.ancient_progression.init.AncientProgressionItemGroups;
import net.minecraft.item.Item;

public class APItem extends Item
{
  public APItem() {
    this(getProps());
  }

  public APItem(Properties props) {
    super(props);
  }

  private static Properties getProps() {
    Properties props = new Properties();
    props.group(AncientProgressionItemGroups.MOD_ITEM_GROUP);
    return props;
  }
}