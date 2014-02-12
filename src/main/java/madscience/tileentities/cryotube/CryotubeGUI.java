package madscience.tileentities.cryotube;

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
public class CryotubeGUI extends GuiContainer
{
    // Texture of the GUI interface that holds all the controls.
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".png");

    // Placed tile entity in the game world.
    private CryotubeEntity tileEntity;

    public CryotubeGUI(InventoryPlayer par1InventoryPlayer, CryotubeEntity par2TileEntityFurnace)
    {
        super(new CryotubeContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.tileEntity = par2TileEntityFurnace;
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

        // --------------
        // ENERGY STORAGE
        // --------------
        int powerRemianingPercentage = this.tileEntity.getPowerRemainingScaled(32);
        //MadScience.logger.info("POWER: " + powerRemianingPercentage);
        // Screen Coords: 112x17
        // Filler Coords: 176x56
        // Image Size WH: 18x32
        this.drawTexturedModalRect(screenX + 112, screenY + 17, 176, 56, 18, 32 - powerRemianingPercentage);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.tileEntity.getHatchTimeScaled(26);
        // Screen Coords: 35x37
        // Filler Coords: 176x0
        // Image Size WH: 26x10
        this.drawTexturedModalRect(screenX + 35, screenY + 37, 176, 0, powerCookPercentage + 1, 10);

        // --------------
        // SUBJECT HEALTH
        // --------------
        int subjectHealthPercentage = this.tileEntity.getSubjectHealthScaled(46);
        // Screen Coords: 68x17
        // Filler Coords: 176x10
        // Image Size WH: 11x46
        this.drawTexturedModalRect(screenX + 68, screenY + 17, 176, 10, 11, 46 - subjectHealthPercentage);

        // ---------------
        // NEURAL ACTIVITY
        // ---------------
        int neuralActivityPercentage = this.tileEntity.getNeuralActivityScaled(46);
        // Screen Coords: 91x17
        // Filler Coords: 187x10
        // Image Size WH: 11x46
        this.drawTexturedModalRect(screenX + 91, screenY + 17, 187, 10, 11, 46 - neuralActivityPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // Name displayed above the GUI, typically name of the furnace.
        String s = MadFurnaces.CRYOTUBE_DISPLAYNAME;
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
    }
}
