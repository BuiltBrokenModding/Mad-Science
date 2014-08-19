package madscience.factory.item;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.furnace.MadFurnaceRecipe;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModel;
import madscience.factory.sound.MadSound;
import madscience.factory.sound.MadSoundPlaybackTypeEnum;
import madscience.factory.sound.MadSoundTriggerEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;

import com.google.gson.annotations.Expose;

public class MadMetaItemData
{
    /** Name of the item which will also be used to reference it in code and in rendering registry. */
    @Expose
    private String itemName;
    
    /** Defines what damage value on the base item will equal this sub item. */
    @Expose
    private int metaID;
    
    /** Contains all recipes that can be used to create this particular item. */
    @Expose
    private MadCraftingRecipe[] craftingRecipes;
    
    /** Contains all related sounds for this item that as associated with triggers to be played at certain times. */
    @Expose
    private MadSound[] soundArchive;
    
    /** Contains all of the models and texture information for rendering factory and Minecraft/Forge. */
    @Expose
    private MadModel modelArchive;
    
    /** Reference to item icon layers and what color they should render as. Path is relative to Minecraft/Forge asset folder for items. */
    @Expose
    private MadItemRenderPass[] renderPassArchive;
    
    /** Contains recipes that will be loaded into Minecraft vanilla furnace for processing. Allows items to be cooked in vanilla furnace. */
    @Expose
    private MadFurnaceRecipe[] furnaceRecipes;
    
    /** Determines if sounds for this given item product have been registered on Minecraft client. */
    private boolean soundArchiveLoaded = false;
    
    public MadMetaItemData(
            int metaID,
            String itemName,
            MadCraftingRecipe[] craftingRecipes,
            MadFurnaceRecipe[] furnaceRecipes,
            MadSound[] soundArchive,
            MadModel modelArchive,
            MadItemRenderPass[] renderPasses)
    {
        super();
        
        this.metaID = metaID;
        this.itemName = itemName.toLowerCase();
        this.craftingRecipes = craftingRecipes;
        this.furnaceRecipes = furnaceRecipes;
        this.soundArchive = soundArchive;
        this.modelArchive = modelArchive;
        this.renderPassArchive = renderPasses;
    }
    
    /** Populates an internal list of sounds associated with this machine and returns them as a string array to be ready by Minecraft/Forge. */
    public String[] loadSoundArchive()
    {
        // Throw an exception if we have already done this! Bad programmer!
        if (soundArchiveLoaded)
        {
            throw new IllegalStateException("Sound archive for '" + this.getItemName() + "' has already been loaded!");
        }

        // Close the door behind us.
        this.soundArchiveLoaded = true;

        // List we will be returning if everything goes well.
        List<String> soundFileList = new ArrayList<String>();
        
        if (this.getSoundArchive() == null)
        {
            MadMod.log().info("[" + this.getItemName() + "]Skipping sounds since none exist.");
            return null;
        }

        // Begin looping through all the sounds that makeup this particular product.
        for (int i = 0; i < this.getSoundArchive().length; i++)
        {
            // Grab the interface to particular sound.
            MadSound machineSound = this.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Store pathing information to where this sound lives.
            String machineSoundPath = MadMod.ID + ":" + this.getItemName() + "/";
            machineSound.setResourcePath(machineSoundPath);
            
            // For future reference we have been here.
            machineSound.setLoaded();
            
            // Reference to this sound globally which anything can address.
            MadMod.addSoundToArchive(machineSound.getSoundNameWithoutExtension(), machineSound);
            
            // Check if this sound is random one and needs multiple files checked.
            if (machineSound.getSoundPlaybackMode().equals(MadSoundPlaybackTypeEnum.RANDOM) && machineSound.getSoundRandomVariance() > 0)
            {
                // Add all the files that makeup this array of random files.
                // Note: Minecraft will automatically play a random sound if named File1,2,3.
                for (int x = 1; x <= machineSound.getSoundRandomVariance(); x++)
                {
                    MadMod.log().info("[" + this.getItemName() + "]Loading random sound " + machineSound.getSoundNameWithoutExtension() + String.valueOf(x) + " " + String.valueOf(x) + "/" + String.valueOf(machineSound.getSoundRandomVariance()));
                    String fullRandomSoundPath = machineSoundPath + machineSound.getSoundNameWithoutExtension() + x + "." + machineSound.getSoundExtension();  
                    
                    // Add to list which gets returned to Minecraft/Forge for actual loading.
                    soundFileList.add(fullRandomSoundPath);
                }
            }
            else
            {
                // Add just the individual sound file.
                MadMod.log().info("[" + this.getItemName() + "]Loading sound " + machineSound.getSoundNameWithoutExtension());
                String fullSingleSoundPath = machineSoundPath + machineSound.getSoundNameWithExtension(); 
                soundFileList.add(fullSingleSoundPath);
            }
        }
        
        // Convert list of strings into string array.
        String[] finalList = soundFileList.toArray(new String[soundFileList.size()]);

        // Check if the array is larger than zero and not null.
        if (finalList != null && finalList.length > 0)
        {
            return finalList;
        }

        // Default response is to return nothing.
        return null;
    }
    
    /** Plays a sound registered to this machine by name without extension. */
    public void playSoundByName(String soundNameWithoutExtension, EntityPlayer player)
    {
        // Check if the sound to play actually exists or not.
        for (int i = 0; i < this.getSoundArchive().length; i++)
        {
            MadSound machineSound = this.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if sound name matches the one from internal list.
            if (machineSound.getSoundNameWithoutExtension().equals(soundNameWithoutExtension))
            {
                String soundName = MadMod.ID + ":" + this.getItemName() + "." + machineSound.getSoundNameWithoutExtension();
                player.playSound(soundName, 1.0F, 1.0F);
                //MadMod.log().info("[" + this.getMachineName() + "]Playing Sound By Name: " + soundName);
                break;
            }
        }
    }

    /** Plays a sound registered to specific machine event such as working, idle, destroyed, etc. */
    public void playTriggerSound(MadSoundTriggerEnum trigger, EntityPlayer player)
    {
        // Locate the sound to play based on trigger enumeration.
        for (int i = 0; i < this.getSoundArchive().length; i++)
        {
            MadSound machineSound = this.getSoundArchive()[i];
            if (machineSound == null)
            {
                continue;
            }

            // Check if input matches the sound type.
            // Note: Multiple sounds with same trigger will play one after the other.
            if (machineSound.getSoundTrigger().equals(trigger))
            {
                String soundName = MadMod.ID + ":" + this.getItemName() + "." + machineSound.getSoundNameWithoutExtension();
                player.playSound(soundName, 1.0F, 1.0F);
                //MadMod.log().info("[" + this.getMachineName() + "]Playing Trigger Sound: " + soundName);
            }
        }
    }
    
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public int getMetaID()
    {
        return metaID;
    }

    public void setMetaID(int metaID)
    {
        this.metaID = metaID;
    }

    public MadCraftingRecipe[] getCraftingRecipes()
    {
        return craftingRecipes;
    }

    public void setCraftingRecipes(MadCraftingRecipe[] craftingRecipes)
    {
        this.craftingRecipes = craftingRecipes;
    }

    public MadSound[] getSoundArchive()
    {
        return soundArchive;
    }

    public void setSoundArchive(MadSound[] soundArchive)
    {
        this.soundArchive = soundArchive;
    }

    public MadModel getModelArchive()
    {
        return modelArchive;
    }

    public void setModelArchive(MadModel modelArchive)
    {
        this.modelArchive = modelArchive;
    }

    public MadItemRenderPass[] getRenderPassArchive()
    {
        return renderPassArchive;
    }

    public void setRenderPassArchive(MadItemRenderPass[] renderPassArchive)
    {
        this.renderPassArchive = renderPassArchive;
    }

    public int getColorForPass(int pass)
    {
        // Loop through the render passes looking for correct render pass.
        for (MadItemRenderPass renderPass : this.renderPassArchive)
        {
            if (renderPass.getRenderPass() == pass)
            {
                // Grabs the color for this given render pass.
                return renderPass.getColorRGB();
            }
        }
        
        // Default response is to return the color white.
        return 16777215;
    }

    public Icon getIconForPass(int pass)
    {
        for (MadItemRenderPass renderPass : this.renderPassArchive)
        {
            if (renderPass.getRenderPass() == pass)
            {
                // Grabs the color for this given render pass.
                return renderPass.getIcon();
            }
        }
        
        return null;
    }

    /** Return the total number of render passes required for this sub-item. */
    public int getRenderPassCount()
    {
        if (this.renderPassArchive != null)
        {
            return this.renderPassArchive.length;        
        }
        
        // Default response is to say we have a single render pass.
        return 1;
    }
    
    /** Returns item name combined with base name to form full name which is same one used in game world by registered item. */
    public String getItemNameWithBase(String baseItemName)
    {
        StringBuilder fullItemName = new StringBuilder();
        fullItemName.append(baseItemName);
        fullItemName.append(".");
        fullItemName.append(this.itemName);
        
        return fullItemName.toString();
    }

    public MadFurnaceRecipe[] getFurnaceRecipes()
    {
        return furnaceRecipes;
    }

    public void setFurnaceRecipes(MadFurnaceRecipe[] furnaceRecipes)
    {
        this.furnaceRecipes = furnaceRecipes;
    }
}
