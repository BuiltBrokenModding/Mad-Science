package madscience.tileentities.clayfurnace;

import java.awt.Desktop;
import java.net.URI;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.buttons.MadGUIButtonInvisibleControl;
import madscience.factory.templates.MadGUITemplate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClayfurnaceGUI extends MadGUITemplate
{
    private ClayfurnaceEntity ENTITY;

    public ClayfurnaceGUI(InventoryPlayer thePlayer, ClayfurnaceEntity meatCubeEntity)
    {
        super(new ClayfurnaceContainer(thePlayer, meatCubeEntity));
        this.ENTITY = meatCubeEntity;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + ".png");
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
                    this.passClick(new URI(MadConfig.CLAYFURNACE_HELP));
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

        // ---------------------
        // ITEM COOKING PROGRESS
        // ---------------------
        int powerCookPercentage = this.ENTITY.getItemCookTimeScaled(28);
        // Screen Coords: 72x15
        // Filler Coords: 176x0
        // Image Size WH: 28x28
        this.drawTexturedModalRect(screenX + 72, screenY + 15 + 28 - powerCookPercentage, 176, 28 - powerCookPercentage, 28, powerCookPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Name displayed above the GUI, typically name of the furnace.
        // Note: Extra spaces are to make name align proper in GUI.
        String s = MadFurnaces.CLAYFURNACE_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String containerName = I18n.getString("container.inventory");
        this.fontRenderer.drawString(containerName, 8, this.ySize - 96 + 2, 4210752);

        // Input charcoal block for fuel source.
        if (this.isPointInRegion(62, 50, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input coal block");
        }

        // Input raw ore to cook into source block.
        if (this.isPointInRegion(98, 50, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input ore");
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
