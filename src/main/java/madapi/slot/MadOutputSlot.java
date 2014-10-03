package madapi.slot;

import madapi.container.MadSlotContainerTypeEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;

public class MadOutputSlot extends SlotFurnace
{
    public MadOutputSlot(
            EntityPlayer entityPlayer,
            IInventory containerObject,
            int index,
            int X,
            int Y,
            MadSlotContainerTypeEnum slotType)
    {
        super(entityPlayer, containerObject, index, X, Y);
    }
}
