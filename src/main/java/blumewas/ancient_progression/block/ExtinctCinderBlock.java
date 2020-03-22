package blumewas.ancient_progression.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtinctCinderBlock extends APBlock {

  private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
  public static final IntegerProperty REMAINS = IntegerProperty.create("remains", 1, 4);

  public ExtinctCinderBlock() {
    super(getProps());

    this.setDefaultState(this.getDefaultState().with(REMAINS, 1));
  }

  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE;
  }

  protected static Properties getProps() {
    Properties props = Properties.create(Material.ROCK);
    props.doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(4);
    return props;
  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    Random r = new Random();
    int remains = r.nextInt(4) + 1;
    return this.getDefaultState().with(REMAINS, remains);
  }

  @Override
	protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(REMAINS);
	}
}