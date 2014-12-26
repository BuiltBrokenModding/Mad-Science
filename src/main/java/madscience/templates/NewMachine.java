package madscience.templates;


import madscience.product.TileEntityFactoryProduct;
import madscience.tile.TileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class NewMachine extends TileEntityPrefab
{

    public NewMachine()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public NewMachine(TileEntityFactoryProduct registeredMachine)
    {
        super( registeredMachine );
        // TODO Auto-generated constructor stub
    }

    public NewMachine(String machineName)
    {
        super( machineName );
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean canSmelt()
    {
        // TODO Auto-generated method stub
        return super.canSmelt();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        // TODO Auto-generated method stub
        super.readFromNBT( nbt );
    }

    @Override
    public void smeltItem()
    {
        // TODO Auto-generated method stub
        super.smeltItem();
    }

    @Override
    public void updateAnimation()
    {
        // TODO Auto-generated method stub
        super.updateAnimation();
    }

    @Override
    public void updateEntity()
    {
        // TODO Auto-generated method stub
        super.updateEntity();
    }

    @Override
    public void updateSound()
    {
        // TODO Auto-generated method stub
        super.updateSound();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        // TODO Auto-generated method stub
        super.writeToNBT( nbt );
    }

    @Override
    public void initiate()
    {
        // TODO Auto-generated method stub
        super.initiate();
    }

    @Override
    public void onBlockRightClick(World world, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        // TODO Auto-generated method stub
        super.onBlockRightClick( world,
                                 x,
                                 y,
                                 z,
                                 par5EntityPlayer );
    }

    @Override
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        // TODO Auto-generated method stub
        super.onBlockLeftClick( world,
                                x,
                                y,
                                z,
                                player );
    }


}
