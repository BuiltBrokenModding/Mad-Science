package madscience.fluid;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.ModMetadata;
import madscience.factory.FluidFactory;
import madscience.mod.ModLoader;
import madscience.product.FluidFactoryProduct;
import madscience.util.MiscUtils;
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
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.ItemFluidContainer;
import org.lwjgl.input.Keyboard;

import java.util.List;


public class FluidBucketTemplate extends ItemFluidContainer
{
    private String registeredFluidName;
    private FluidFactoryProduct registeredFluid;

    public FluidBucketTemplate(FluidFactoryProduct fluidFactoryProduct)
    {
        super( fluidFactoryProduct.getFluidContainerID() );

        this.registeredFluid = fluidFactoryProduct;
        this.registeredFluidName = fluidFactoryProduct.getFluidName();

        setUnlocalizedName( fluidFactoryProduct.getFluidContainerName() );

        // Add the block to the specific tab in creative mode.
        this.setCreativeTab( ModLoader.getCreativeTab() );
    }

    public FluidFactoryProduct getRegisteredFluid()
    {
        if (this.registeredFluid == null)
        {
            FluidFactoryProduct reloadedFluid = FluidFactory.instance().getFluidInfo( this.registeredFluidName );
            this.registeredFluid = reloadedFluid;
            this.registeredFluidName = reloadedFluid.getFluidName();
        }

        return this.registeredFluid;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Only displays tooltip information when SHIFT key is pressed.
        String tooltip = StatCollector.translateToLocal( getUnlocalizedName() + ".tooltip" );
        String defaultTooltip = StatCollector.translateToLocal( "noshift.tooltip" );
        boolean isShiftPressed = Keyboard.isKeyDown( Keyboard.KEY_LSHIFT );

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed)
        {
            info.addAll( MiscUtils.splitStringPerWord( tooltip,
                                                       5 ) );
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 &&
                 ! isShiftPressed)
        {
            info.addAll( MiscUtils.splitStringPerWord( String.valueOf( defaultTooltip ),
                                                       10 ) );
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {

        // world.playSoundAtEntity(player, "mazlt:sound", 1F, 1F);

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer( world,
                                                                                            player,
                                                                                            true );

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            FillBucketEvent event = new FillBucketEvent( player,
                                                         item,
                                                         world,
                                                         movingobjectposition );

            if (MinecraftForge.EVENT_BUS.post( event ))
            {
                return item;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                if (player.capabilities.isCreativeMode)
                {
                    return item;
                }

                if (-- item.stackSize <= 0)
                {
                    return event.result;
                }

                if (! player.inventory.addItemStackToInventory( event.result ))
                {
                    player.dropPlayerItem( event.result );
                }

                return item;
            }

            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                if (! world.canMineBlock( player,
                                          x,
                                          y,
                                          z ))
                {
                    return item;
                }

                if (movingobjectposition.sideHit == 0)
                {
                    -- y;
                }

                if (movingobjectposition.sideHit == 1)
                {
                    ++ y;
                }

                if (movingobjectposition.sideHit == 2)
                {
                    -- z;
                }

                if (movingobjectposition.sideHit == 3)
                {
                    ++ z;
                }

                if (movingobjectposition.sideHit == 4)
                {
                    -- x;
                }

                if (movingobjectposition.sideHit == 5)
                {
                    ++ x;
                }

                if (! player.canPlayerEdit( x,
                                            y,
                                            z,
                                            movingobjectposition.sideHit,
                                            item ))
                {
                    return item;
                }

                if (this.tryPlaceContainedLiquid( world,
                                                  x,
                                                  y,
                                                  z ) && ! player.capabilities.isCreativeMode)
                {
                    return new ItemStack( Item.bucketEmpty );
                }

            }

            return item;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegistry)
    {
        this.itemIcon = iconRegistry.registerIcon( ModMetadata.ID + ":" +
                                                   this.getRegisteredFluid().getIconFluidContainerPath() );
    }

    private boolean tryPlaceContainedLiquid(World w, int x, int y, int z)
    {
        // Attempts to place the MazFluid contained inside the bucket.
        Material material = w.getBlockMaterial( x,
                                                y,
                                                z );
        boolean isNotSolid = ! material.isSolid();

        if (! w.isAirBlock( x,
                            y,
                            z ) && ! isNotSolid)
        {
            return false;
        }
        else
        {

            if (! w.isRemote && isNotSolid && ! material.isLiquid())
            {
                w.destroyBlock( x,
                                y,
                                z,
                                true );
            }
            w.setBlock( x,
                        y,
                        z,
                        this.getRegisteredFluid().getFluidID(),
                        0,
                        3 );
            return true;
        }

    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
    {
        int blockID = world.getBlockId( pos.blockX,
                                        pos.blockY,
                                        pos.blockZ );

        if (blockID == this.getRegisteredFluid().getFluidID() && world.getBlockMetadata( pos.blockX,
                                                                                         pos.blockY,
                                                                                         pos.blockZ ) == 0)
        {
            world.setBlock( pos.blockX,
                            pos.blockY,
                            pos.blockZ,
                            0 );
            return new ItemStack( this.getRegisteredFluid().getFluidContainer() );
        }
        else
        {
            return null;
        }
    }

    @ForgeSubscribe
    public void onBucketFill(FillBucketEvent event) // NO_UCD (unused code)
    {
        ItemStack result = fillCustomBucket( event.world,
                                             event.target );

        if (result != null)
        {
            event.result = result;
            event.setResult( Result.ALLOW );
        }
    }
}
