package madscience.factory.item;

import net.minecraft.util.Icon;

import com.google.gson.annotations.Expose;

public class MadItemRenderPass
{
    /** RGB color that this icon will be rendered as. */
    @Expose
    private int colorRGB;
    
    /** Number representing which render pass this is so order is maintained. */
    @Expose
    private int renderPass;
    
    /** Stored path to icon which will later be used to populate Icon variable. */
    @Expose
    private String iconPath;
    
    /** Holds loaded icon from Minecraft/Forge icon registry. */
    private Icon icon = null;
    
    /** Holds reference to if we have loaded icon from Minecraft/Forge icon registry, prevents and throws error for double loading. */
    private boolean loadedIcon = false;
    
    public MadItemRenderPass(
            int renderPass,
            String iconPath,
            int colorRGB)
    {
        super();
        
        this.renderPass = renderPass;
        this.iconPath = iconPath;
        this.colorRGB = colorRGB;
    }
    
    public int getColorRGB()
    {
        return colorRGB;
    }

    public int getRenderPass()
    {
        return renderPass;
    }

    public String getIconPath()
    {
        return iconPath;
    }

    public Icon getIcon()
    {
        return icon;
    }

    public void setIcon(Icon icon)
    {
        // Prevent loading of icons more than once.
        if (loadedIcon)
        {
            throw new IllegalArgumentException("Unable to load icon '" + this.iconPath + "' because it has already been loaded!");
        }
        
        this.loadedIcon = true;
        this.icon = icon;
    }
}
