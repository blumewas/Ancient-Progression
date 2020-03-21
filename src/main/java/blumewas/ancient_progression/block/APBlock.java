package blumewas.ancient_progression.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class APBlock extends Block {

  public APBlock() {
    this(getProps());
  }

  public APBlock(Properties properties) {
    super(properties);
  }

  protected static Properties getProps() {
    Properties props = Properties.create(Material.ROCK);
    props.hardnessAndResistance(3.0F, 3.0F);
    return props;
  }

}