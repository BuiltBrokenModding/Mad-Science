package madapi.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadFluidFactoryProductData
{
    /** Internal name of the fluid that will be used on bucket and fluid itself. */
    @Expose
    @SerializedName("FluidName")
    private String fluidName;
    
    /** Determines how much this fluid slows down entities. Higher values make the player move slower through the fluid. */
    @Expose 
    @SerializedName("Density")
    private int density;
    
    /** Determines how fast this fluid will flow over other blocks and down channels. */
    @Expose
    @SerializedName("Viscosity")
    private int viscosity;
    
    /** Determines how well light can pass through this fluid. */
    @Expose
    @SerializedName("Luminosity")
    private int luminosity;
    
    /** Path to texture which will be loaded and become out still fluid. */
    @Expose
    @SerializedName("IconStillPath")
    private String iconStillPath;
    
    /** Path to texture which will be loaded and become side texture for fluid. */
    @Expose
    @SerializedName("IconFlowingPath")
    private String iconFlowingPath;
    
    /** Path to texture which will be used to represent a filled bucket of the given fluid. */
    @Expose
    @SerializedName("IconFluidContainerPath")
    private String iconFluidContainerPath;
    
    /** Determines if this fluid requires a bucket to be registered to pick it up into a container. */
    @Expose
    @SerializedName("HasFluidContainerItem")
    private boolean hasFluidContainerItem;
    
    /** Determines if the fluid source block will be shown in the creative tab for the mod. */
    @Expose
    @SerializedName("ShowFluidBlockInCreativeTab")
    private boolean showFluidBlockInCreativeTab;
    
    /** Fluid ID which is also the same ID used for block flowing classic. */
    private int fluidID;
    
    /** Fluid container item ID, this item has special event binded to it to pickup this fluid. */
    private int fluidContainerID;

    public MadFluidFactoryProductData(
            String fluidName,
            int density,
            int viscosity,
            int luminosity,
            String iconStillPath,
            String iconFlowingPath,
            String iconFluidContainerPath,
            boolean hasFluidContainerItem)
    {
        super();
        
        this.fluidName = fluidName;
        this.density = density;
        this.viscosity = viscosity;
        this.luminosity = luminosity;
        this.iconStillPath = iconStillPath;
        this.iconFlowingPath = iconFlowingPath;
        this.iconFluidContainerPath = iconFluidContainerPath;
        this.hasFluidContainerItem = hasFluidContainerItem;
    }

    public String getFluidName()
    {
        return fluidName;
    }

    public String getFluidContainerName()
    {
        return fluidName + "Bucket";
    }

    public int getFluidContainerID()
    {
        return fluidContainerID;
    }

    public int getDensity()
    {
        return density;
    }

    public int getViscosity()
    {
        return viscosity;
    }

    public int getLuminosity()
    {
        return luminosity;
    }

    public int getFluidID()
    {
        return fluidID;
    }

    public void setFluidID(int defaultFluidID)
    {
        this.fluidID = defaultFluidID;
    }

    public void setFluidContainerID(int defaultFluidContainerID)
    {
        this.fluidContainerID = defaultFluidContainerID;
    }

    public boolean hasFluidContainerItem()
    {
        return hasFluidContainerItem;
    }

    public String getIconStillPath()
    {
        return iconStillPath;
    }

    public String getIconFlowingPath()
    {
        return iconFlowingPath;
    }

    public String getIconFluidContainerPath()
    {
        return iconFluidContainerPath;
    }

    public boolean getShowFluidBlockInCreativeTab()
    {
        return showFluidBlockInCreativeTab;
    }
}
