package madscience.factory;

import net.minecraft.entity.player.InventoryPlayer;
import madscience.factory.buttons.MadGUIButtonInterface;
import madscience.factory.controls.MadGUIControlInterface;
import madscience.factory.energy.MadEnergyInterface;
import madscience.factory.fluids.MadFluidInterface;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import madscience.factory.templates.MadContainerTemplate;
import madscience.factory.templates.MadGUITemplate;
import madscience.tileentities.prefab.MadTileEntity;

public class MadTileEntityFactoryProduct
{
    /* Holds the internal name of this machine as used in config files and referenced in other lists. */
    private String machineName;

    /* Reference number used by Forge/MC to keep track of this tile entity. */
    private int blockID;

    /* Stores all of the slot containers where items can be inputed and extracted from. */
    private MadSlotContainerInterface[] containerTemplate = new MadSlotContainerInterface[0];

    /* Stores all of the GUI controls like tanks, progress bars, animations, etc. */
    private MadGUIControlInterface[] guiControlsTemplate = new MadGUIControlInterface[0];

    /* Stores all of the GUI button, includes invisible ones with custom textures also. */
    private MadGUIButtonInterface[] guiButtonTemplate = new MadGUIButtonInterface[0];

    /* Stores all of the fluids that this machine will be able to interface with it's internal tank methods. */
    private MadFluidInterface[] fluidsSupported = new MadFluidInterface[0];
    
    /* Stores information about how we want to plugged into other electrical grids. */
    private MadEnergyInterface[] energySupported = new MadEnergyInterface[0];

    MadTileEntityFactoryProduct(String machineName, int blockID)
    {
        this.machineName = machineName;
        this.blockID = blockID;
    }

    /** Returns client GUI which can be pushed to renderer. */
    public MadGUITemplate getClientGUIElement(InventoryPlayer playerEntity, MadTileEntity worldEntity)
    {
        return new MadGUITemplate(playerEntity, worldEntity);
    }

    /** Returns container slots for storing items in machines on server. */
    public MadContainerTemplate getServerGUIElement(InventoryPlayer playerEntity, MadTileEntity worldEntity)
    {
        return new MadContainerTemplate(playerEntity, worldEntity);
    }

    /** Returns machines internal name as it should be referenced by rest of code. */
    public String getMachineName()
    {
        return machineName;
    }

    public MadSlotContainerInterface[] getContainerTemplate()
    {
        return containerTemplate;
    }

    public void setContainerTemplate(MadSlotContainerInterface[] containerTemplate)
    {
        this.containerTemplate = containerTemplate;
    }

    public MadGUIControlInterface[] getGuiControlsTemplate()
    {
        return guiControlsTemplate;
    }

    public void setGuiControlsTemplate(MadGUIControlInterface[] guiControlsTemplate)
    {
        this.guiControlsTemplate = guiControlsTemplate;
    }

    public MadGUIButtonInterface[] getGuiButtonTemplate()
    {
        return guiButtonTemplate;
    }

    public void setGuiButtonTemplate(MadGUIButtonInterface[] guiButtonTemplate)
    {
        this.guiButtonTemplate = guiButtonTemplate;
    }

    public int getBlockID()
    {
        return blockID;
    }

    public MadFluidInterface[] getFluidsSupported()
    {
        return fluidsSupported;
    }

    public void setFluidsSupported(MadFluidInterface[] fluidSupported)
    {
        this.fluidsSupported = fluidSupported;
    }

    public MadEnergyInterface[] getEnergySupported()
    {
        return energySupported;
    }

    public void setEnergySupported(MadEnergyInterface[] energySupported)
    {
        this.energySupported = energySupported;
    }
}