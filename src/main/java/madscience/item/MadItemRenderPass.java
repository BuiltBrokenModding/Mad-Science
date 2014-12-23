package madscience.item;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MadItemRenderPass
{
    /**
     * RGB color that this icon will be rendered as.
     */
    @Expose
    @SerializedName("ColorRGB")
    private int colorRGB;

    /**
     * Number representing which render pass this is so order is maintained.
     */
    @Expose
    @SerializedName("RenderPass")
    private int renderPass;

    /**
     * Stored path to icon which will later be used to populate Icon variable.
     */
    @Expose
    @SerializedName("IconPath")
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
