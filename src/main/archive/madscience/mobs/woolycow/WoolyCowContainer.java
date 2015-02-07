package madscience.mobs.woolycow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

class WoolyCowContainer extends Container
{
    final WoolyCowMobEntity theWoolyCow;

    WoolyCowContainer(WoolyCowMobEntity par1EntitySheep)
    {
        this.theWoolyCow = par1EntitySheep;
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
}
