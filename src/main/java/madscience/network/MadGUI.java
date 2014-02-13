package madscience.network;

import madscience.MadFurnaces;
import madscience.tileentities.cryofreezer.CryofreezerContainer;
import madscience.tileentities.cryofreezer.CryofreezerEntity;
import madscience.tileentities.cryofreezer.CryofreezerGUI;
import madscience.tileentities.cryotube.CryotubeContainer;
import madscience.tileentities.cryotube.CryotubeEntity;
import madscience.tileentities.cryotube.CryotubeGUI;
import madscience.tileentities.dataduplicator.DataDuplicatorContainer;
import madscience.tileentities.dataduplicator.DataDuplicatorEntity;
import madscience.tileentities.dataduplicator.DataDuplicatorGUI;
import madscience.tileentities.dnaextractor.DNAExtractorContainer;
import madscience.tileentities.dnaextractor.DNAExtractorEntity;
import madscience.tileentities.dnaextractor.DNAExtractorGUI;
import madscience.tileentities.incubator.IncubatorContainer;
import madscience.tileentities.incubator.IncubatorEntity;
import madscience.tileentities.incubator.IncubatorGUI;
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
import madscience.tileentities.thermosonicbonder.ThermosonicBonderContainer;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderGUI;
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
        TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

        // DNA extractor.
        if (ID == MadFurnaces.DNAEXTRACTOR_TILEENTITY.blockID)
        {
            return new DNAExtractorGUI(player.inventory, (DNAExtractorEntity) tile_entity);
        }

        // Needle sanitizer.
        if (ID == MadFurnaces.SANTITIZER_TILEENTITY.blockID)
        {
            return new SanitizerGUI(player.inventory, (SanitizerEntity) tile_entity);
        }

        // Computer Mainframe.
        if (ID == MadFurnaces.MAINFRAME_TILEENTITY.blockID)
        {
            return new MainframeGUI(player.inventory, (MainframeEntity) tile_entity);
        }

        // Genome Sequencer
        if (ID == MadFurnaces.SEQUENCER_TILEENTITY.blockID)
        {
            return new SequencerGUI(player.inventory, (SequencerEntity) tile_entity);
        }

        // Cryogenic Freezer
        if (ID == MadFurnaces.CRYOFREEZER_TILEENTITY.blockID)
        {
            return new CryofreezerGUI(player.inventory, (CryofreezerEntity) tile_entity);
        }

        // Genome Incubator
        if (ID == MadFurnaces.INCUBATOR_TILEENTITY.blockID)
        {
            return new IncubatorGUI(player.inventory, (IncubatorEntity) tile_entity);
        }

        // Meat Cube
        if (ID == MadFurnaces.MEATCUBE_TILEENTITY.blockID)
        {
            return new MeatcubeGUI(player.inventory, (MeatcubeEntity) tile_entity);
        }
        
        // Cryogenic Tube
        if (ID == MadFurnaces.CRYOTUBE_TILEENTITY.blockID)
        {
            return new CryotubeGUI(player.inventory, (CryotubeEntity) tile_entity);
        }
        
        // Thermosonic Bonder
        if (ID == MadFurnaces.THERMOSONIC_TILEENTITY.blockID)
        {
            return new ThermosonicBonderGUI(player.inventory, (ThermosonicBonderEntity) tile_entity);
        }
        
        // Data Reel Duplicator
        if (ID == MadFurnaces.DATADUPLICATOR_TILEENTITY.blockID)
        {
            return new DataDuplicatorGUI(player.inventory, (DataDuplicatorEntity) tile_entity);
        }

        // Default response is to return nothing.
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // Grab the running instance of the block on the server world.
        TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

        // DNA extractor.
        if (ID == MadFurnaces.DNAEXTRACTOR_TILEENTITY.blockID)
        {
            return new DNAExtractorContainer(player.inventory, (DNAExtractorEntity) tile_entity);
        }

        // Needle sanitizer.
        if (ID == MadFurnaces.SANTITIZER_TILEENTITY.blockID)
        {
            return new SanitizerContainer(player.inventory, (SanitizerEntity) tile_entity);
        }

        // Computer Mainframe.
        if (ID == MadFurnaces.MAINFRAME_TILEENTITY.blockID)
        {
            return new MainframeContainer(player.inventory, (MainframeEntity) tile_entity);
        }

        // Genome Sequencer
        if (ID == MadFurnaces.SEQUENCER_TILEENTITY.blockID)
        {
            return new SequencerContainer(player.inventory, (SequencerEntity) tile_entity);
        }

        // Cryogenic Freezer
        if (ID == MadFurnaces.CRYOFREEZER_TILEENTITY.blockID)
        {
            return new CryofreezerContainer(player.inventory, (CryofreezerEntity) tile_entity);
        }

        // Genome Incubator
        if (ID == MadFurnaces.INCUBATOR_TILEENTITY.blockID)
        {
            return new IncubatorContainer(player.inventory, (IncubatorEntity) tile_entity);
        }

        // Meat Cube
        if (ID == MadFurnaces.MEATCUBE_TILEENTITY.blockID)
        {
            return new MeatcubeContainer(player.inventory, (MeatcubeEntity) tile_entity);
        }
        
        // Cryogenic Tube
        if (ID == MadFurnaces.CRYOTUBE_TILEENTITY.blockID)
        {
            return new CryotubeContainer(player.inventory, (CryotubeEntity) tile_entity);
        }
        
        // Thermosonic Bonder
        if (ID == MadFurnaces.THERMOSONIC_TILEENTITY.blockID)
        {
            return new ThermosonicBonderContainer(player.inventory, (ThermosonicBonderEntity) tile_entity);
        }
        
        // Data Reel Duplicator
        if (ID == MadFurnaces.DATADUPLICATOR_TILEENTITY.blockID)
        {
            return new DataDuplicatorContainer(player.inventory, (DataDuplicatorEntity) tile_entity);
        }

        // Default response is to return nothing.
        return null;
    }

}