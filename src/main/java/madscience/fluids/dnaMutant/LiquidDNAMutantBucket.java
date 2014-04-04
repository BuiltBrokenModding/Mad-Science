package madscience.fluids.dnamutant;

import java.util.List;

import madscience.MadEntities;
import madscience.MadFluids;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.ItemFluidContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LiquidDNAMutantBucket extends ItemFluidContainer
{

    public LiquidDNAMutantBucket(int id, int capacity)
    {
        super(id);

        // Add the block to the specific tab in creative mode.
        this.setCreativeTab(MadEntities.tabMadScience);
    }
    
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {

        // world.playSoundAtEntity(player, "mazlt:sound", 1F, 1F);

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            FillBucketEvent event = new FillBucketEvent(player, item, world, movingobjectposition);

            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return item;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                if (player.capabilities.isCreativeMode)
                {
                    return item;
                }

                if (--item.stackSize <= 0)
                {
                    return event.result;
                }

                if (!player.inventory.addItemStackToInventory(event.result))
                {
                    player.dropPlayerItem(event.result);
                }

                return item;
            }

            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, x, y, z))
                {
                    return item;
                }

                if (movingobjectposition.sideHit == 0)
                {
                    --y;
                }

                if (movingobjectposition.sideHit == 1)
                {
                    ++y;
                }

                if (movingobjectposition.sideHit == 2)
                {
                    --z;
                }

                if (movingobjectposition.sideHit == 3)
                {
                    ++z;
                }

                if (movingobjectposition.sideHit == 4)
                {
                    --x;
                }

                if (movingobjectposition.sideHit == 5)
                {
                    ++x;
                }

                if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, item))
                {
                    return item;
                }

                if (this.tryPlaceContainedLiquid(world, x, y, z) && !player.capabilities.isCreativeMode)
                {
                    return new ItemStack(Item.bucketEmpty);
                }

            }

            return item;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.itemIcon = ir.registerIcon(MadScience.ID + ":" + MadFluids.LIQUIDDNA_MUTANT_BUCKET_INTERNALNAME);
    }

    public boolean tryPlaceContainedLiquid(World w, int x, int y, int z)
    {
        // Attempts to place the MazFluid contained inside the bucket.
        Material material = w.getBlockMaterial(x, y, z);
        boolean isNotSolid = !material.isSolid();

        if (!w.isAirBlock(x, y, z) && !isNotSolid)
        {
            return false;
        }
        else
        {

            if (!w.isRemote && isNotSolid && !material.isLiquid())
            {
                w.destroyBlock(x, y, z, true);
            }
            w.setBlock(x, y, z, MadFluids.LIQUIDDNA_MUTANT.getBlockID(), 0, 3);
            return true;
        }

    }
}
