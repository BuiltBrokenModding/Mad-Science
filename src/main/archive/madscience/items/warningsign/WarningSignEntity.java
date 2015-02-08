package madscience.content.items.warningsign;

import java.util.ArrayList;

import madscience.MadEntities;
import madscience.MadScience;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WarningSignEntity extends EntityHanging
{
    /* Current sign that we want to be displayed on all clients. */
    public WarningSignEnum serverCurrentSignType;

    /* Current sign that is being displayed on the client. */
    public WarningSignEnum clientCurrentSignType;

    /* Should update packet information and send it to all clients in current dimension the entity is within. */
    public boolean serverShouldUpdate;

    /* Determines if client has sent request for correct sign type. */
    public boolean clientShouldRequestSignType;

    /* Determines if this warning sign has received a reply from server in response to request. */
    public boolean clientHasRecievedServerReply;

    /* Determines who the owner of this sign is. */
    public String serverOwnerName;

    /* Required for reflection */
    public WarningSignEntity(World gameWorld)
    {
        super(gameWorld);
    }

    /* Called from WarningSignItem on right-click with a warning sign item. */
    public WarningSignEntity(World par1World, int posX, int posY, int posZ, int direction, int signType, String ownerName)
    {
        super(par1World, posX, posY, posZ, direction);
        ArrayList arraylist = new ArrayList();
        WarningSignEnum[] aenumart = WarningSignEnum.values();
        int i1 = aenumart.length;

        for (int j1 = 0; j1 < i1; ++j1)
        {
            WarningSignEnum enumart = aenumart[j1];
            this.serverCurrentSignType = enumart;
            this.setDirection(direction);

            // Ensure that we are on a valid surface and match the wanted sign type in the image enumeration.
            if (this.onValidSurface())
            {
                arraylist.add(enumart);
            }
        }

        // Set our direction as dictated from params.
        this.setDirection(direction);

        // Set owner information.
        this.serverOwnerName = ownerName;

        if (!par1World.isRemote)
        {
            // Forces server to send packet to all clients on server with warning sign type.
            this.serverShouldUpdate = true;
        }
    }

    @Override
    public int getHeightPixels()
    {
        return 16;
    }

    @Override
    public int getWidthPixels()
    {
        return 16;
    }

    /** Returns true if other Entities should be prevented from moving through this Entity. */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }

    @Override
    public boolean hitByEntity(Entity par1Entity)
    {
        // Check if it was a player that hit the sign.
        if (par1Entity instanceof EntityPlayer)
        {
            // Cast entity we have determined to be a player to that type of object.
            EntityPlayer entityPlayer = (EntityPlayer) par1Entity;

            // If the player hits us while sneaking we will update the picture.
            if (entityPlayer.isSneaking())
            {
                // Only the server needs to know about the update, it will tell others about it for us.
                if (entityPlayer.worldObj != null && !entityPlayer.worldObj.isRemote)
                {
                    // Check if username matches owner information.
                    if (!isOwner(entityPlayer))
                    {
                        // Incorrect permissions error for player.
                        entityPlayer.addChatMessage("Warning sign belongs to " + this.serverOwnerName + ". " + entityPlayer.username + " cannot edit or break it.");
                        return true;
                    }
                    
                    // Change picture array number and send new update packet.
                    serverShouldUpdate = true;

                    // Make sure we won't exceed the warning sign array, if so then reset to first sign in enumeration.
                    int nextSign = this.serverCurrentSignType.ordinal() + 1;
                    int warningEnumLength = WarningSignEnum.values().length - 1;
                    if (nextSign <= warningEnumLength)
                    {
                        this.serverCurrentSignType = WarningSignEnum.values()[nextSign];
                    }
                    else
                    {
                        // We change nextSign so player read out will look correct, has no bearing in code execution since we force it to zero.
                        nextSign = 1;
                        this.serverCurrentSignType = WarningSignEnum.values()[0];
                    }
                    
                    String signDesc = getServerSignTypeDescription();
                    if (signDesc != null && !signDesc.isEmpty())
                    {
                        // Since you are the owner and are changing the sign we tell you which one you are one and what it means.
                        entityPlayer.addChatMessage(nextSign + "/" + warningEnumLength + ": " + this.serverCurrentSignType.title + ": " + signDesc);                        
                    }
                    else
                    {
                        entityPlayer.addChatMessage(nextSign + "/" + warningEnumLength + ": " + this.serverCurrentSignType.title);
                    }

                    // Debugging!
                    // MadScience.logger.info("[Server][WarningSignEntity]Changing server signType to " + this.serverCurrentSignType.title);
                }

                // Cancels the attack on server and client!
                return true;
            }
            else
            {
                // Check if we should break the object because owner hit us or just transmit description of what sign means.
                if (entityPlayer.worldObj != null && !entityPlayer.worldObj.isRemote)
                {
                    // Check if username matches owner information.
                    if (!isOwner(entityPlayer))
                    {
                        String signDesc = getServerSignTypeDescription();
                        if (signDesc != null && !signDesc.isEmpty())
                        {
                            entityPlayer.addChatMessage(signDesc);
                        }
                        return true;
                    }
                    else
                    {
                        // Attempts to break the sign and give it back to the player as an item.
                        this.attackEntityFrom(DamageSource.causePlayerDamage(entityPlayer), 0.0F);
                        return false;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns string of localized warning sign description.
     */
    private String getServerSignTypeDescription()
    {
        // Grab the localized string for the warning sign description based on title.
        String signTypeDescriptionTranslated = StatCollector.translateToLocal("warningSign.description." + this.serverCurrentSignType.title);
        String signTypeDescriptionPrefix = StatCollector.translateToLocal("warningSign.description.prefix");
        
        // Only display the string if is is actually there and has something.
        if (signTypeDescriptionTranslated != null && !signTypeDescriptionTranslated.isEmpty())
        {
            // Note: the space is added to prevent any trimming errors with localization manager.
            return signTypeDescriptionPrefix + " " + signTypeDescriptionTranslated;
        }
        
        // Default response is to return empty string is isEmpty() checks will work.
        return "";
    }

    /**
     * @param entityPlayer
     * @return boolean
     */
    private boolean isOwner(EntityPlayer entityPlayer)
    {
        if (this.serverOwnerName != null && !this.serverOwnerName.isEmpty())
        {
            if (!entityPlayer.username.equals(this.serverOwnerName))
            {
                // Cancel the attack since you are not the owner!
                return false;
            }
        }
        
        return true;
    }

    /** Called when this entity is broken. Entity parameter may be null. */
    @Override
    public void onBroken(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayer)
        {
            // Don't return a sign in creative mode just break it.
            EntityPlayer entityplayer = (EntityPlayer) par1Entity;
            if (entityplayer.capabilities.isCreativeMode)
            {
                return;
            }
        }

        // Returns the now broken warning sign as an item to the player.
        this.entityDropItem(new ItemStack(MadEntities.WARNING_SIGN), 0.0F);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        // Client side check if we should send server a request for current warning sign.
        if (worldObj != null && worldObj.isRemote)
        {
            // Checking to make sure we should be doing this and if we have already received a reply (in which case only updates should occur until relog).
            if (clientShouldRequestSignType && !clientHasRecievedServerReply)
            {
                // Don't fire packets all the time, do it every second until we get an answer.
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
                {
                    // Default sign that is used if no others are specified.
                    if (this.clientCurrentSignType == null)
                    {
                        this.clientCurrentSignType = WarningSignEnum.GenericWarning;
                    }

                    // Send packet to server asking for current painting information
                    PacketDispatcher.sendPacketToServer(new WarningSignPacketClientRequestSignType(this.entityId, this.clientCurrentSignType.ordinal()).makePacket());

                    // Debugging!
                    // MadScience.logger.info("[Client][WarningSignEntity]Sent request packet to server for Warning Sign ID " + this.entityId + " saying we are " + this.clientCurrentSignType.title);
                }
            }
        }

        // Server side check if we should send packet to all players in dimension with sign information.
        if (worldObj != null && !worldObj.isRemote && serverShouldUpdate)
        {
            // Note: This always uses the default sign first which is index zero in array.
            PacketDispatcher.sendPacketToAllInDimension(new WarningSignPacketServerUpdateSignType(this.entityId, this.serverCurrentSignType.ordinal() /* , this.hangingDirection */).makePacket(), worldObj.provider.dimensionId);

            // Prevents this from running all the time.
            serverShouldUpdate = false;

            // Debugging!
            // MadScience.logger.info("[Server][WarningSignEntity]Sent update packet for Warning Sign ID " + this.entityId + " to become sign type " + this.serverCurrentSignType.title);
        }
    }

    /** (abstract) Protected helper method to read subclass entity data from NBT. */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        // Get the sign type from NBT stored data.
        String savedSignType = par1NBTTagCompound.getString("Motive");

        // Loop through all warning sign artwork and look for matching value.
        WarningSignEnum[] allWarningSignsTypes = WarningSignEnum.values();
        int i = allWarningSignsTypes.length;

        for (int j = 0; j < i; ++j)
        {
            // Check if stored NBT sign name exists in current enumeration of signs.
            WarningSignEnum enumart = allWarningSignsTypes[j];
            if (enumart.title.equals(savedSignType))
            {
                this.serverCurrentSignType = enumart;

                // Debugging!
                // MadScience.logger.info("[Server][WarningSignEntity] Reloaded Sign From NBT and got " + this.serverCurrentSignType.title);
            }
        }
        
        // Get the username from the NBT stored data.
        String savedOwner = par1NBTTagCompound.getString("Owner");
        if (savedOwner != null && !savedOwner.isEmpty())
        {
            this.serverOwnerName = savedOwner;
        }

        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        // do nothing to prevent tracking updates
    }

    /** (abstract) Protected helper method to write subclass entity data to NBT. */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        // Only save the information about what sign to display if we have something.
        if (serverCurrentSignType != null)
        {
            par1NBTTagCompound.setString("Motive", this.serverCurrentSignType.title);
            // MadScience.logger.info("[WarningSignEntity] Saved Sign NBT and with " + this.serverCurrentSignType.title);
        }
        
        // Only save a username if we actually have one worth saving.
        if (this.serverOwnerName != null && !this.serverOwnerName.isEmpty())
        {
            par1NBTTagCompound.setString("Owner", this.serverOwnerName);   
        }

        super.writeEntityToNBT(par1NBTTagCompound);
    }
}
