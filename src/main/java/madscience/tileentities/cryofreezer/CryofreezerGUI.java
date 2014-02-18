package madscience.tileentities.cryofreezer;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CryofreezerGUI extends GuiContainer
{
    // Texture of the GUI interface that holds all the controls.
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.CRYOFREEZER_INTERNALNAME + ".png");

    private CryofreezerEntity furnaceInventory;

    public CryofreezerGUI(InventoryPlayer par1InventoryPlayer, CryofreezerEntity par2TileEntityFurnace)
    {
        super(new CryofreezerContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
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
        mc.renderEngine.bindTexture(furnaceGuiTextures);
        int screenX = (this.width - this.xSize) / 2;
        int screenY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(screenX, screenY, 0, 0, this.xSize, this.ySize);

        // -----------
        // POWER LEVEL
        // -----------
        int powerRemianingPercentage = this.furnaceInventory.getPowerRemainingScaled(14);
        // Screen Coords: 10x56
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 10, screenY + 56 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.furnaceInventory.getItemCookTimeScaled(16);
        // Screen Coords: 10x14
        // Filler Coords: 176x14
        // Image Size WH: 14x16
        this.drawTexturedModalRect(screenX + 10, screenY + 14, 176, 14, 14, powerCookPercentage + 1);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String s = "     " + MadFurnaces.CRYOFREEZER_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
    }
}
