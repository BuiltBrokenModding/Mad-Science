package madscience.gui;

import madscience.MadFurnaces;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.tile.SanitizerEntity;
import madscience.tile.clayfurnace.ClayfurnaceContainer;
import madscience.tile.clayfurnace.ClayfurnaceEntity;
import madscience.tile.clayfurnace.ClayfurnaceGUI;
import madscience.tile.cncmachine.CnCMachineContainer;
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineGUI;
import madscience.tile.cryofreezer.CryofreezerContainer;
import madscience.tile.cryofreezer.CryofreezerEntity;
import madscience.tile.cryofreezer.CryofreezerGUI;
import madscience.tile.cryotube.CryotubeContainer;
import madscience.tile.cryotube.CryotubeEntity;
import madscience.tile.cryotube.CryotubeGUI;
import madscience.tile.dataduplicator.DataDuplicatorContainer;
import madscience.tile.dataduplicator.DataDuplicatorEntity;
import madscience.tile.dataduplicator.DataDuplicatorGUI;
import madscience.tile.incubator.IncubatorContainer;
import madscience.tile.incubator.IncubatorEntity;
import madscience.tile.incubator.IncubatorGUI;
import madscience.tile.magloader.MagLoaderContainer;
import madscience.tile.magloader.MagLoaderEntity;
import madscience.tile.magloader.MagLoaderGUI;
import madscience.tile.mainframe.MainframeContainer;
import madscience.tile.mainframe.MainframeEntity;
import madscience.tile.mainframe.MainframeGUI;
import madscience.tile.meatcube.MeatcubeContainer;
import madscience.tile.meatcube.MeatcubeEntity;
import madscience.tile.meatcube.MeatcubeGUI;
import madscience.tile.sequencer.SequencerContainer;
import madscience.tile.sequencer.SequencerEntity;
import madscience.tile.sequencer.SequencerGUI;
import madscience.tile.soniclocator.SoniclocatorContainer;
import madscience.tile.soniclocator.SoniclocatorEntity;
import madscience.tile.soniclocator.SoniclocatorGUI;
import madscience.tile.thermosonicbonder.ThermosonicBonderContainer;
import madscience.tile.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tile.thermosonicbonder.ThermosonicBonderGUI;
import madscience.tile.voxbox.VoxBoxContainer;
import madscience.tile.voxbox.VoxBoxEntity;
import madscience.tile.voxbox.VoxBoxGUI;
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
        if (tileEntity instanceof MadTileEntityPrefab)
        {
            // Cast the object as MadTE so we can get internal name from it.
            MadTileEntityPrefab madTile = (MadTileEntityPrefab) tileEntity;
            
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
        if (tileEntity instanceof MadTileEntityPrefab)
        {
            // Cast the object as MadTE so we can get internal name from it.
            MadTileEntityPrefab madTile = (MadTileEntityPrefab) tileEntity;
            
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
