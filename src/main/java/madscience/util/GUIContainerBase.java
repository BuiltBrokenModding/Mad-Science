package madscience.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import universalelectricity.api.CompatibilityType;
import universalelectricity.api.energy.UnitDisplay;
import universalelectricity.api.energy.UnitDisplay.Unit;
import universalelectricity.api.vector.Vector2;

public class GUIContainerBase extends GuiContainer
{
    public ResourceLocation TEXTURE;

    public String SANDRA_YOUTUBE = "https://www.youtube.com/watch?feature=player_detailpage&v=ItjKGURohzU#t=76";

    public String tooltip = "";
    protected HashMap<Region2, String> tooltips = new HashMap<Region2, String>();

    protected int screenX = (this.width - this.xSize) / 2;
    protected int screenY = (this.height - this.ySize) / 2;
    private float lastChangeFrameTime;

    public final int PROMPT_REPLY_ACTION = 0;
    public URI displayedURI = null;

    public GUIContainerBase(Container container)
    {
        super(container);
    }

    @Override
    public void confirmClicked(boolean result, int action)
    {
        // Returns MC internal click codes for the URL response.
        if (action == PROMPT_REPLY_ACTION && result)
        {
            openURI(this.displayedURI);
            this.displayedURI = null;
        }

        this.mc.displayGuiScreen(this);
    }

    private void openURI(URI uri)
    {
        try
        {
            // Opens default browser on system with URL.
            Desktop.getDesktop().browse(uri);
        }
        catch (IOException e)
        {
            // Nothing to see here.
        }
    }

    public void passClick(URI webLocation)
    {
        // Rude not to ask if user has chat link prompts disabled.
        if (Minecraft.getMinecraft().gameSettings.chatLinksPrompt)
        {
            this.displayedURI = webLocation;
            this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.displayedURI.toString(), PROMPT_REPLY_ACTION, false));
        }
        else
        {
            // Open the URL normally without asking anything.
            openURI(webLocation);
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        Iterator<Entry<Region2, String>> it = this.tooltips.entrySet().iterator();

        while (it.hasNext())
        {
            Entry<Region2, String> entry = it.next();

            if (entry.getKey().isIn(new Vector2(mouseX - this.guiLeft, mouseY - this.guiTop)))
            {
                this.tooltip = entry.getValue();
                break;
            }
        }

        if (this.tooltip != null && this.tooltip != "")
        {
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop, MadUtils.splitStringPerWord(this.tooltip, 5).toArray(new String[]
            {}));
        }

        this.tooltip = "";

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        this.screenX = (this.width - this.xSize) / 2;
        this.screenY = (this.height - this.ySize) / 2;

        this.mc.renderEngine.bindTexture(this.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(this.screenX, this.screenY, 0, 0, this.xSize, this.ySize);
    }

    protected void drawBulb(int x, int y, boolean isOn)
    {
        this.mc.renderEngine.bindTexture(this.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (isOn)
        {
            this.drawTexturedModalRect(this.screenX + x, this.screenY + y, 161, 0, 6, 6);

        }
        else
        {
            this.drawTexturedModalRect(this.screenX + x, this.screenY + y, 161, 4, 6, 6);
        }
    }

    protected void drawSlot(int x, int y, ItemStack itemStack)
    {
        this.mc.renderEngine.bindTexture(this.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(this.screenX + x, this.screenY + y, 0, 0, 18, 18);

        this.drawItemStack(itemStack, this.screenX + x, this.screenY + y);
    }

    protected void drawItemStack(ItemStack itemStack, int x, int y)
    {
        x += 1;
        y += 1;
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);

        // drawTexturedModelRectFromIcon
        // GL11.glEnable(GL11.GL_BLEND);
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack, x, y);
        // GL11.glDisable(GL11.GL_BLEND);
    }

    protected void drawTextWithTooltip(String textName, String format, int x, int y, int mouseX, int mouseY)
    {
        this.drawTextWithTooltip(textName, format, x, y, mouseX, mouseY, 4210752);
    }

    protected void drawTextWithTooltip(String textName, String format, int x, int y, int mouseX, int mouseY, int color)
    {
        String name = I18n.getString("gui." + textName + ".name");
        String text = format.replaceAll("%1", name);
        this.fontRenderer.drawString(text, x, y, color);

        String tooltip = I18n.getString("gui." + textName + ".tooltip");

        if (tooltip != null && tooltip != "")
        {
            if (this.isPointInRegion(x, y, (int) (text.length() * 4.8), 12, mouseX, mouseY))
            {
                this.tooltip = tooltip;
            }
        }
    }

    protected void drawTextWithTooltip(String textName, int x, int y, int mouseX, int mouseY)
    {
        this.drawTextWithTooltip(textName, "%1", x, y, mouseX, mouseY);
    }

    public void drawTooltip(int x, int y, String... toolTips)
    {
        if (!GuiScreen.isShiftKeyDown())
        {
            if (toolTips != null)
            {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glDisable(GL11.GL_DEPTH_TEST);

                int var5 = 0;
                int var6;
                int var7;

                for (var6 = 0; var6 < toolTips.length; ++var6)
                {
                    var7 = this.fontRenderer.getStringWidth(toolTips[var6]);

                    if (var7 > var5)
                    {
                        var5 = var7;
                    }
                }

                var6 = x + 12;
                var7 = y - 12;

                int var9 = 8;

                if (toolTips.length > 1)
                {
                    var9 += 2 + (toolTips.length - 1) * 10;
                }

                if (this.guiTop + var7 + var9 + 6 > this.height)
                {
                    var7 = this.height - var9 - this.guiTop - 6;
                }

                this.zLevel = 300;
                int var10 = -267386864;
                this.drawGradientRect(var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10);
                this.drawGradientRect(var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10);
                int var11 = 1347420415;
                int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
                this.drawGradientRect(var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11);
                this.drawGradientRect(var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

                for (int var13 = 0; var13 < toolTips.length; ++var13)
                {
                    String var14 = toolTips[var13];

                    this.fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                    var7 += 10;
                }

                this.zLevel = 0;

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
        }
    }
}
