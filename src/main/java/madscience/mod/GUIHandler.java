package madscience.mod;


import cpw.mods.fml.common.network.IGuiHandler;
import madscience.factory.TileEntityFactory;
import madscience.product.TileEntityFactoryProduct;
import madscience.tile.TileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class GUIHandler implements IGuiHandler
{
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // Grab a running instance of the block on the server world.
        TileEntity tileEntity = world.getBlockTileEntity( x,
                                                          y,
                                                          z );

        // Check if this block is one of ours.
        if (tileEntity instanceof TileEntityPrefab)
        {
            // Cast the object as MadTE so we can get internal name from it.
            TileEntityPrefab madTile = (TileEntityPrefab) tileEntity;

            if (madTile != null)
            {
                // Check with machine factory if this is valid machine.
                TileEntityFactoryProduct machineInfo =
                        TileEntityFactory.instance().getMachineInfo( madTile.getMachineInternalName() );

                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getClientGUIElement( player.inventory,
                                                            madTile );
                }
            }
        }

        // Default response is to return nothing.
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        // Grab the running instance of the block on the server world.
        TileEntity tileEntity = world.getBlockTileEntity( x,
                                                          y,
                                                          z );

        // Check if this block is one of ours.
        if (tileEntity instanceof TileEntityPrefab)
        {
            // Cast the object as MadTE so we can get internal name from it.
            TileEntityPrefab madTile = (TileEntityPrefab) tileEntity;

            if (madTile != null)
            {
                // Check with machine factory if this is valid machine.
                TileEntityFactoryProduct machineInfo =
                        TileEntityFactory.instance().getMachineInfo( madTile.getMachineInternalName() );

                // Check if ID matches our factory product ID.
                if (ID == machineInfo.getBlockID())
                {
                    return machineInfo.getServerGUIElement( player.inventory,
                                                            madTile );
                }
            }
        }

        // Default response is to return nothing.
        return null;
    }

}
