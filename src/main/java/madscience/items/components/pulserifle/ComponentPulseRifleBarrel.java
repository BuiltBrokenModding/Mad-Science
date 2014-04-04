package madscience.items.components.pulserifle;

import madscience.items.ItemComponent;
import net.minecraft.item.Item;

public class ComponentPulseRifleBarrel extends ItemComponent
{
    public ComponentPulseRifleBarrel(int itemID)
    {
        super(itemID);
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        // Prevents us having to rotate the item 180 degrees in renderer.
        return true;
    }
}
