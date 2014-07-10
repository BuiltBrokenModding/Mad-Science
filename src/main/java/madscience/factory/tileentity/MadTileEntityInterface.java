package madscience.factory.tileentity;

public interface MadTileEntityInterface
{
    /** Determines if this machine is capable of doing work at this given time. 
     * @throws Exception */
    public abstract boolean canSmelt();

    /** Run after the machine has completed a cycle of work */
    public abstract void smeltItem();

    /** Update current texture that should be displayed based on our status. */
    public abstract void updateAnimation();

    /** Plays sounds based on our state. */
    public abstract void updateSound();

}