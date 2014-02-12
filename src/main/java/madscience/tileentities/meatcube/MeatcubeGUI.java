package madscience.tileentities.meatcube;

import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadMobs;
import madscience.MadScience;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MeatcubeGUI extends GuiContainer
{
    // Texture of the GUI interface that holds all the controls.
    private static final ResourceLocation meatCubeGUITexture = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.MEATCUBE_INTERNALNAME + ".png");

    private MeatcubeEntity meatCubeTileEntity;

    public MeatcubeGUI(InventoryPlayer thePlayer, MeatcubeEntity meatCubeEntity)
    {
        super(new MeatcubeContainer(thePlayer, meatCubeEntity));
        this.meatCubeTileEntity = meatCubeEntity;
    }

    private void displayGauge(int screenX, int screenY, int line, int col, int squaled)
    {
        // Variable to keep track of block texture segments.
        int start = 0;

        // Grab the icon of the liquid by looking at fluid properties in
        // internal tank.
        Icon liquidIcon = MadFluids.LIQUIDDNA_MUTANT.getStillIcon();

        // Bind the texture we grabbed so we can use it in rendering.
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        // We can only display the water icon if it is not null.
        if (liquidIcon == null)
        {
            return;
        }

        while (true)
        {
            int x;
            if (squaled > 16)
            {
                x = 16;
                squaled -= 16;
            }
            else
            {
                x = squaled;
                squaled = 0;
            }

            // ------------
            // PROGRESS BAR
            // ------------
            // Screen Coords: 67x18
            // Filler Coords: 176x0
            // Image Size WH: 16x58
            drawTexturedModelRectFromIcon(screenX + col, screenY + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
            start = start + 16;

            if (x == 0 || squaled == 0)
            {
                break;
            }
        }

        // Re-draws gauge lines ontop of scaled fluid amount to make it look
        // like the fluid is behind the gauge lines.
        mc.renderEngine.bindTexture(meatCubeGUITexture);
        drawTexturedModalRect(screenX + col, screenY + line, 176, 0, 16, 58);
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        // x of part to be drawn over(top left corner)
        // y of part to be drawn over
        // x to draw over
        // y to draw over
        // width and height of the overlaid image to be drawn

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        mc.renderEngine.bindTexture(meatCubeGUITexture);
        int screenX = (this.width - this.xSize) / 2;
        int screenY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(screenX, screenY, 0, 0, this.xSize, this.ySize);

        // ------------
        // PROGRESS BAR
        // ------------
        // Screen Coords: 67x18
        // Filler Coords: 176x0
        // Image Size WH: 16x58
        displayGauge(screenX, screenY, 18, 67, meatCubeTileEntity.getWaterRemainingScaled(58));

    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String s = MadFurnaces.MEATCUBE_DISPLAYNAME;
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
    }
}
