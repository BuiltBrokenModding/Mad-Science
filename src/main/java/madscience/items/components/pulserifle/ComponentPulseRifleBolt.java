package madscience.items.components.pulserifle;

import madscience.items.components.ItemComponentBase;
import net.minecraft.item.Item;

public class ComponentPulseRifleBolt extends ItemComponentBase
{

    public ComponentPulseRifleBolt(int itemID)
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
