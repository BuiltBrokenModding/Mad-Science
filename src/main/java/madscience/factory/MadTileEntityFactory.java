package madscience.factory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInterface;
import madscience.factory.controls.MadGUIControlInterface;
import madscience.factory.energy.MadEnergyInterface;
import madscience.factory.fluids.MadFluidInterface;
import madscience.factory.slotcontainers.MadSlotContainerInterface;

public class MadTileEntityFactory
{
    private static final Map<String, MadTileEntityFactoryProduct> registeredMachines = new LinkedHashMap<String, MadTileEntityFactoryProduct>();

    public static MadTileEntityFactoryProduct getMachineInfo(String id)
    {
        MadScience.logger.info("[MadMachineFactory]Querying machine: " + id);
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
            MadSlotContainerInterface[] containerTemplate,
            MadGUIControlInterface[] guiTemplate,
            MadGUIButtonInterface[] buttonTemplate,
            MadFluidInterface[] fluidsTemplate,
            MadEnergyInterface[] energyTemplate) throws IllegalArgumentException
    {
        // Setup basic tile entity template object, the name and ID are unchangeable and referenced only.
        MadTileEntityFactoryProduct tileEntityProduct = new MadTileEntityFactoryProduct(machineName, blockID);
        
        // Adds required things most tile entities need such as GUI, slots, energy and fluid support, etc.
        tileEntityProduct.setContainerTemplate(containerTemplate);
        tileEntityProduct.setGuiControlsTemplate(guiTemplate);
        tileEntityProduct.setGuiButtonTemplate(buttonTemplate);
        tileEntityProduct.setFluidsSupported(fluidsTemplate);
        tileEntityProduct.setEnergySupported(energyTemplate);

        if (!isValidMachineID(tileEntityProduct.getMachineName()))
        {
            throw new IllegalArgumentException("Duplicate MadTileEntityFactoryProduct '" + tileEntityProduct.getMachineName() + "' was added. Execution halted!");
        }

        MadScience.logger.info("[MadMachineFactory]Registering machine: " + tileEntityProduct.getMachineName());
        registeredMachines.put(tileEntityProduct.getMachineName(), tileEntityProduct);
        return true;
    }

}