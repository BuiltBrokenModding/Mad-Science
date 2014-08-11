package madscience.factory.fluid.prefab;

import madscience.factory.fluid.template.MadFluidBlockTemplate;
import madscience.factory.fluid.template.MadFluidBucketTemplate;
import madscience.factory.fluid.template.MadFluidTemplate;
import net.minecraftforge.fluids.Fluid;

public class MadFluidFactoryProduct
{
    /** Serialized data that is loaded from JSON loader and also saved. */
    private MadFluidFactoryProductData data = null;
    
    /** Instance of fluid for fluid registry. */
    private MadFluidTemplate fluidTemplate = null;
    
    /** Instance of block fluid classic for fluid source block. */
    private MadFluidBlockTemplate blockFluidTemplate = null;
    
    /** Instance of item fluid container (bucket by default) capable of holding some amount of fluid.
     *  The entire Minecraft/Forge way of hooking and controlling this is via the EVENT_BUS.  */
    private MadFluidBucketTemplate bucketTemplate = null;
    
    public MadFluidFactoryProduct(MadFluidFactoryProductData blockData)
    {
        // Still's ID must be 1 above Flowing.
        fluidTemplate = new MadFluidTemplate(this);

        // Create the block of flowing liquid only after registering it with the system.
        blockFluidTemplate =  new MadFluidBlockTemplate(this);

        // Container for our fluid.
        bucketTemplate = new MadFluidBucketTemplate(this);
    }

    public String getFluidName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public MadFluidBlockTemplate getFluidBlock()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public MadFluidFactoryProductData getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getDensity()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getViscosity()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getLuminosity()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getFluidID()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public Fluid getFluid()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getFluidContainerID()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getFluidContainerName()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
