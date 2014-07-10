package madscience.gui;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.MadTileEntity;
import madscience.factory.tileentity.MadTileEntityInterface;
import madscience.tileentities.clayfurnace.ClayfurnaceContainer;
import madscience.tileentities.clayfurnace.ClayfurnaceEntity;
import madscience.tileentities.clayfurnace.ClayfurnaceGUI;
import madscience.tileentities.cncmachine.CnCMachineContainer;
import madscience.tileentities.cncmachine.CnCMachineEntity;
import madscience.tileentities.cncmachine.CnCMachineGUI;
import madscience.tileentities.cryofreezer.CryofreezerContainer;
import madscience.tileentities.cryofreezer.CryofreezerEntity;
import madscience.tileentities.cryofreezer.CryofreezerGUI;
import madscience.tileentities.cryotube.CryotubeContainer;
import madscience.tileentities.cryotube.CryotubeEntity;
import madscience.tileentities.cryotube.CryotubeGUI;
import madscience.tileentities.dataduplicator.DataDuplicatorContainer;
import madscience.tileentities.dataduplicator.DataDuplicatorEntity;
import madscience.tileentities.dataduplicator.DataDuplicatorGUI;
import madscience.tileentities.incubator.IncubatorContainer;
import madscience.tileentities.incubator.IncubatorEntity;
import madscience.tileentities.incubator.IncubatorGUI;
import madscience.tileentities.magloader.MagLoaderContainer;
import madscience.tileentities.magloader.MagLoaderEntity;
import madscience.tileentities.magloader.MagLoaderGUI;
import madscience.tileentities.mainframe.MainframeContainer;
import madscience.tileentities.mainframe.MainframeEntity;
import madscience.tileentities.mainframe.MainframeGUI;
import madscience.tileentities.meatcube.MeatcubeContainer;
import madscience.tileentities.meatcube.MeatcubeEntity;
import madscience.tileentities.meatcube.MeatcubeGUI;
import madscience.tileentities.sanitizer.SanitizerContainer;
import madscience.tileentities.sanitizer.SanitizerEntity;
import madscience.tileentities.sanitizer.SanitizerGUI;
import madscience.tileentities.sequencer.SequencerContainer;
import madscience.tileentities.sequencer.SequencerEntity;
import madscience.tileentities.sequencer.SequencerGUI;
import madscience.tileentities.soniclocator.SoniclocatorContainer;
import madscience.tileentities.soniclocator.SoniclocatorEntity;
import madscience.tileentities.soniclocator.SoniclocatorGUI;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderContainer;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderGUI;
import madscience.tileentities.voxbox.VoxBoxContainer;
import madscience.tileentities.voxbox.VoxBoxEntity;
import madscience.tileentities.voxbox.VoxBoxGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MadGUI implements IGuiHandler
{
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // Grab a running instance of the block on the server world.
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        // Check if this block is one of ours.
        if (tileEntity instanceof MadTileEntity)
        {
            // Cast the object as MadTE so we can get internal name from it.
            MadTileEntity madTile = (MadTileEntity) tileEntity;
            
            if (madTile != null)
            {
                // Check with machine factory if this is valid machine.
                MadTileEntityFactoryProduct machineInfo = MadTileEntityFactory.getMachineInfo(madTile.getMachineInternalName());
                
                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getClientGUIElement(player.inventory, madTile);
                }
            }
        }

        // Needle sanitizer.
        if (ID == MadFurnaces.SANTITIZER_TILEENTITY.blockID)
        {
            return new SanitizerGUI(player.inventory, (SanitizerEntity) tileEntity);
        }

        // Computer Mainframe.
        if (ID == MadFurnaces.MAINFRAME_TILEENTITY.blockID)
        {
            return new MainframeGUI(player.inventory, (MainframeEntity) tileEntity);
        }

        // Genome Sequencer
        if (ID == MadFurnaces.SEQUENCER_TILEENTITY.blockID)
        {
            return new SequencerGUI(player.inventory, (SequencerEntity) tileEntity);
        }

        // Cryogenic Freezer
        if (ID == MadFurnaces.CRYOFREEZER_TILEENTITY.blockID)
        {
            return new CryofreezerGUI(player.inventory, (CryofreezerEntity) tileEntity);
        }

        // Genome Incubator
        if (ID == MadFurnaces.INCUBATOR_TILEENTITY.blockID)
        {
            return new IncubatorGUI(player.inventory, (IncubatorEntity) tileEntity);
        }

        // Meat Cube
        if (ID == MadFurnaces.MEATCUBE_TILEENTITY.blockID)
        {
            return new MeatcubeGUI(player.inventory, (MeatcubeEntity) tileEntity);
        }

        // Cryogenic Tube
        if (ID == MadFurnaces.CRYOTUBE_TILEENTITY.blockID)
        {
            return new CryotubeGUI(player.inventory, (CryotubeEntity) tileEntity);
        }

        // Thermosonic Bonder
        if (ID == MadFurnaces.THERMOSONIC_TILEENTITY.blockID)
        {
            return new ThermosonicBonderGUI(player.inventory, (ThermosonicBonderEntity) tileEntity);
        }

        // Data Reel Duplicator
        if (ID == MadFurnaces.DATADUPLICATOR_TILEENTITY.blockID)
        {
            return new DataDuplicatorGUI(player.inventory, (DataDuplicatorEntity) tileEntity);
        }

        // Soniclocator Device
        if (ID == MadFurnaces.SONICLOCATOR_TILEENTITY.blockID)
        {
            return new SoniclocatorGUI(player.inventory, (SoniclocatorEntity) tileEntity);
        }
        
        // Clay Furnace
        if (ID == MadFurnaces.CLAYFURNACE_TILEENTITY.blockID)
        {
            return new ClayfurnaceGUI(player.inventory, (ClayfurnaceEntity) tileEntity);
        }
        
        // VOX Box
        if (ID == MadFurnaces.VOXBOX_TILEENTITY.blockID)
        {
            return new VoxBoxGUI(player.inventory, (VoxBoxEntity) tileEntity);
        }
        
        // Magazine Loader
        if (ID == MadFurnaces.MAGLOADER_TILEENTITY.blockID)
        {
            return new MagLoaderGUI(player.inventory, (MagLoaderEntity) tileEntity);
        }
        
        // CnC Machine
        if (ID == MadFurnaces.CNCMACHINE_TILEENTITY.blockID)
        {
            return new CnCMachineGUI(player.inventory, (CnCMachineEntity) tileEntity);
        }
       
        // Default response is to return nothing.
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // Grab the running instance of the block on the server world.
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        // Check if this block is one of ours.
        if (tileEntity instanceof MadTileEntity)
        {
            // Cast the object as MadTE so we can get internal name from it.
            MadTileEntity madTile = (MadTileEntity) tileEntity;
            
            if (madTile != null)
            {
                // Check with machine factory if this is valid machine.
                MadTileEntityFactoryProduct machineInfo = MadTileEntityFactory.getMachineInfo(madTile.getMachineInternalName());
                
                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getServerGUIElement(player.inventory, madTile);
                }
            }
        }

        // Needle sanitizer.
        if (ID == MadFurnaces.SANTITIZER_TILEENTITY.blockID)
        {
            return new SanitizerContainer(player.inventory, (SanitizerEntity) tileEntity);
        }

        // Computer Mainframe.
        if (ID == MadFurnaces.MAINFRAME_TILEENTITY.blockID)
        {
            return new MainframeContainer(player.inventory, (MainframeEntity) tileEntity);
        }

        // Genome Sequencer
        if (ID == MadFurnaces.SEQUENCER_TILEENTITY.blockID)
        {
            return new SequencerContainer(player.inventory, (SequencerEntity) tileEntity);
        }

        // Cryogenic Freezer
        if (ID == MadFurnaces.CRYOFREEZER_TILEENTITY.blockID)
        {
            return new CryofreezerContainer(player.inventory, (CryofreezerEntity) tileEntity);
        }

        // Genome Incubator
        if (ID == MadFurnaces.INCUBATOR_TILEENTITY.blockID)
        {
            return new IncubatorContainer(player.inventory, (IncubatorEntity) tileEntity);
        }

        // Meat Cube
        if (ID == MadFurnaces.MEATCUBE_TILEENTITY.blockID)
        {
            return new MeatcubeContainer(player.inventory, (MeatcubeEntity) tileEntity);
        }

        // Cryogenic Tube
        if (ID == MadFurnaces.CRYOTUBE_TILEENTITY.blockID)
        {
            return new CryotubeContainer(player.inventory, (CryotubeEntity) tileEntity);
        }

        // Thermosonic Bonder
        if (ID == MadFurnaces.THERMOSONIC_TILEENTITY.blockID)
        {
            return new ThermosonicBonderContainer(player.inventory, (ThermosonicBonderEntity) tileEntity);
        }

        // Data Reel Duplicator
        if (ID == MadFurnaces.DATADUPLICATOR_TILEENTITY.blockID)
        {
            return new DataDuplicatorContainer(player.inventory, (DataDuplicatorEntity) tileEntity);
        }

        // Soniclocator Device
        if (ID == MadFurnaces.SONICLOCATOR_TILEENTITY.blockID)
        {
            return new SoniclocatorContainer(player.inventory, (SoniclocatorEntity) tileEntity);
        }
        
        // Clay Furnace
        if (ID == MadFurnaces.CLAYFURNACE_TILEENTITY.blockID)
        {
            return new ClayfurnaceContainer(player.inventory, (ClayfurnaceEntity) tileEntity);
        }
        
        // VOX Box
        if (ID == MadFurnaces.VOXBOX_TILEENTITY.blockID)
        {
            return new VoxBoxContainer(player.inventory, (VoxBoxEntity) tileEntity);
        }
        
        // Magazine Loader
        if (ID == MadFurnaces.MAGLOADER_TILEENTITY.blockID)
        {
            return new MagLoaderContainer(player.inventory, (MagLoaderEntity) tileEntity);
        }
        
        // CnC Machine
        if (ID == MadFurnaces.CNCMACHINE_TILEENTITY.blockID)
        {
            return new CnCMachineContainer(player.inventory, (CnCMachineEntity) tileEntity);
        }
        
        // Default response is to return nothing.
        return null;
    }

}
