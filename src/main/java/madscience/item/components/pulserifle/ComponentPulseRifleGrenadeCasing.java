package madscience.item.components.pulserifle;


public class ComponentPulseRifleGrenadeCasing extends ItemComponentBase
{

    public ComponentPulseRifleGrenadeCasing(int itemID)
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
