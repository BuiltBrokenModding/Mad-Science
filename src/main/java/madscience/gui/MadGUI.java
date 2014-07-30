package madscience.gui;

import madscience.MadFurnaces;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.tile.SoniclocatorEntity;
import madscience.tile.cncmachine.CnCMachineContainer;
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineGUI;
import madscience.tile.magloader.MagLoaderContainer;
import madscience.tile.magloader.MagLoaderEntity;
import madscience.tile.magloader.MagLoaderGUI;
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
                MadTileEntityFactoryProduct machineInfo = MadTileEntityFactory.instance().getMachineInfo(madTile.getMachineInternalName());
                
                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getClientGUIElement(player.inventory, madTile);
                }
            }
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
                MadTileEntityFactoryProduct machineInfo = MadTileEntityFactory.instance().getMachineInfo(madTile.getMachineInternalName());
                
                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getServerGUIElement(player.inventory, madTile);
                }
            }
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
