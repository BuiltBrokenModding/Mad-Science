package madscience.factory.tileentity;

import madscience.MadConfig;
import madscience.MadScience;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.sounds.MadSoundTriggerEnum;
import madscience.factory.tileentity.prefab.MadTileEntityEnergyPrefab;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class MadTileEntityPrefab extends MadTileEntityEnergyPrefab
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

    /** Holds reference to our factory product which is grabbed after the object has been placed into the world. */
    private MadTileEntityFactoryProduct registeredMachine;

    public MadTileEntityPrefab()
    {
        super();
    }

    public MadTileEntityPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        this.entityTexture = "models/" + registeredMachine.getMachineName() + "/idle.png";
    }

    public MadTileEntityPrefab(String machineName)
    {
        super();
        MadScience.logger.info("[PlzConvertMe]" + machineName);
    }

    public boolean canSmelt()
    {
        // Default response is to return false.
        return false;
    }

    public int getAnimationCurrentFrame()
    {
        return animationCurrentFrame;
    }

    public String getEntityTexture()
    {
        return entityTexture;
    }

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

    public int getProgressMaximum()
    {
        return progressMaximum;
    }

    public int getProgressValue()
    {
        return progressValue;
    }

    public boolean isPlayingAnimation()
    {
        return playingAnimation;
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

    public void setAnimationCurrentFrame(int animationCurrentFrame)
    {
        this.animationCurrentFrame = animationCurrentFrame;
    }

    public void setEntityTexture(String entityTexture)
    {
        this.entityTexture = entityTexture;
    }

    public void setPlayingAnimation(boolean playingAnimation)
    {
        this.playingAnimation = playingAnimation;
    }

    public void setProgressMaximum(int progressMaximum)
    {
        this.progressMaximum = progressMaximum;
    }

    public void setProgressValue(int progressValue)
    {
        this.progressValue = progressValue;
    }
    
    /** Sends base update packet for MadTileEntity containing position, progress, energy, fluids, textures, etc. */
    public void sendUpdatePacket()
    {
        // Send update to clients that require it.
        PacketDispatcher.sendPacketToAllAround(
                this.xCoord,
                this.yCoord,
                this.zCoord,
                MadConfig.PACKETSEND_RADIUS,
                worldObj.provider.dimensionId,
                new MadTileEntityPacketTemplate(this.xCoord, this.yCoord, this.zCoord, this.getProgressValue(), this.getProgressMaximum(), getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), this.getFluidAmount(), this
                        .getFluidCapacity(), this.getEntityTexture()).makePacket());
    }

    public void smeltItem()
    {
        
    }

    public void updateAnimation()
    {
        
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

    }

    public void updateSound()
    {
        // Get overview of machine progress.
        int progressPercentage = getItemCookTimeScaled(100);
        
        // Work Start
        if (progressPercentage == 1)
        {
            this.registeredMachine.playTriggerSound(MadSoundTriggerEnum.WORKSTART, this.xCoord, this.yCoord, this.zCoord, this.worldObj);
        }
        
        // Working
        if (this.canSmelt() && progressPercentage > 1 && progressPercentage < 100)
        {
            this.registeredMachine.playTriggerSound(MadSoundTriggerEnum.WORKING, this.xCoord, this.yCoord, this.zCoord, this.worldObj);
        }
        
        // Work Finished
        if (progressPercentage >= 99)
        {
            this.registeredMachine.playTriggerSound(MadSoundTriggerEnum.WORKEND, this.xCoord, this.yCoord, this.zCoord, this.worldObj);
        }
        
        // Idle Off
        if (!this.canSmelt() && !this.isPowered() && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
        {
            this.registeredMachine.playTriggerSound(MadSoundTriggerEnum.IDLEOFF, this.xCoord, this.yCoord, this.zCoord, this.worldObj);
        }
        
        // Idle On
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
        {
            this.registeredMachine.playTriggerSound(MadSoundTriggerEnum.IDLEON, this.xCoord, this.yCoord, this.zCoord, this.worldObj);
        }
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
    public void initiate()
    {
        super.initiate();
        
        // Populate our factory product object if we have not done so!
        this.registeredMachine = MadTileEntityFactory.getMachineInfo(this.getMachineInternalName());
    }


}
