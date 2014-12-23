package madscience.tile;


import cpw.mods.fml.common.network.PacketDispatcher;
import madscience.factory.MadTileEntityFactory;
import madscience.mod.MadForgeMod;
import madscience.mod.MadModLoader;
import madscience.product.MadTileEntityFactoryProduct;
import madscience.sound.MadSoundTriggerEnum;
import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public abstract class MadTileEntityPrefab extends MadTileEntityModelSyncPrefab
{
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning.
     */
    private int progressMaximum;

    /**
     * The number of ticks that the current item has been cooking.
     */
    private int progressValue;

    /**
     * Determines if we currently should be playing animation frames every tick or not.
     */
    private boolean playingAnimation;

    /**
     * Current frame of animation we should use to display in world.
     */
    private int animationCurrentFrame;

    /**
     * Holds reference to our factory product which is grabbed after the object has been placed into the world.
     */
    private MadTileEntityFactoryProduct registeredMachine;

    public MadTileEntityPrefab()
    {
        super();
    }

    public MadTileEntityPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super( registeredMachine );
    }

    public MadTileEntityPrefab(String machineName)
    {
        super();
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

    /**
     * Sets internal number representing what frame of animation we should currently be playing.
     */
    public void setAnimationCurrentFrame(int animationCurrentFrame)
    {
        this.animationCurrentFrame = animationCurrentFrame;
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

    /**
     * Sets the maximum number that progress will reach for completing smelting.
     */
    public void setProgressMaximum(int progressMaximum)
    {
        this.progressMaximum = progressMaximum;
    }

    public int getProgressValue()
    {
        return progressValue;
    }

    /**
     * Sets progress value manually, does not check if value is above maximum!
     */
    public void setProgressValue(int progressValue)
    {
        this.progressValue = progressValue;
    }

    public boolean isPlayingAnimation()
    {
        return playingAnimation;
    }

    /**
     * Sets a flag that tells prefab that an animation should currently be looping on the machine.
     */
    public void setPlayingAnimation(boolean playingAnimation)
    {
        this.playingAnimation = playingAnimation;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT( nbt );

        // Current time left to cook item.
        this.progressValue = nbt.getShort( "CookTime" );

        // Should play animation status.
        this.playingAnimation = nbt.getBoolean( "ShouldPlay" );

        // Current frame of animation we are displaying.
        this.animationCurrentFrame = nbt.getInteger( "CurrentFrame" );
    }

    /**
     * Automatically increments the animation current frame. Performs no check for bounds!
     */
    public int incrementAnimationCurrentFrame()
    {
        return this.animationCurrentFrame++;
    }

    /**
     * Increases the percentage of completion for smelting a given item by one.
     */
    public int incrementProgressValue()
    {
        // Only increment if we can actually do so.
        if (this.progressValue <= this.progressMaximum)
        {
            this.progressValue++;
        }

        return this.progressValue;
    }

    /**
     * Plays a registered sound specific to this machine and only once.
     */
    public void playSoundByName(String soundNameWithoutExtension)
    {
        this.getRegisteredMachine().playSoundByName( soundNameWithoutExtension,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     worldObj );
    }

    /**
     * Sends base update packet for MadTileEntity containing position, progress, energy, fluids, textures, etc.
     */
    private void sendUpdatePacket()
    {
        // Send update to clients that require it.
        PacketDispatcher.sendPacketToAllAround( this.xCoord,
                                                this.yCoord,
                                                this.zCoord,
                                                MadModLoader.PACKET_SEND_RADIUS,
                                                worldObj.provider.dimensionId,
                                                new MadTileTemplatePacket( this ).makePacket() );
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

        if (! this.worldObj.isRemote)
        {
            // Change texture based on state.
            this.updateAnimation();

            // Play sound based on state.
            this.updateSound();

            // Update status of machine to all clients around us.
            this.sendUpdatePacket();
        }
    }

    public void updateSound()
    {
        // Get overview of machine progress.
        int progressPercentage = getItemCookTimeScaled( 100 );

        // Work Start
        if (progressPercentage == 1)
        {
            this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.WORKSTART,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     this.worldObj );
        }

        // Working
        if (this.canSmelt() && progressPercentage > 1 &&
            progressPercentage < 100)
        {
            this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.WORKING,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     this.worldObj );
        }

        // Work Finished
        if (progressPercentage >= 99)
        {
            this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.WORKEND,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     this.worldObj );
        }

        // Idle Off
        if (! this.canSmelt() && ! this.isPowered() &&
            worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
        {
            this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.IDLEOFF,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     this.worldObj );
        }

        // Idle On
        if (this.canSmelt() && this.isPowered() &&
            worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
        {
            this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.IDLEON,
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     this.worldObj );
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT( nbt );

        // Time remaining of this needle extraction to get DNA sample.
        nbt.setShort( "CookTime",
                      (short) this.progressValue );

        // Should play animation status.
        nbt.setBoolean( "ShouldPlay",
                        this.playingAnimation );

        // Current frame of animation we are displaying.
        nbt.setInteger( "CurrentFrame",
                        this.animationCurrentFrame );
    }

    @Override
    public void initiate()
    {
        super.initiate();

        // Populate our factory product object if we have not done so!
        this.registeredMachine = MadTileEntityFactory.instance().getMachineInfo( this.getMachineInternalName() );
    }

    /**
     * Called from block template when player right-clicks on the machine.
     */
    public void onBlockRightClick(World world, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        // Right Click Sound
        this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.RIGHTCLICK,
                                                 this.xCoord,
                                                 this.yCoord,
                                                 this.zCoord,
                                                 this.worldObj );

        // Open a GUI through our MadGUI handler if there is one associated with this given machine.
        par5EntityPlayer.openGui( MadForgeMod.instance,
                                  this.getRegisteredMachine().getBlockID(),
                                  world,
                                  this.xCoord,
                                  this.yCoord,
                                  this.zCoord );
    }

    /**
     * Called from block template when player left-clicks on the machine.
     */
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        // Left Click Sound
        this.registeredMachine.playTriggerSound( MadSoundTriggerEnum.LEFTCLICK,
                                                 this.xCoord,
                                                 this.yCoord,
                                                 this.zCoord,
                                                 this.worldObj );
    }
}
