package blumewas.ancient_progression.item;

import javax.annotation.Nullable;

import blumewas.ancient_progression.block.CinderBlock;
import blumewas.ancient_progression.init.AncientProgressionBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.world.World;

public class CinderCatcher extends APItem {

  private int maxCinder = 8;

  @Override
  public boolean isDamageable() {
    return true;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    return true;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    CompoundNBT tag = stack.getOrCreateTag();
    int cinder = tag.getInt("cinder");
    if (cinder == 0) {
      return 1.0D;
    }

    return 1.0 - ((double) cinder) / ((double) this.maxCinder);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);

    ActionResult<ItemStack> result = new ActionResult<ItemStack>(ActionResultType.PASS, stack);

    RayTraceResult traceResult = Item.rayTrace(worldIn, playerIn, FluidMode.NONE);
		if (traceResult == null || traceResult.getType() != RayTraceResult.Type.BLOCK) {
			return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
    }
    
    BlockPos pos = new BlockPos(traceResult.getHitVec());
    BlockState state = worldIn.getBlockState(pos);
    if(state.getBlock() instanceof CinderBlock) {
      result = this.collectCinder(worldIn, playerIn, stack, traceResult);
    } else {
      result = this.realeseCinder(worldIn, playerIn, stack, traceResult);
    }

    return result;
  }

  public ActionResult<ItemStack> collectCinder(World worldIn, PlayerEntity playerIn, ItemStack stack, RayTraceResult traceResult) {
    CompoundNBT tag = stack.getOrCreateTag();
    int cinder = tag.getInt("cinder");
    if (cinder >= 8) {
      return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
    }
    BlockPos pos = new BlockPos(traceResult.getHitVec());
    BlockState newState = Blocks.AIR.getDefaultState();
    worldIn.setBlockState(pos, newState, 1);
    tag.putInt("cinder", cinder+1);
    stack.setTag(tag); 

    return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
  }

  public ActionResult<ItemStack> realeseCinder(World worldIn, PlayerEntity playerIn, ItemStack stack, RayTraceResult traceResult) {
    CompoundNBT tag = stack.getOrCreateTag();
    int cinder = tag.getInt("cinder");
    if (cinder <= 0) {
      return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
    }

    BlockPos pos = new BlockPos(traceResult.getHitVec());
    Direction dir = Direction.byIndex(traceResult.subHit);
		if (worldIn.isBlockModifiable(playerIn, pos)) {
			BlockPos targetPos = pos.offset(dir);
			if (playerIn.canPlayerEdit(targetPos, dir.getOpposite(), stack)) {
				BlockState block = AncientProgressionBlocks.CINDER_TORCH.getDefaultState();
				if (placeBlock(worldIn, pos, block)) {
          this.onBlockPlaced(pos, worldIn, playerIn, stack, block);
          tag.putInt("cinder", cinder - 1);
          stack.setTag(tag);
					return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
				}
			}
		}
    return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
  }

  private boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack,
      BlockState state) {
    return setTileEntityNBT(worldIn, player, pos, stack);
  }

  private static boolean setTileEntityNBT(World worldIn, @Nullable PlayerEntity player, BlockPos pos,
      ItemStack stackIn) {
    MinecraftServer minecraftserver = worldIn.getServer();
    if (minecraftserver == null) {
      return false;
    } else {
      CompoundNBT compoundnbt = stackIn.getChildTag("BlockEntityTag");
      if (compoundnbt != null) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity != null) {
          if (!worldIn.isRemote && tileentity.onlyOpsCanSetNbt() && (player == null || !player.canUseCommandBlock())) {
            return false;
          }

          CompoundNBT compoundnbt1 = tileentity.write(new CompoundNBT());
          CompoundNBT compoundnbt2 = compoundnbt1.copy();
          compoundnbt1.merge(compoundnbt);
          compoundnbt1.putInt("x", pos.getX());
          compoundnbt1.putInt("y", pos.getY());
          compoundnbt1.putInt("z", pos.getZ());
          if (!compoundnbt1.equals(compoundnbt2)) {
            tileentity.read(compoundnbt1);
            tileentity.markDirty();
            return true;
          }
        }
      }

      return false;
    }
  }

  private boolean placeBlock(World world, BlockPos pos, BlockState state) {
    return world.setBlockState(pos, state, 11);
  }
}