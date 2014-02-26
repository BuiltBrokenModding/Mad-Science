package madscience.tileentities.soniclocator;

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
public class SoniclocatorGUI extends GUIContainerBase
{
    private SoniclocatorEntity ENTITY;

    public SoniclocatorGUI(InventoryPlayer par1InventoryPlayer, SoniclocatorEntity par2TileEntityFurnace)
    {
        super(new SoniclocatorContainer(par1InventoryPlayer, par2TileEntityFurnace));
        this.ENTITY = par2TileEntityFurnace;
        TEXTURE = new ResourceLocation(MadScience.ID, "textures/gui/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + ".png");
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
        // Screen Coords: 86x62
        // Filler Coords: 176x0
        // Image Size WH: 14x14
        this.drawTexturedModalRect(screenX + 86, screenY + 60 + 14 - powerRemianingPercentage, 176, 14 - powerRemianingPercentage, 14, powerRemianingPercentage);

        // ----------
        // HEAT LEVEL
        // ----------
        int heatLevelPercentage = ENTITY.getHeatLevelTimeScaled(40);
        // Screen Coords: 88x18
        // Filler Coords: 176x14
        // Image Size WH: 18x40
        drawTexturedModalRect(screenX + 88, screenY + 18, 176, 14, 18, 40 - heatLevelPercentage);

    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        // Name displayed above the GUI, typically name of the furnace.
        String s = MadFurnaces.SONICLOCATOR_TILEENTITY.getLocalizedName();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);

        // Text that labels player inventory area as "Inventory".
        String x = I18n.getString("container.inventory");
        this.fontRenderer.drawString(x, 8, this.ySize - 96 + 2, 4210752);
        
        // Power level
        if (this.isPointInRegion(86, 62, 14, 14, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.getEnergy(ForgeDirection.UNKNOWN)) + "/" + String.valueOf(this.ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN));
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Energy " + String.valueOf(this.ENTITY.getPowerRemainingScaled(100)) + " %", powerLevelLiteral);
        }
        
        // Heat level
        if (this.isPointInRegion(88, 18, 18, 40, mouseX, mouseY))
        {
            String powerLevelLiteral = String.valueOf(this.ENTITY.currentHeatValue) + "/" + String.valueOf(this.ENTITY.currentHeatMaximum);
            this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Progress " + String.valueOf(this.ENTITY.getHeatLevelTimeScaled(100)) + " %",
                    powerLevelLiteral);
        }

        // Input gravel
        if (this.isPointInRegion(27, 35, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(0) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input gravel");
        }
        
        // Input target block.
        if (this.isPointInRegion(58, 35, 18, 18, mouseX, mouseY))
        {
            if (this.ENTITY.getStackInSlot(1) == null)
                this.drawTooltip(mouseX - this.guiLeft, mouseY - this.guiTop + 10, "Input target block");
        }
        

    }
}
