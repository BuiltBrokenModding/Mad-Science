package madscience.tileentities.mainframe;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MainframeGUI extends GuiContainer
{
    // Texture of the GUI interface that holds all the controls.
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.MAINFRAME_INTERNALNAME + ".png");

    private MainframeEntity furnaceInventory;

    public MainframeGUI(InventoryPlayer par1InventoryPlayer, MainframeEntity par2TileEntityFurnace)
    {
        super(new MainframeContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
    }

    private void displayGauge(int screenX, int screenY, int line, int col, int squaled)
    {
        // Variable to keep track of block texture segments.
        int start = 0;

        // Bind the texture we grabbed so we can use it in rendering.
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

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

            // Draws fluid background ontop of existing gauge lines.
            // Screen Coords: 7x7
            // Filler Coords: 176x14
            // Image Size WH: 16x58
            drawTexturedModelRectFromIcon(screenX + col, screenY + line + 58 - x - start, FluidRegistry.WATER.getStillIcon(), 16, 16 - (16 - x));
            start = start + 16;

            if (x == 0 || squaled == 0)
            {
                break;
            }
        }

        // Re-draws gauge lines ontop of scaled fluid amount to make it look
        // like the fluid is behind the gauge lines.
        mc.renderEngine.bindTexture(furnaceGuiTextures);
        drawTexturedModalRect(screenX + col, screenY + line, 176, 14, 16, 58);
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
        mc.renderEngine.bindTexture(furnaceGuiTextures);
        int screenX = (this.width - this.xSize) / 2;
        int screenY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(screenX, screenY, 0, 0, this.xSize, this.ySize);

        // -----------
        // POWER LEVEL
        // -----------
        int powerRemianingPercentage = this.furnaceInventory.getPowerRemainingScaled(14);
        // Screen Coords: 54x57
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 54, screenY + 57 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage + 2);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.furnaceInventory.getItemCookTimeScaled(65);
        // Screen Coords: 78x34
        // Filler Coords: 176x112
        // Image Size WH: 65x20
        this.drawTexturedModalRect(screenX + 78, screenY + 34, 176, 112, powerCookPercentage + 1, 20);

        // -----------
        // WATER LEVEL
        // -----------
        // Screen Coords: 7x7
        // Filler Coords: 176x14
        // Image Size WH: 16x58
        if (furnaceInventory.getWaterRemainingScaled(58) > 0)
        {
            // Scale up the stored water amount in the block to match the
            // capacity ratio.
            displayGauge(screenX, screenY, 7, 7, furnaceInventory.getWaterRemainingScaled(58));
        }

        // ----------
        // HEAT LEVEL
        // ----------
        int heatLevelPercentage = furnaceInventory.getHeatLevelTimeScaled(40);
        // Screen Coords: 52x15
        // Filler Coords: 176x72
        // Image Size WH: 18x40
        drawTexturedModalRect(screenX + 52, screenY + 15, 176, 72, 18, 40 - heatLevelPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String s = "     " + MadFurnaces.MAINFRAME_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
    }
}
