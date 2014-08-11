package madscience;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import madscience.factory.MadBlockFactory;
import madscience.factory.MadItemFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.block.MadBlockFactoryProduct;
import madscience.factory.block.MadMetaBlockData;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.item.MadMetaItemData;
import madscience.factory.tile.MadTileEntityFactoryProduct;
import madscience.mobs.abomination.AbominationSounds;
import madscience.mobs.creepercow.CreeperCowSounds;
import madscience.mobs.werewolf.WerewolfMobSounds;
import madscience.mobs.woolycow.WoolyCowSounds;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadSounds
{
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) // NO_UCD (unused code)
    {
        // Load sounds as required by event.
        this.loadBlockSounds(event);
        this.loadItemSounds(event);
        this.loadTileEntitySounds(event);
        
        // ----
        // MOBS
        // ----

        // Werewolf
        WerewolfMobSounds.init(event);

        // Creeper Cow
        CreeperCowSounds.init(event);

        // Wooly Cow
        WoolyCowSounds.init(event);

        // Abomination
        AbominationSounds.init(event);
    }

    private void loadTileEntitySounds(SoundLoadEvent event)
    {
        // -----------
        // TILE ENTITY
        // -----------
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Grab processed list of filenames of sounds that need to be registered with Forge/MC.
                String[] unregisteredSounds = registeredMachine.loadSoundArchive();
                if (unregisteredSounds == null)
                {
                    continue;
                }
                
                // Loop through the filenames and register them.
                for (String unregisteredSound: unregisteredSounds)
                {
                    event.manager.addSound(unregisteredSound);
                    //MadScience.logger.info("[" + registeredMachine.getMachineName() + "]Registering Sound:" + unregisteredSound);
                }
            }
        }
    }

    private void loadItemSounds(SoundLoadEvent event)
    {
        // -----
        // ITEMS
        // -----
        Iterable<MadItemFactoryProduct> registeredItems = MadItemFactory.instance().getItemInfoList();
        for (Iterator iterator = registeredItems.iterator(); iterator.hasNext();)
        {
            MadItemFactoryProduct registeredItem = (MadItemFactoryProduct) iterator.next();
            if (registeredItem != null)
            {
                List<String> unregisteredSounds = new ArrayList<String>();
                
                // Loop through all sub-items.
                for (MadMetaItemData subItem : registeredItem.getSubItems())
                {
                    // Load sound archive for each sub-item.
                    for (String unloadedSound : subItem.loadSoundArchive())
                    {
                        unregisteredSounds.add(unloadedSound);
                    }
                }
                
                // Loop through the filenames and register them.
                for (String unregisteredSound: unregisteredSounds)
                {
                    event.manager.addSound(unregisteredSound);
                    //MadScience.logger.info("[" + registeredMachine.getMachineName() + "]Registering Sound:" + unregisteredSound);
                }
            }
        }
    }

    private void loadBlockSounds(SoundLoadEvent event)
    {
        // ------
        // BLOCKS
        // ------
        Iterable<MadBlockFactoryProduct> registeredBlocks = MadBlockFactory.instance().getBlockInfoList();
        for (Iterator iterator = registeredBlocks.iterator(); iterator.hasNext();)
        {
            MadBlockFactoryProduct registeredBlock = (MadBlockFactoryProduct) iterator.next();
            if (registeredBlock != null)
            {
                List<String> unregisteredSounds = new ArrayList<String>();
                
                // Loop through all sub-blocks.
                for (MadMetaBlockData subItem : registeredBlock.getSubBlocks())
                {
                    // Load sound archive for each sub-block.
                    for (String unloadedSound : subItem.loadSoundArchive())
                    {
                        unregisteredSounds.add(unloadedSound);
                    }
                }
                
                // Loop through the filenames and register them.
                for (String unregisteredSound: unregisteredSounds)
                {
                    event.manager.addSound(unregisteredSound);
                    //MadScience.logger.info("[" + registeredMachine.getMachineName() + "]Registering Sound:" + unregisteredSound);
                }
            }
        }
    }
}
