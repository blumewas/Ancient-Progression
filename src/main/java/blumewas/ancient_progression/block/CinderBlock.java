package blumewas.ancient_progression.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CinderBlock extends APBlock {

  private static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);

  public CinderBlock() {
    super(getProps());
  }

  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }

  protected static Properties getProps() {
    Properties props = Properties.create(Material.ROCK);
    props.doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(14);
    return props;
  }
}