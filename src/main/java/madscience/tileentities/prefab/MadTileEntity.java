package madscience.tileentities.prefab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.factory.tileentity.MadTileEntityInterface;
import net.minecraft.nbt.NBTTagCompound;

public abstract class MadTileEntity extends MadTileEntityEnergy implements MadTileEntityInterface
{
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int progressMaximum;

    /** The number of ticks that the current item has been cooking for */
    private int progressValue;

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean playingAnimation;

    /** Current frame of animation we should use to display in world. */
    private int animationCurrentFrame;

    /** Path to current texture that should be displayed on our model. */
    private String entityTexture;
    
    public MadTileEntity()
    {
        super();
    }
    
    public MadTileEntity(String machineName)
    {
        super(machineName);
        this.entityTexture = "models/" + machineName + "/idle.png";
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (progressMaximum == 0)
        {
            // MadScience.logger.info("CLIENT: getItemCookTimeScaled() was called with currentItemCookingMaximum being zero!");
            progressMaximum = 200;
        }

        return (progressValue * prgPixels) / progressMaximum;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        // Current time left to cook item.
        this.progressValue = nbt.getShort("CookTime");

        // Should play animation status.
        this.playingAnimation = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.animationCurrentFrame = nbt.getInteger("CurrentFrame");
        
        // Path to current texture what should be loaded onto the model.
        this.entityTexture = nbt.getString("TexturePath");
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        // Time remaining of this needle extraction to get DNA sample.
        nbt.setShort("CookTime", (short) this.progressValue);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.playingAnimation);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.animationCurrentFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.entityTexture);
    }

    @Override
    public void updateAnimation()
    {
        
    }

    @Override
    public void updateSound()
    {
        
    }

    @Override
    public void smeltItem()
    {
        
    }

    @Override
    public boolean canSmelt()
    {
        return false;
    }

    public int getProgressMaximum()
    {
        return progressMaximum;
    }

    public void setProgressMaximum(int progressMaximum)
    {
        this.progressMaximum = progressMaximum;
    }

    public int getProgressValue()
    {
        return progressValue;
    }

    public void setProgressValue(int progressValue)
    {
        this.progressValue = progressValue;
    }

    public boolean isPlayingAnimation()
    {
        return playingAnimation;
    }

    public void setPlayingAnimation(boolean playingAnimation)
    {
        this.playingAnimation = playingAnimation;
    }

    public int getAnimationCurrentFrame()
    {
        return animationCurrentFrame;
    }

    public void setAnimationCurrentFrame(int animationCurrentFrame)
    {
        this.animationCurrentFrame = animationCurrentFrame;
    }

    public String getEntityTexture()
    {
        return entityTexture;
    }

    public void setEntityTexture(String entityTexture)
    {
        this.entityTexture = entityTexture;
    }
}
