package madscience.tileentities.dnaextractor;

import java.awt.Desktop;
import java.net.URI;

import madscience.MadConfig;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.interfaces.buttons.MadGUIButtonClickActionEnum;
import madscience.factory.interfaces.buttons.MadGUIButtonInterface;
import madscience.factory.interfaces.buttons.MadGUIButtonTypeEnum;
import madscience.factory.interfaces.controls.MadGUIControlInterface;
import madscience.factory.interfaces.controls.MadGUIControlTypeEnum;
import madscience.factory.interfaces.slotcontainers.MadSlotContainerInterface;
import madscience.factory.tileentity.MadTileEntityFactory;
import madscience.factory.tileentity.MadTileEntityTemplate;
import madscience.gui.GUIButtonInvisible;
import madscience.gui.GUIContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DNAExtractorGUI extends GUIContainerBase
{
    /* Tile entity that is sitting in the game world */
    private DNAExtractorEntity ENTITY;
    
    /* Every GUI control like power, cook time, tank gauges, etc. */
    private MadGUIControlInterface[] GUICONTROLS;
    
    /* Every Container such as input and output slots. */
    private MadSlotContainerInterface[] CONTAINERS;
    
    /* Every button we want to display and hook on this GUI */
    private MadGUIButtonInterface[] BUTTONS;

    public DNAExtractorGUI(InventoryPlayer entityPlayer, DNAExtractorEntity worldEntity)
    {
        super(new DNAExtractorContainer(entityPlayer, worldEntity));
        this.ENTITY = worldEntity;
        
        // Query machine registry for GUI control information.
        MadTileEntityTemplate MACHINE = MadTileEntityFactory.getMachineInfo(this.ENTITY.getInvName());
        
        // Grab our array of GUI controls from the template object.
        this.GUICONTROLS = MACHINE.getGUIControls();
        
        // Grab our array of container slots so we can provide them with tooltip from client renderer.
        this.CONTAINERS = MACHINE.getSlotContainers();
        
        // Grab our array of buttons that we want to initialize and associate with this GUI.
        this.BUTTONS = MACHINE.getGUIButtons();
        
        // Grab the texture for the GUI control based off our name.
        this.TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.DNAEXTRACTOR_INTERNALNAME + ".png");
    }

    private void displayGauge(int screenX, int screenY, MadGUIControlInterface control, int percentLeft)
    {
        // Variable to keep track of block texture segments.
        int start = 0;

        // Grab the icon of the liquid by looking at fluid properties in internal tank.
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
            if (percentLeft > control.sizeX())
            {
                x = 16;
                percentLeft -= control.sizeX();
            }
            else
            {
                x = percentLeft;
                percentLeft = 0;
            }

            // Draw mutant DNA tank with proper offset.
            drawTexturedModelRectFromIcon(screenX + control.sizeY(), screenY + control.screenX() + control.sizeY() - x - start, liquidIcon, control.sizeX(), control.sizeX() - (control.sizeX() - x));
            start = start + control.sizeX();

            if (x == 0 || percentLeft == 0)
            {
                break;
            }
        }

        // Re-draws gauge lines ontop of scaled fluid amount to make it look
        // like the fluid is behind the gauge lines.
        mc.renderEngine.bindTexture(TEXTURE);
        drawTexturedModalRect(screenX + control.sizeY(), screenY + control.sizeX(),
                control.fillerX(),
                control.fillerY(),
                control.sizeX(),
                control.sizeY());
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        
        // Loop through the controls and use the data inside them to prepare the server client GUI rendering.
        for(int i = 0; i < this.GUICONTROLS.length; i++)
        {
            MadGUIControlInterface guiControl = this.GUICONTROLS[i];
            
            // -------------
            // POWER LEVEL X
            // -------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelX))
            {
                int powerRemianingPercentage = this.ENTITY.getPowerRemainingScaled(guiControl.sizeX());
                this.drawTexturedModalRect(screenX + guiControl.screenX(),
                        screenY + guiControl.screenY() + guiControl.sizeY() - powerRemianingPercentage,
                        guiControl.fillerX(),
                        guiControl.sizeX() - powerRemianingPercentage,
                        guiControl.sizeY(), powerRemianingPercentage + 2);
            }
            
            // ------------------
            // COOKING PROGRESS Y
            // ------------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressY))
            {
                int powerCookPercentage = this.ENTITY.getItemCookTimeScaled(guiControl.screenY());
                this.drawTexturedModalRect(screenX + guiControl.screenX(),
                        screenY + guiControl.screenY(),
                        guiControl.fillerX(), guiControl.fillerY(),
                        powerCookPercentage + 1,
                        guiControl.sizeY());
            }
            
            // ------------
            // PROGRESS BAR
            // ------------
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.TankGauge))
            {
                displayGauge(screenX, screenY,
                        guiControl,
                        this.ENTITY.getWaterRemainingScaled(guiControl.sizeY()));
            }
        }
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        // Name displayed above the GUI, typically name of the furnace.
        String s = MadFurnaces.DNAEXTRACTOR_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
        
        // --------
        // CONTROLS
        // --------
        for(int i = 0; i < this.GUICONTROLS.length; i++)
        {
            // Loop through the controls and use the data inside them to prepare the server client GUI rendering.
            MadGUIControlInterface guiControl = this.GUICONTROLS[i];
            
            // Power level X OR Y
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelX) ||
                    guiControl.getControlType().equals(MadGUIControlTypeEnum.PowerLevelY))
            {
                if (this.isPointInRegion(guiControl.screenX(),
                        guiControl.screenY(),
                        guiControl.sizeX(),
                        guiControl.sizeY(),
                        mouseX, mouseY))
                {
                    String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
                }
            }
            
            // Cooking progress X OR Y
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressX) ||
                    guiControl.getControlType().equals(MadGUIControlTypeEnum.CookingProgressY))
            {
                if (this.isPointInRegion(guiControl.screenX(),
                        guiControl.screenY(),
                        guiControl.sizeX(),
                        guiControl.sizeY(),
                        mouseX, mouseY))
                {
                    String powerLevelLiteral = String.valueOf(this.ENTITY.currentItemCookingValue) + "/" + String.valueOf(this.ENTITY.currentItemCookingMaximum);
                    this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Progress " + String.valueOf(this.ENTITY.getItemCookTimeScaled(100)) + " %",
                            powerLevelLiteral);
                }
            }
            
            // Tank fluid level information.
            if (guiControl.getControlType().equals(MadGUIControlTypeEnum.TankGauge))
            {
                if (this.isPointInRegion(guiControl.screenX(),
                        guiControl.screenY(),
                        guiControl.sizeX(),
                        guiControl.sizeY(), mouseX, mouseY) && this.ENTITY.internalLiquidDNAMutantTank.getFluid() != null)
                {
                    if (this.ENTITY.internalLiquidDNAMutantTank.getFluid() != null)
                        this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, this.ENTITY.internalLiquidDNAMutantTank.getFluid().getFluid().getLocalizedName(), this.ENTITY.internalLiquidDNAMutantTank.getFluid().amount + " L");
                }
            }
        }
        
        // ----------
        // CONTAINERS
        // ----------
        for(int i = 0; i < CONTAINERS.length; i++)
        {
            // Loop through the slot containers and add tooltip information based on their position relative to screen and GUI.
            MadSlotContainerInterface slotContainer = CONTAINERS[i];
            
            // Check if mouse cursor is within the given region of the GUI.
            if (this.isPointInRegion(slotContainer.offsetX(),
                    slotContainer.offsetY(),
                    slotContainer.sizeX(),
                    slotContainer.sizeY(), mouseX, mouseY))
            {
                // Standard Tooltip.
                if (this.ENTITY.getStackInSlot(slotContainer.slot()) == null)
                {
                    // By design if the tooltip is empty we will render nothing for tooltip then.
                    String slotTooltip = slotContainer.getTooltip();
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
        for(int i = 0; i < BUTTONS.length; i++)
        {
            // Loop through the buttons and determines if we need to show any tooltip information.
            MadGUIButtonInterface guiButton = BUTTONS[i];
            
            // Help link.
            if (guiButton.getButtonType().equals(MadGUIButtonTypeEnum.InvisibleButton))
            {
                // Check if mouse cursor is within the given region of the GUI.
                if (this.isPointInRegion(guiButton.screenX(),
                        guiButton.screenY(),
                        guiButton.sizeX(),
                        guiButton.sizeY(), mouseX, mouseY))
                {
                    // By design if the tooltip is empty we will render nothing for tooltip then.
                    String slotTooltip = guiButton.getTooltip();
                    if (slotTooltip != null && !slotTooltip.isEmpty())
                    {
                        if (this.isCtrlKeyDown())
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

    @Override
    public void initGui()
    {
        super.initGui();

        // --------------
        // CREATE BUTTONS
        // --------------
        
        for(int i = 0; i < BUTTONS.length; i++)
        {
            // Loop through the buttons and create them based on info in enumeration.
            MadGUIButtonInterface guiButton = BUTTONS[i];
            
            int posX = (this.width - guiButton.sizeX()) / 2;
            int posY = (this.height - guiButton.sizeY()) / 2;
            
            // Clear the internal button list before adding them.
            buttonList.clear();
            
            // Invisible button
            if (guiButton.getButtonType().equals(MadGUIButtonTypeEnum.InvisibleButton))
            {
                buttonList.add(new GUIButtonInvisible(1, posX + 81, posY - 76,
                        guiButton.sizeX(), guiButton.sizeY()));
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        
        // -------------
        // BUTTON ACTION
        // -------------
        
        // Loop through the buttons and decide how to proceed with given action.
        for(int i = 0; i < BUTTONS.length; i++)
        {
            MadGUIButtonInterface guiButton = BUTTONS[i];
            
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
                        MadScience.logger.info("[MadGUIButtonInterface]Unable to cast userData from action " + guiButton.getClickAction() + " to string!");
                        
                        // Stop further execution.
                        return;
                    }
                    
                    // Check that our userData object was correctly turned into a string.
                    if (helpLink != null && !helpLink.isEmpty())
                    {
                        if (this.isCtrlKeyDown() && this.isShiftKeyDown())
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
                                this.passClick(new URI(helpLink));
                            }
                            catch (Exception err)
                            {
                                MadScience.logger.info("Unable to open wiki link in default browser.");
                            }
                        }
                    }
                }
            }            
        }
    }
}
