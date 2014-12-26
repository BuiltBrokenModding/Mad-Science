package madscience.mod;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.factory.ItemFactory;
import madscience.factory.TileEntityFactory;
import madscience.item.SubItemsArchive;
import madscience.product.ItemFactoryProduct;
import madscience.product.TileEntityFactoryProduct;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SoundLoader
{
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) // NO_UCD (unused code)
    {
        // Load sounds as required by event.
        this.loadItemSounds( event );
        this.loadTileEntitySounds( event );
    }

    private void loadTileEntitySounds(SoundLoadEvent event)
    {
        // -----------
        // TILE ENTITY
        // -----------
        Iterable<TileEntityFactoryProduct> registeredMachines = TileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext(); )
        {
            TileEntityFactoryProduct registeredMachine = (TileEntityFactoryProduct) iterator.next();
            if (registeredMachine != null)
            {
                // Grab processed list of filenames of sounds that need to be registered with Forge/MC.
                String[] unregisteredSounds = registeredMachine.loadSoundArchive();
                if (unregisteredSounds == null)
                {
                    continue;
                }

                // Loop through the filenames and register them.
                for (String unregisteredSound : unregisteredSounds)
                {
                    event.manager.addSound( unregisteredSound );
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
        Iterable<ItemFactoryProduct> registeredItems = ItemFactory.instance().getItemInfoList();
        for (Iterator iterator = registeredItems.iterator(); iterator.hasNext(); )
        {
            ItemFactoryProduct registeredItem = (ItemFactoryProduct) iterator.next();
            if (registeredItem != null)
            {
                List<String> unregisteredSounds = new ArrayList<String>();

                // Loop through all sub-items.
                for (SubItemsArchive subItem : registeredItem.getSubItems())
                {
                    // Skip items that have no sounds associated with them.
                    String[] itemSounds = subItem.loadSoundArchive();
                    if (itemSounds == null)
                    {
                        continue;
                    }

                    // Load sound archive for each sub-item.
                    for (String unloadedSound : itemSounds)
                    {
                        unregisteredSounds.add( unloadedSound );
                    }
                }

                // Loop through the filenames and register them.
                for (String unregisteredSound : unregisteredSounds)
                {
                    event.manager.addSound( unregisteredSound );
                    //MadScience.logger.info("[" + registeredMachine.getMachineName() + "]Registering Sound:" + unregisteredSound);
                }
            }
        }
    }
}
