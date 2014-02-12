package madscience.fluids.dnaMutant;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
    public Icon getIcon(int side, int meta)
    {
        // Use the same default minecraft icon for water (for now).
        return Block.waterMoving.getIcon(side, meta);
    }
}
