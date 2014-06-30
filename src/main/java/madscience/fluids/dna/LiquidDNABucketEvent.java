package madscience.fluids.dna;

import madscience.MadFluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class LiquidDNABucketEvent
{
    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
    {
        int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);

        if ((blockID == MadFluids.LIQUIDDNA.getBlockID() || blockID == MadFluids.LIQUIDDNA_BLOCK.blockID) && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)
        {
            world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
            return new ItemStack(MadFluids.LIQUIDDNA_BUCKET_ITEM);
        }
        else
        {
            return null;
        }
    }

    @ForgeSubscribe
    public void onBucketFill(FillBucketEvent event) // NO_UCD (unused code)
    {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result != null)
        {
            event.result = result;
            event.setResult(Result.ALLOW);
        }
    }
}
