package madscience.tile.mainframe;

import java.awt.Desktop;
import java.net.URI;

import madscience.MadConfig;
import madscience.MadMachines;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInvisibleControl;
import madscience.factory.tileentity.MadGUITemplate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MainframeGUI extends MadGUITemplate
{
    private MainframeEntity ENTITY;

    public MainframeGUI(InventoryPlayer par1InventoryPlayer, MainframeEntity par2TileEntityFurnace)
    {
        super(new MainframeContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.ENTITY = par2TileEntityFurnace;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadMachines.MAINFRAME_INTERNALNAME + ".png");
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
        mc.renderEngine.bindTexture(TEXTURE);
        drawTexturedModalRect(screenX + col, screenY + line, 176, 14, 16, 58);
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);

        // -----------
        // POWER LEVEL
        // -----------
        int powerRemianingPercentage = this.ENTITY.getPowerRemainingScaled(14);
        // Screen Coords: 54x57
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 54, screenY + 57 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage + 2);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.ENTITY.getItemCookTimeScaled(65);
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
        if (ENTITY.getWaterRemainingScaled(58) > 0)
        {
            // Scale up the stored water amount in the block to match the
            // capacity ratio.
            displayGauge(screenX, screenY, 7, 7, ENTITY.getWaterRemainingScaled(58));
        }

        // ----------
        // HEAT LEVEL
        // ----------
        int heatLevelPercentage = ENTITY.getHeatLevelTimeScaled(40);
        // Screen Coords: 52x15
        // Filler Coords: 176x72
        // Image Size WH: 18x40
        drawTexturedModalRect(screenX + 52, screenY + 15, 176, 72, 18, 40 - heatLevelPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String s = "     " + MadMachines.MAINFRAME_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
        
        // Power level
        if (this.isPointInRegion(54, 57, 14, 14, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
        }
        
        // Cooking progress
        if (this.isPointInRegion(78, 34, 65, 20, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.currentItemCookingValue) + "/" + String.valueOf(this.ENTITY.currentItemCookingMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Progress " + String.valueOf(this.ENTITY.getItemCookTimeScaled(100)) + " %",
                    powerLevelLiteral);
        }
        
        // Heat level
        if (this.isPointInRegion(52, 15, 18, 40, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.currentHeatValue) + "/" + String.valueOf(this.ENTITY.currentHeatMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Heat " + String.valueOf(this.ENTITY.getHeatLevelTimeScaled(100)) + " %",
                    powerLevelLiteral);
        }

        // Input water bucket.
        if (this.isPointInRegion(27, 27, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input water bucket");
        }
        
        // Input genome 1
        if (this.isPointInRegion(71, 17, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(1) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input sequenced genome");
        }
        
        // Input genome 2
        if (this.isPointInRegion(96, 17, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(2) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input sequenced genome");
        }
        
        // Input empty data reel
        if (this.isPointInRegion(115, 55, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(3) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input empty data reel");
        }
        
        // Water level
        if (this.isPointInRegion(7, 7, 16, 58, mouseX, mouseY) && this.ENTITY.internalWaterTank.getFluid() != null)
        {
            if (this.ENTITY.internalWaterTank.getFluid() != null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, this.ENTITY.internalWaterTank.getFluid().getFluid().getLocalizedName(), this.ENTITY.internalWaterTank.getFluid().amount + " L");
        }
        
        // Help link
        if (this.isPointInRegion(166, 4, 6, 5, mouseX, mouseY))
        {
            if (GuiScreen.isCtrlKeyDown())
            {
                // The Net Reference - Easter Egg 1
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Sandra Bullock Mode");
            }
            else
            {
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Help");
            }
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        int posX = (this.width - 6) / 2;
        int posY = (this.height - 5) / 2;
        
        // make buttons
        buttonList.clear();
        buttonList.add(new MadGUIButtonInvisibleControl(1, posX + 81, posY - 76, 6, 5));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        
        if (button.id == 1 && Desktop.isDesktopSupported())
        {
            if (GuiScreen.isCtrlKeyDown() && GuiScreen.isShiftKeyDown())
            {
                try
                {
                    this.passClick(new URI(this.SANDRA_YOUTUBE));
                }
                catch (Exception err)
                {
                    MadScience.logger.info("Unable to open sandra youtube easter egg link in default browser.");
                }
            }
            else
            {
                try
                {
                    this.passClick(new URI(MadConfig.MAINFRAME_HELP));
                }
                catch (Exception err)
                {
                    MadScience.logger.info("Unable to open wiki link in default browser.");
                }
            }
        }
    }
}
