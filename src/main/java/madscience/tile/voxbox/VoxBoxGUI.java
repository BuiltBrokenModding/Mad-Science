package madscience.tile.voxbox;

import java.awt.Desktop;
import java.net.URI;

import madscience.MadConfig;
import madscience.MadMachines;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInvisibleControl;
import madscience.factory.tileentity.MadGUITemplate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VoxBoxGUI extends MadGUITemplate
{
    // Placed tile entity in the game world.
    private VoxBoxEntity ENTITY;

    public VoxBoxGUI(InventoryPlayer inventory, VoxBoxEntity entity)
    {
        super(new VoxBoxContainer(inventory, entity));
        this.ENTITY = entity;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadMachines.VOXBOX_INTERNALNAME + ".png");
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
                    this.passClick(new URI(MadConfig.VOXBOX_HELP));
                }
                catch (Exception err)
                {
                    MadScience.logger.info("Unable to open wiki link in default browser.");
                }
            }
        }
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
        // Screen Coords: 80x43
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 80, screenY + 43 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Name displayed above the GUI, typically name of the furnace.
        String s = MadMachines.VOXBOX_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);

        // Power level
        if (this.isPointInRegion(80, 43, 14, 14, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
        }

        // Written book
        if (this.isPointInRegion(79, 25, 16, 16, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input written book");
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
}
