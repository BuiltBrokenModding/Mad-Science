package madscience.tileentities.dnaextractor;

import madscience.MadFluids;
import madscience.MadFurnaces;
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
public class DNAExtractorGUI extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".png");

    private DNAExtractorEntity furnaceInventory;

    public DNAExtractorGUI(InventoryPlayer par1InventoryPlayer, DNAExtractorEntity par2TileEntityFurnace)
    {
        super(new DNAExtractorContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
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
            // Screen Coords: 131x19
            // Filler Coords: 176x31
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
        mc.renderEngine.bindTexture(furnaceGuiTextures);
        drawTexturedModalRect(screenX + col, screenY + line, 176, 31, 16, 58);
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(furnaceGuiTextures);
        int screenX = (this.width - this.xSize) / 2;
        int screenY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(screenX, screenY, 0, 0, this.xSize, this.ySize);

        // -----------
        // POWER LEVEL
        // -----------
        int powerRemianingPercentage = this.furnaceInventory.getPowerRemainingScaled(14);
        // Screen Coords: 10x49
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 10, screenY + 49 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage + 2);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.furnaceInventory.getItemCookTimeScaled(31);
        // Screen Coords: 32x31
        // Filler Coords: 176x14
        // Image Size WH: 31x17
        this.drawTexturedModalRect(screenX + 32, screenY + 31, 176, 14, powerCookPercentage + 1, 17);

        // ------------
        // PROGRESS BAR
        // ------------
        // Screen Coords: 131x19
        // Filler Coords: 176x31
        // Image Size WH: 16x58
        displayGauge(screenX, screenY, 19, 131, this.furnaceInventory.getWaterRemainingScaled(58));
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // Name displayed above the GUI, typically name of the furnace.
        String s = MadFurnaces.DNAEXTRACTOR_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
    }
}
