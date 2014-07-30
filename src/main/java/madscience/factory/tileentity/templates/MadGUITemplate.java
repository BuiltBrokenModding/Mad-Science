package madscience.factory.tileentity.templates;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.buttons.MadGUIButton;
import madscience.factory.buttons.MadGUIButtonClickActionEnum;
import madscience.factory.buttons.MadGUIButtonInvisibleControl;
import madscience.factory.buttons.MadGUIButtonTypeEnum;
import madscience.factory.controls.MadGUIControl;
import madscience.factory.controls.MadGUIControlTypeEnum;
import madscience.factory.mod.MadMod;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.Region2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTankInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class MadGUITemplate extends GuiContainer
{
    private MadTileEntityPrefab ENTITY;
    private MadGUIControl[] GUICONTROLS;
    private MadSlotContainer[] CONTAINERS;
    private MadGUIButton[] BUTTONS;

    public ResourceLocation TEXTURE;

    public String SANDRA_YOUTUBE = "https://www.youtube.com/watch?feature=player_detailpage&v=ItjKGURohzU#t=76";

    private String tooltip = "";
    private HashMap<Region2, String> tooltips = new HashMap<Region2, String>();

    protected int screenX = (this.width - this.xSize) / 2;
    protected int screenY = (this.height - this.ySize) / 2;
    private float lastChangeFrameTime;

    private int PROMPT_REPLY_ACTION = 0;
    private URI displayedURI = null;
    private Fluid gaugeFluid;

    public MadGUITemplate(Container container)
    {
        super(container);
    }

    public MadGUITemplate(InventoryPlayer entityPlayer, MadTileEntityPrefab tileEntity)
    {
        super(new MadContainerTemplate(entityPlayer, tileEntity));
        this.ENTITY = tileEntity;

        // Query machine registry for GUI control information.
        MadTileEntityFactoryProduct MACHINE = MadTileEntityFactory.instance().getMachineInfo(this.ENTITY.getMachineInternalName());

        // Grab our array of GUI controls from the template object.
        this.GUICONTROLS = MACHINE.getGuiControlsTemplate();

        // Grab our array of container slots so we can provide them with tooltip from client renderer.
        this.CONTAINERS = MACHINE.getContainerTemplate();

        // Grab our array of buttons that we want to initialize and associate with this GUI.
        this.BUTTONS = MACHINE.getGuiButtonTemplate();

        // Grab the texture for the GUI control based off our name.
        this.TEXTURE = new ResourceLocation(MadMod.ID, "textures/gui/" + ENTITY.getMachineInternalName() + ".png");
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);

        // -------------
        // BUTTON ACTION
        // -------------

        // Loop through the buttons and decide how to proceed with given action.
        for (int i = 0; i < BUTTONS.length; i++)
        {
            MadGUIButton guiButton = BUTTONS[i];

            // Determine if button clicked matches any from our enumeration.
            if (button.id == guiButton.buttonID())
            {
                // Open Link
                if (guiButton.getClickAction().equals(MadGUIButtonClickActionEnum.OpenLink) && Desktop.isDesktopSupported())
                {
                    // Cast userdata object to string since we know it will be URL for wiki link.
                    String helpLink;
                    try
                    {
                        helpLink = (String) guiButton.getUserData();
                    }
                    catch (Exception err)
                    {
                        // Set the link to nothing we're aborting doctor!
                        helpLink = "";
                        MadMod.log().info("[MadGUIButtonInterface]Unable to cast userData from action " + guiButton.getClickAction() + " to string!");

                        // Stop further execution.
                        return;
                    }

                    // Check that our userData object was correctly turned into a string.
                    if (helpLink != null && !helpLink.isEmpty())
                    {
                        if (GuiScreen.isCtrlKeyDown() && GuiScreen.isShiftKeyDown())
                        {
                            try
                            {
                                this.passClick(new URI(this.SANDRA_YOUTUBE));
                            }
                            catch (Exception err)
                            {
                                MadMod.log().info("Unable to open sandra youtube easter egg link in default browser.");
                            }
                        }
                        else
                        {
                            try
                            {
                                this.passClick(new URI(helpLink));
                            }
                            catch (Exception err)
                            {
                                MadMod.log().info("Unable to open wiki link in default browser.");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void confirmClicked(boolean result, int action)
    {
        // Returns MC internal click codes for the URL response.
        if (action == PROMPT_REPLY_ACTION && result)
        {
            openURI(this.displayedURI);
            this.displayedURI = null;
        }

        this.mc.displayGuiScreen(this);
    }

    private void displayGauge(int screenX, int screenY, MadGUIControl control, int percentLeft)
    {
        // Variable to keep track of block texture segments.
        int start = 0;
        
        // Check if we need to discover the location of tank gauge fluid texture.
        if (this.ENTITY != null && this.gaugeFluid == null)
        {
            FluidTankInfo[] tankInfoArray = this.ENTITY.getTankInfo(ForgeDirection.UNKNOWN);
            for (FluidTankInfo tankInfo : tankInfoArray)
            {
                // TODO: Only one fluid and tank gauge is supported in the factory system!
                this.gaugeFluid = tankInfo.fluid.getFluid();
            }
        }

        // Grab the icon of the liquid by looking at fluid properties in internal tank.
        Icon liquidIcon = this.gaugeFluid.getStillIcon();

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
            if (percentLeft > control.sizeX())
            {
                x = control.sizeX();
                percentLeft -= control.sizeX();
            }
            else
            {
                x = percentLeft;
                percentLeft = 0;
            }

            // Draw fluid tank with proper offset.
            drawTexturedModelRectFromIcon(screenX + control.screenX(), screenY + control.screenY() + control.sizeY() - x - start, liquidIcon, control.sizeX(), control.sizeX() - (control.sizeX() - x));

            start = start + control.sizeX();

            if (x == 0 || percentLeft == 0)
            {
                break;
            }
        }

        // Re-draws gauge lines ontop of scaled fluid amount to make it look like the fluid is behind the gauge lines.
        mc.renderEngine.bindTexture(TEXTURE);
        drawTexturedModalRect(screenX + control.screenX(), screenY + control.screenY(), control.fillerX(), control.fillerY(), control.sizeX(), control.sizeY());
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        // Define the area of the screen.
        this.screenX = (this.width - this.xSize) / 2;
        this.screenY = (this.height - this.ySize) / 2;

        // Bind the texture that will makeup our GUI overlay.
        this.mc.renderEngine.bindTexture(this.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // Draw the base texture we will draw controls and other elements.
        this.drawTexturedModalRect(this.screenX, this.screenY, 0, 0, this.xSize, this.ySize);

        // Loop through the controls and use the data inside them to prepare the server client GUI rendering.
        for (int i = 0; i < this.GUICONTROLS.length; i++)
        {
            MadGUIControl guiControl = this.GUICONTROLS[i];

            // -------------
            // POWER LEVEL X
            // -------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelX))
            {
                int energyLevelX = this.ENTITY.getPowerRemainingScaled(guiControl.sizeX());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY(),
                        guiControl.fillerX(),
                        guiControl.fillerY(),
                        energyLevelX,
                        guiControl.sizeY());
            }
            
            // -------------
            // POWER LEVEL Y
            // -------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelY))
            {
                int energyLevelY = this.ENTITY.getPowerRemainingScaled(guiControl.sizeY());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY() + guiControl.sizeY() - energyLevelY,
                        guiControl.fillerX(),
                        guiControl.fillerY() + guiControl.sizeY() - energyLevelY,
                        guiControl.sizeX(),
                        energyLevelY);
            }

            // ------------------
            // COOKING PROGRESS X
            // ------------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressX))
            {
                int cookingProgressX = this.ENTITY.getItemCookTimeScaled(guiControl.sizeX());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY(),
                        guiControl.fillerX(),
                        guiControl.fillerY(),
                        cookingProgressX,
                        guiControl.sizeY());
            }
            
            // ------------------
            // COOKING PROGRESS Y
            // ------------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressY))
            {
                int cookingProgressY = this.ENTITY.getItemCookTimeScaled(guiControl.sizeY());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY() + guiControl.sizeY() - cookingProgressY,
                        guiControl.fillerX(),
                        guiControl.fillerY() + guiControl.sizeY() - cookingProgressY,
                        guiControl.sizeX(),
                        cookingProgressY);
            }
            
            // --------------
            // DAMAGE LEVEL X
            // --------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.DamageLevelX))
            {
                int damageLevelX = this.ENTITY.getDamageValueScaled(guiControl.sizeX());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY(),
                        guiControl.fillerX(),
                        guiControl.fillerY(),
                        damageLevelX,
                        guiControl.sizeY());
            }
            
            // --------------
            // DAMAGE LEVEL Y
            // --------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.DamageLevelY))
            {
                int damageLevelY = this.ENTITY.getDamageValueScaled(guiControl.sizeY());
                this.drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY() + guiControl.sizeY() - damageLevelY,
                        guiControl.fillerX(),
                        guiControl.fillerY() + guiControl.sizeY() - damageLevelY,
                        guiControl.sizeX(),
                        damageLevelY);
            }
            
            // ------------
            // HEAT LEVEL X
            // ------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.HeatGaugeX))
            {
                int heatLevelX = this.ENTITY.getHeatLevelTimeScaled(guiControl.sizeX());
                drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY(),
                        guiControl.fillerX(),
                        guiControl.fillerY(),
                        heatLevelX,
                        guiControl.sizeY());
            }
            
            // ------------
            // HEAT LEVEL Y
            // ------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.HeatGaugeY))
            {
                int heatLevelY = this.ENTITY.getHeatLevelTimeScaled(guiControl.sizeY());
                drawTexturedModalRect(
                        screenX + guiControl.screenX(),
                        screenY + guiControl.screenY() + guiControl.sizeY() - heatLevelY,
                        guiControl.fillerX(),
                        guiControl.fillerY() + guiControl.sizeY() - heatLevelY,
                        guiControl.sizeX(),
                        heatLevelY);
            }

            // ------------
            // TANK GAUGE Y
            // ------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.TankGaugeY))
            {
                displayGauge(
                        screenX,
                        screenY,
                        guiControl,
                        this.ENTITY.getFluidRemainingScaled(guiControl.sizeY()));
            }
        }
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Name displayed above the GUI, typically name of the furnace.
        String machineNameLocalized = StatCollector.translateToLocal("tile." + this.ENTITY.getMachineInternalName() + ".name");
        
        // Check to make sure the machine name has something, if nothing use internal name.
        if (machineNameLocalized == null)
        {
            machineNameLocalized = this.ENTITY.getMachineInternalName();
        }
        
        // Check to make sure the localized string is not empty, if so then use internal name.
        if (machineNameLocalized != null && machineNameLocalized.isEmpty())
        {
            machineNameLocalized = this.ENTITY.getMachineInternalName();
        }
        
        this.fontRenderer.drawString(machineNameLocalized, this.xSize / 2 - this.fontRenderer.getStringWidth(machineNameLocalized) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String inventoryWordLocalized = I18n.getString("container.inventory");
        this.fontRenderer.drawString(inventoryWordLocalized, 8, this.ySize - 96 + 2, 4210752);

        // --------
        // CONTROLS
        // --------
        for (int i = 0; i < this.GUICONTROLS.length; i++)
        {
            // Loop through the controls and use the data inside them to prepare the server client GUI rendering.
            MadGUIControl guiControl = this.GUICONTROLS[i];

            // Power level X OR Y
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelX) || guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelY))
            {
                if (this.isPointInRegion(guiControl.screenX(), guiControl.screenY(), guiControl.sizeX(), guiControl.sizeY(), mouseX, mouseY))
                {
                    String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
                    String powerLevelLocalized = StatCollector.translateToLocal("tooltip.energy") + " "; 
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, powerLevelLocalized + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
                }
            }

            // Cooking progress X OR Y
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressX) || guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressY))
            {
                if (this.isPointInRegion(guiControl.screenX(), guiControl.screenY(), guiControl.sizeX(), guiControl.sizeY(), mouseX, mouseY))
                {
                    String cookingProgressLiteral = String.valueOf(this.ENTITY.getProgressValue()) + "/" + String.valueOf(this.ENTITY.getProgressMaximum());
                    String cookingLevelLocalized = StatCollector.translateToLocal("tooltip.progress") + " ";
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, cookingLevelLocalized + String.valueOf(this.ENTITY.getItemCookTimeScaled(100)) + " %", cookingProgressLiteral);
                }
            }

            // Tank fluid level information.
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.TankGaugeY))
            {
                if (this.isPointInRegion(guiControl.screenX(), guiControl.screenY(), guiControl.sizeX(), guiControl.sizeY(), mouseX, mouseY))
                {
                    if (this.ENTITY.getFluidStack() != null)
                    {
                        this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, this.ENTITY.getFluidLocalizedName(), this.ENTITY.getFluidAmount() + " L");
                    }
                }
            }
            
            // Damage level information X OR Y.
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.DamageLevelX) || guiControl.getControlType().equals(MadGUIControlTypeEnum.DamageLevelY))
            {
                if (this.isPointInRegion(guiControl.screenX(), guiControl.screenY(), guiControl.sizeX(), guiControl.sizeY(), mouseX, mouseY))
                {
                    String dmgLevelLiteral = String.valueOf(this.ENTITY.getDamageValue()) + "/" + String.valueOf(this.ENTITY.getDamageMaximum());
                    String dmgLevelLocalized = StatCollector.translateToLocal("tooltip.damage") + " ";
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, dmgLevelLocalized + String.valueOf(this.ENTITY.getDamageValueScaled(100)) + " %", dmgLevelLiteral);
                }
            }
            
            // Heat level information X OR Y.
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.HeatGaugeX) || guiControl.getControlType().equals(MadGUIControlTypeEnum.HeatGaugeY))
            {
                if (this.isPointInRegion(guiControl.screenX(), guiControl.screenY(), guiControl.sizeX(), guiControl.sizeY(), mouseX, mouseY))
                {
                    String heatLevelLiteral = String.valueOf(this.ENTITY.getHeatLevelValue()) + "/" + String.valueOf(this.ENTITY.getHeatLevelMaximum());
                    String heatLevelLocalized = StatCollector.translateToLocal("tooltip.heat") + " ";
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, heatLevelLocalized + String.valueOf(this.ENTITY.getHeatLevelTimeScaled(100)) + " %", heatLevelLiteral);
                }
            }
        }

        // ----------
        // CONTAINERS
        // ----------
        for (int i = 0; i < CONTAINERS.length; i++)
        {
            // Loop through the slot containers and add tooltip information based on their position relative to screen and GUI.
            MadSlotContainer slotContainer = CONTAINERS[i];

            // Check if mouse cursor is within the given region of the GUI.
            if (this.isPointInRegion(slotContainer.offsetX(), slotContainer.offsetY(), slotContainer.sizeX(), slotContainer.sizeY(), mouseX, mouseY))
            {
                // Standard Tooltip.
                if (this.ENTITY.getStackInSlot(slotContainer.slot()) == null)
                {
                    // Determine if we should label the slot as input or output.
                    String slotTooltip = "";
                    if (slotContainer.getSlotType().name().toLowerCase().contains("input"))
                    {
                        // Input slot.
                        slotTooltip = StatCollector.translateToLocal("tile.inputslot.tooltip");
                    }
                    else if (slotContainer.getSlotType().name().toLowerCase().contains("output"))
                    {
                        // Output slot.
                        slotTooltip = StatCollector.translateToLocal("tile.outputslot.tooltip");
                    }
                    
                    // By design if the tooltip is empty we will render nothing for tooltip then.
                    if (slotTooltip != null && !slotTooltip.isEmpty())
                    {
                        this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, slotTooltip);
                    }
                }
            }
        }

        // -------
        // BUTTONS
        // -------
        for (int i = 0; i < BUTTONS.length; i++)
        {
            // Loop through the buttons and determines if we need to show any tooltip information.
            MadGUIButton guiButton = BUTTONS[i];

            // Help link.
            if (guiButton.getButtonType().equals(MadGUIButtonTypeEnum.InvisibleButton))
            {
                // Check if mouse cursor is within the given region of the GUI.
                if (this.isPointInRegion(guiButton.screenX(), guiButton.screenY(), guiButton.sizeX(), guiButton.sizeY(), mouseX, mouseY))
                {
                    // By design if the tooltip is empty we will render nothing for tooltip then.
                    String slotTooltip = StatCollector.translateToLocal(guiButton.getTooltip());
                    if (slotTooltip != null && !slotTooltip.isEmpty())
                    {
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            // The Net Reference - Easter Egg 1
                            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Sandra Bullock Mode");
                        }
                        else
                        {
                            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, slotTooltip);
                        }
                    }
                }
            }
        }
    }

    private void drawItemStack(ItemStack itemStack, int x, int y)
    {
        x += 1;
        y += 1;
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);

        // drawTexturedModelRectFromIcon
        // GL11.glEnable(GL11.GL_BLEND);
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack, x, y);
        // GL11.glDisable(GL11.GL_BLEND);
    }

    private void drawTextWithTooltip(String textName, String format, int x, int y, int mouseX, int mouseY)
    {
        this.drawTextWithTooltip(textName, format, x, y, mouseX, mouseY, 4210752);
    }

    private void drawTextWithTooltip(String textName, String format, int x, int y, int mouseX, int mouseY, int color)
    {
        String name = I18n.getString("gui." + textName + ".name");
        String text = format.replaceAll("%1", name);
        this.fontRenderer.drawString(text, x, y, color);

        String tooltip = I18n.getString("gui." + textName + ".tooltip");

        if (tooltip != null && tooltip != "")
        {
            if (this.isPointInRegion(x, y, (int) (text.length() * 4.8), 12, mouseX, mouseY))
            {
                this.tooltip = tooltip;
            }
        }
    }

    public void drawTooltip(int x, int y, String... toolTips)
    {
        if (!GuiScreen.isShiftKeyDown())
        {
            if (toolTips != null)
            {
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glDisable(GL11.GL_DEPTH_TEST);

                int var5 = 0;
                int var6;
                int var7;

                for (var6 = 0; var6 < toolTips.length; ++var6)
                {
                    var7 = this.fontRenderer.getStringWidth(toolTips[var6]);

                    if (var7 > var5)
                    {
                        var5 = var7;
                    }
                }

                var6 = x + 12;
                var7 = y - 12;

                int var9 = 8;

                if (toolTips.length > 1)
                {
                    var9 += 2 + (toolTips.length - 1) * 10;
                }

                if (this.guiTop + var7 + var9 + 6 > this.height)
                {
                    var7 = this.height - var9 - this.guiTop - 6;
                }

                this.zLevel = 300;
                int var10 = -267386864;
                this.drawGradientRect(var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10);
                this.drawGradientRect(var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10);
                this.drawGradientRect(var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10);
                int var11 = 1347420415;
                int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
                this.drawGradientRect(var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12);
                this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11);
                this.drawGradientRect(var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

                for (int var13 = 0; var13 < toolTips.length; ++var13)
                {
                    String var14 = toolTips[var13];

                    this.fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                    var7 += 10;
                }

                this.zLevel = 0;

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        // --------------
        // CREATE BUTTONS
        // --------------

        for (int i = 0; i < BUTTONS.length; i++)
        {
            // Loop through the buttons and create them based on info in enumeration.
            MadGUIButton guiButton = BUTTONS[i];

            int posX = (this.width - guiButton.sizeX()) / 2;
            int posY = (this.height - guiButton.sizeY()) / 2;

            // Clear the internal button list before adding them.
            buttonList.clear();

            // Invisible button
            if (guiButton.getButtonType().equals(MadGUIButtonTypeEnum.InvisibleButton))
            {
                buttonList.add(new MadGUIButtonInvisibleControl(guiButton.buttonID(), posX + 81, posY - 76, guiButton.sizeX(), guiButton.sizeY()));
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    private void openURI(URI uri)
    {
        try
        {
            // Opens default browser on system with URL.
            Desktop.getDesktop().browse(uri);
        }
        catch (IOException e)
        {
            // Nothing to see here.
        }
    }

    public void passClick(URI webLocation)
    {
        // Rude not to ask if user has chat link prompts disabled.
        if (Minecraft.getMinecraft().gameSettings.chatLinksPrompt)
        {
            this.displayedURI = webLocation;
            this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.displayedURI.toString(), PROMPT_REPLY_ACTION, false));
        }
        else
        {
            // Open the URL normally without asking anything.
            openURI(webLocation);
        }
    }

}