package madscience.fluids.dnamutant;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LiquidDNAMutant extends Fluid
{
    public LiquidDNAMutant(String internalName)
    {
        super(internalName);

        // used by the block to work out how much it slows entities.
        setDensity(3);

        // used by the block to work out how fast it flows
        setViscosity(4000);

        // Set how well light can pass through this block.
        setLuminosity(5);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) // NO_UCD (unused code)
    {
        // Use the same default minecraft icon for water (for now).
        return Block.waterMoving.getIcon(side, meta);
    }

    /**
     * Returns the unlocalized name of this fluid.
     */
    @Override
    public String getUnlocalizedName()
    {
        return "tile." + this.unlocalizedName + ".name";
    }
}
