package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.BlockContainer;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInterface;
import madscience.factory.controls.MadGUIControlInterface;
import madscience.factory.energy.MadEnergyInterface;
import madscience.factory.fluids.MadFluidInterface;
import madscience.factory.recipes.MadRecipeInterface;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.factory.sounds.MadSoundInterface;
import madscience.factory.tileentity.MadTileEntity;

public class MadTileEntityFactory
{
    /** Mapping of machine names to created products. */
    private static final Map<String, MadTileEntityFactoryProduct> registeredMachines = new LinkedHashMap<String, MadTileEntityFactoryProduct>();

    public static MadTileEntityFactoryProduct getMachineInfo(String id)
    {
        //MadScience.logger.info("[MadTileEntityFactory]Querying machine: " + id);
        return registeredMachines.get(id);
    }

    public static Collection<MadTileEntityFactoryProduct> getMachineInfoList()
    {
        return Collections.unmodifiableCollection(registeredMachines.values());
    }

    private static boolean isValidMachineID(String id)
    {
        return !registeredMachines.containsKey(id);
    }

    public static boolean registerMachine(String machineName, int blockID,
            Class<? extends MadTileEntity> logicClass,
            MadSlotContainerInterface[] containerTemplate,
            MadGUIControlInterface[] guiTemplate,
            MadGUIButtonInterface[] buttonTemplate,
            MadFluidInterface[] fluidsTemplate,
            MadEnergyInterface[] energyTemplate,
            MadSoundInterface[] soundArchive,
            MadRecipeInterface[] recipeArchive) throws IllegalArgumentException
    {
        // Setup basic tile entity template object, the name and ID are unchangeable and referenced only.
        MadTileEntityFactoryProduct tileEntityProduct = new MadTileEntityFactoryProduct(
                machineName,
                blockID,
                logicClass);

        // Adds required things most tile entities need such as GUI, slots, energy and fluid support, sounds, etc.
        tileEntityProduct.setContainerTemplate(containerTemplate);
        tileEntityProduct.setGuiControlsTemplate(guiTemplate);
        tileEntityProduct.setGuiButtonTemplate(buttonTemplate);
        tileEntityProduct.setFluidsSupported(fluidsTemplate);
        tileEntityProduct.setEnergySupported(energyTemplate);
        tileEntityProduct.setSoundArchive(soundArchive);
        tileEntityProduct.setRecipeArchive(recipeArchive);
        
        // Check to make sure we have not added this machine before.
        if (!isValidMachineID(tileEntityProduct.getMachineName()))
        {
            throw new IllegalArgumentException("Duplicate MadTileEntityFactoryProduct '" + tileEntityProduct.getMachineName() + "' was added. Execution halted!");
        }

        // Debugging!
        MadScience.logger.info("[MadTileEntityFactory]Registering machine: " + tileEntityProduct.getMachineName());
        
        // Actually register the machine with the product listing.
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);
        
        return true;
    }

}