package madscience.factory.buttons;

import madscience.factory.mod.MadMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MadGUIButtonInvisibleControl extends GuiButton
{
    private static final ResourceLocation sandraBullock = new ResourceLocation(MadMod.ID, "textures/gui/sandraBullock.png");

    private static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0, 1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1, 0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
    }

    public MadGUIButtonInvisibleControl(int par1, int par2, int par3, int par4, int par5)
    {
        super(par1, par2, par3, par4, par5, "");
    }

    /** Draws this button to the screen. */
    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            // Bind texture
            Minecraft.getMinecraft().renderEngine.bindTexture(sandraBullock);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);

            // Render image without regard for powers of 2 (unlike everything else in MC).
            drawTexturedQuadFit(xPosition, yPosition, width, height, zLevel);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }
        }
    }

    /** Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent e). */
    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        return this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
    }
}
