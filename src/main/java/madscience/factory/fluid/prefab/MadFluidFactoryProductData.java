package madscience.factory.fluid.prefab;

import com.google.gson.annotations.Expose;

public class MadFluidFactoryProductData
{
    /** Internal name of the fluid that will be used on bucket and fluid itself. */
    @Expose
    private String fluidName;
    
    /** Determines how much this fluid slows down entities. Higher values make the player move slower through the fluid. */
    @Expose 
    private int fluidDensity;
    
    /** Determines how fast this fluid will flow over other blocks and down channels. */
    @Expose
    private int fluidViscosity;
    
    /** Determines how well light can pass through this fluid. */
    @Expose
    private int fluidLuminosity;
}
