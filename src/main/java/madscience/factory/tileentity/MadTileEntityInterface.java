package madscience.factory.tileentity;

public interface MadTileEntityInterface
{
    /** Allows the entity to update its state. */
    public abstract void updateEntity();
    
    /** Update current texture that should be displayed based on our status. */
    public abstract void updateAnimation();
    
    /** Plays sounds based on our state. */
    public abstract void updateSound();

    /** Run after the machine has completed a cycle of work */
    public abstract void smeltItem();
    
    /** Determines if this machine is capable of doing work at this given time. */
    public abstract boolean canSmelt();
    
}