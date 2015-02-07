package madscience.items.warningsign;

import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WarningSignItem extends ItemHangingEntity
{
    public WarningSignItem(int par1)
    {
        super(par1, WarningSignEntity.class);
        this.setCreativeTab(MadEntities.tabMadScience);
        this.setMaxStackSize(64);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Only displays tooltip information when SHIFT key is pressed.
        String tooltip = StatCollector.translateToLocal(getUnlocalizedName() + ".tooltip");
        String defaultTooltip = StatCollector.translateToLocal("noshift.tooltip");
        boolean isShiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 && !isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(String.valueOf(defaultTooltip), 10));
        }
    }

    /** Callback for item usage. If the item does something special on right clicking, he will have one of those. Return True if something happen and false if it don't. This is for ITEMS, not BLOCKS */
    @Override
    public boolean onItemUse(ItemStack playerItemHolding, EntityPlayer thePlayer, World gameWorld, int xCoord, int yCoord, int zCoord, int direction, float par8, float par9, float par10)
    {
        if (direction == 0)
        {
            return false;
        }
        else if (direction == 1)
        {
            return false;
        }
        else
        {
            // Check if the player is even allowed to place things in the world.
            if (!thePlayer.canPlayerEdit(xCoord, yCoord, zCoord, direction, playerItemHolding))
            {
                return false;
            }
            else
            {                
                // Create the entity on the server world.
                int dirValue = Direction.facingToDirection[direction];
                WarningSignEntity entityhanging = new WarningSignEntity(gameWorld, xCoord, yCoord, zCoord, dirValue, WarningSignEnum.GenericWarning.ordinal(), thePlayer.username);
                
                // Ensure the painting is on a valid surface (ex. no water, lava, air).
                if (entityhanging != null && entityhanging.onValidSurface())
                {
                    if (!gameWorld.isRemote)
                    {
                        gameWorld.spawnEntityInWorld(entityhanging);
                        // MadScience.logger.info("[Warning Sign] Spawning Entity");
                    }

                    --playerItemHolding.stackSize;
                }

                return true;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }
}
