package madscience.factory.item;

import com.google.gson.annotations.Expose;

public class MadItemRenderPass
{
    @Expose
    private int colorRGB;
    
    @Expose
    private int renderPass;
    
    @Expose
    private String iconPath;
    
    public MadItemRenderPass(int renderPass, String iconPath, int colorRGB)
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
