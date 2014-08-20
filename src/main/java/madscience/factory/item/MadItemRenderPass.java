package madscience.factory.item;

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
}
