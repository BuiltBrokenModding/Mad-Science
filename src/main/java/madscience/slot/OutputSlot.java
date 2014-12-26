package madscience.slot;


import madscience.container.SlotContainerTypeEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;


public class OutputSlot extends SlotFurnace
{
    public OutputSlot(EntityPlayer entityPlayer,
                      IInventory containerObject,
                      int index,
                      int X,
                      int Y,
                      SlotContainerTypeEnum slotType)
    {
        super( entityPlayer,
               containerObject,
               index,
               X,
               Y );
    }
}
