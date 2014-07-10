package madscience.tileentities.cncmachine;

import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInvisibleControl;
import madscience.factory.tileentity.MadGUITemplate;
import madscience.util.MadUtils;
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
public class CnCMachineGUI extends MadGUITemplate
{
    private CnCMachineEntity ENTITY;

    public CnCMachineGUI(InventoryPlayer playerInventory, CnCMachineEntity tileEntity)
    {
        super(new CnCMachineContainer(playerInventory, tileEntity));
        this.ENTITY = tileEntity;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.CNCMACHINE_INTERNALNAME + ".png");
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
                    this.passClick(new URI(MadConfig.CNCMACHINE_HELP));
                }
                catch (Exception err)
                {
                    MadScience.logger.info("Unable to open wiki link in default browser.");
                }
            }
        }
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
            drawTexturedModelRectFromIcon(screenX + col, screenY + line + 58 - x - start, FluidRegistry.WATER.getStillIcon(), 16, 16 - (16 - x));
            start = start + 16;

            if (x == 0 || squaled == 0)
            {
                break;
            }
        }

        // Re-draws gauge lines ontop of scaled fluid amount.
        mc.renderEngine.bindTexture(TEXTURE);
        drawTexturedModalRect(screenX + col, screenY + line, 176, 28, 16, 58);
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
        // Screen Coords: 68x62
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 68, screenY + 62 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage);

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.ENTITY.getItemCookTimeScaled(31);
        // Screen Coords: 89x45
        // Filler Coords: 176x14
        // Image Size WH: 31x14
        this.drawTexturedModalRect(screenX + 89, screenY + 45, 176, 14, powerCookPercentage + 1, 14);

        // -----------
        // WATER GUAGE
        // -----------
        // Screen Coords: 8x9
        // Filler Coords: 176x28
        // Image Size WH: 16x58
        if (ENTITY.getWaterRemainingScaled(58) > 0)
        {
            // Scale up the stored water amount.
            displayGauge(screenX, screenY, 9, 8, ENTITY.getWaterRemainingScaled(58));
        }
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String guiTitleLabel = "     " + MadFurnaces.CNCMACHINE_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(guiTitleLabel, this.xSize / 2 - this.fontRenderer.getStringWidth(guiTitleLabel) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String guiInventoryLabel = I18n.getString("container.inventory");
        this.fontRenderer.drawString(guiInventoryLabel, 8, this.ySize - 96 + 2, 4210752);

        // Text that tells if your written book is valid or not.
        String guiBookDecoder = "";
        if (ENTITY != null)
        {
            if (ENTITY.isPowered() && !ENTITY.canSmelt() && !ENTITY.isRedstonePowered())
            {
                // Powered, Cannot Smelt, No Redstone Signal.
                guiBookDecoder = "OFFLINE";
                this.fontRenderer.drawString(guiBookDecoder, 90, 21, Color.RED.getRGB());
            }
            else if (ENTITY.isPowered() && !ENTITY.canSmelt() && ENTITY.isRedstonePowered())
            {
                // Powered, Cannot Smelt, Redstone Signal.
                guiBookDecoder = "NOT READY";
                this.fontRenderer.drawString(guiBookDecoder, 90, 21, Color.RED.getRGB());
            }
            else if (ENTITY.isPowered() && ENTITY.canSmelt() && !ENTITY.isRedstonePowered())
            {
                // Powered, Can Smelt, No Redstone Signal.
                guiBookDecoder = "READY!";
                this.fontRenderer.drawString(guiBookDecoder, 90, 21, Color.GREEN.getRGB());
            }
            else if (ENTITY.isPowered() && ENTITY.canSmelt() && ENTITY.isRedstonePowered())
            {
                // Powered, Can Smelt, Redstone Signal (ACTIVE).
                guiBookDecoder = ENTITY.getStringFromBookContents().toUpperCase().trim();
                if (guiBookDecoder.contains("INVALID"))
                {
                    this.fontRenderer.drawString(guiBookDecoder, 90, 21, Color.RED.getRGB());
                }
                else
                {
                    this.fontRenderer.drawString(MadUtils.BinaryToAscii(guiBookDecoder), 90, 21, Color.GREEN.getRGB());
                }
            }
            else if (ENTITY.isPowered() && ENTITY.WATER_TANK != null && ENTITY.WATER_TANK.getFluidAmount() <= 5)
            {
                // No water detected in machine.
                guiBookDecoder = "NEED WATER";
                this.fontRenderer.drawString(guiBookDecoder, 90, 21, Color.RED.getRGB());
            }
        }

        // Power level for the machine.
        if (this.isPointInRegion(68, 62, 14, 14, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
        }

        // Cutting progress on the iron block.
        if (this.isPointInRegion(89, 45, 31, 14, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.currentItemCookingValue) + "/" + String.valueOf(this.ENTITY.currentItemCookingMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Progress " + String.valueOf(this.ENTITY.getItemCookTimeScaled(100)) + " %", powerLevelLiteral);
        }

        // Input water in form of buckets.
        if (this.isPointInRegion(31, 34, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input water bucket");
        }

        // Block of Iron that needs cutting.
        if (this.isPointInRegion(67, 44, 16, 16, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(1) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input iron block");
        }

        // Written book with binary code inside of it acting like a punchcard or data reel.
        if (this.isPointInRegion(67, 17, 16, 16, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(2) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input written book");
        }

        // Water tank help
        if (this.isPointInRegion(8, 9, 16, 58, mouseX, mouseY) && this.ENTITY.WATER_TANK.getFluid() != null)
        {
            if (this.ENTITY.WATER_TANK.getFluid() != null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, this.ENTITY.WATER_TANK.getFluid().getFluid().getLocalizedName(), this.ENTITY.WATER_TANK.getFluid().amount + " L");
        }

        // Help link and info for schematics for weapons.
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
}
