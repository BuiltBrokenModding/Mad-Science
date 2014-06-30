package madscience.mobs.woolycow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

class WoolyCowContainer extends Container
{
    WoolyCowContainer(WoolyCowMobEntity par1EntitySheep)
    {

    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
}
