package madscience.tileentities.cryotube;

import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.util.GUIContainerBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CryotubeGUI extends GUIContainerBase
{
    // Placed tile entity in the game world.
    private CryotubeEntity ENTITY;

    public CryotubeGUI(InventoryPlayer par1InventoryPlayer, CryotubeEntity par2TileEntityFurnace)
    {
        super(new CryotubeContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.ENTITY = par2TileEntityFurnace;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.CRYOTUBE_INTERNALNAME + ".png");
    }

    /** Draw the background layer for the GuiContainer (everything behind the items) */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);

        // --------------
        // ENERGY STORAGE
        // --------------
        int powerRemianingPercentage = this.ENTITY.getPowerRemainingScaled(32);
        // MadScience.logger.info("POWER: " + powerRemianingPercentage);
        // Screen Coords: 112x17
        // Filler Coords: 176x56
        // Image Size WH: 18x32
        this.drawTexturedModalRect(screenX + 112, screenY + 17, 176, 56, 18, 32 - powerRemianingPercentage);

        // -----------------
        // HATCHING PROGRESS
        // -----------------
        int powerCookPercentage = this.ENTITY.getHatchTimeScaled(26);
        // Screen Coords: 35x37
        // Filler Coords: 176x0
        // Image Size WH: 26x10
        this.drawTexturedModalRect(screenX + 35, screenY + 37, 176, 0, powerCookPercentage + 1, 10);

        // --------------
        // SUBJECT HEALTH
        // --------------
        int subjectHealthPercentage = this.ENTITY.getSubjectHealthScaled(46);
        // Screen Coords: 68x17
        // Filler Coords: 176x10
        // Image Size WH: 11x46
        this.drawTexturedModalRect(screenX + 68, screenY + 17, 176, 10, 11, 46 - subjectHealthPercentage);

        // ---------------
        // NEURAL ACTIVITY
        // ---------------
        int neuralActivityPercentage = this.ENTITY.getNeuralActivityScaled(46);
        // Screen Coords: 91x17
        // Filler Coords: 187x10
        // Image Size WH: 11x46
        this.drawTexturedModalRect(screenX + 91, screenY + 17, 187, 10, 11, 46 - neuralActivityPercentage);
    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        // Name displayed above the GUI, typically name of the furnace.
        String s = MadFurnaces.CRYOTUBE_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
        
        //Neural Activity
        if (this.isPointInRegion(91, 17, 11, 46, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.neuralActivityValue) + "/" + String.valueOf(this.ENTITY.neuralActivityMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Neural " + String.valueOf(this.ENTITY.getNeuralActivityScaled(100)) + " %",
                    powerLevelLiteral);
        }
        
        // Subject Health
        if (this.isPointInRegion(68, 17, 11, 46, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.subjectCurrentHealth) + "/" + String.valueOf(this.ENTITY.subjectMaximumHealth);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Health " + String.valueOf(this.ENTITY.getSubjectHealthScaled(100)) + " %",
                    powerLevelLiteral);
        }
        
        // Hatching progress
        if (this.isPointInRegion(35, 37, 26, 10, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.hatchTimeCurrentValue) + "/" + String.valueOf(this.ENTITY.hatchTimeMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Hatching " + String.valueOf(this.ENTITY.getHatchTimeScaled(100)) + " %", powerLevelLiteral);
        }
        
        // Power level
        if (this.isPointInRegion(112, 17, 18, 32, mouseX, mouseY))
        {
            //String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %");
        }

        // Spawn egg input.
        if (this.isPointInRegion(11, 26, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input spawn egg");
        }
        
        // Memory or empty data reel input.
        if (this.isPointInRegion(11, 47, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(1) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input memory or empty data reel");
        }
        
        // Nether star input.
        if (this.isPointInRegion(113, 52, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(2) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input nether star");
        }        
       
    }
}
