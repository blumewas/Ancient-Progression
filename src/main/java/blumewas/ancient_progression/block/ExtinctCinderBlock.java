package blumewas.ancient_progression.block;

import java.util.List;
import java.util.Random;

import blumewas.ancient_progression.init.AncientProgressionItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

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

  @Override
  public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

    if(entityIn instanceof ItemEntity) {
      ItemEntity item = (ItemEntity) entityIn;
      List<ItemEntity> items = worldIn.getEntitiesWithinAABB(item.getClass(), new AxisAlignedBB(pos));
      boolean hasCopper = false;
      boolean hasGold = false;
      for(ItemEntity ie : items){
        ItemStack itemStack = ie.getItem();
        if (itemStack.getItem() == Items.GOLD_INGOT) {
          hasGold = true;
        } else if (itemStack.getItem() == AncientProgressionItems.COPPER_INGOT){
          if(itemStack.getCount() >= 3) {
            hasCopper = true;
          }
        }
      }
      if(hasCopper && hasGold) {
        for(ItemEntity ie : items){
          ItemStack itemStack = ie.getItem();
          if (itemStack.getItem() == Items.GOLD_INGOT) {
            itemStack.shrink(1);
          } else if (itemStack.getItem() == AncientProgressionItems.COPPER_INGOT){
            itemStack.shrink(3);
          }
        }

        // Spawn Auricupride Ingots
        ItemStack auricupride = new ItemStack(AncientProgressionItems.AURICUPRIDE_INGOT, 2);
        worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), auricupride));
        worldIn.addParticle(ParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
        // remove one cinder
        int remains = state.get(REMAINS);
        BlockState newState = state;
        if(remains - 1 <= 0) {
          newState = Blocks.AIR.getDefaultState();
        } else {
          newState = state.with(REMAINS, remains-1);
        }
        worldIn.setBlockState(pos, newState, 1);
      }
    }
  }
}