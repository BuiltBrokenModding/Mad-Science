package madscience.items.components.pulserifle;

import madscience.factory.item.prefab.ItemComponentBase;

public class ComponentPulseRifleTrigger extends ItemComponentBase
{

    public ComponentPulseRifleTrigger(int itemID)
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
