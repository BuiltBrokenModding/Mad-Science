package madscience.factory.block.prefab;

import net.minecraft.block.material.Material;
import madscience.factory.block.MadBlockFactoryProduct;

public class MadBlockPrefab extends MadBlockBasePrefab
{
    public MadBlockPrefab(int blockID, Material blockMaterial)
    {
        super(blockID, blockMaterial);
    }

    public MadBlockPrefab(MadBlockFactoryProduct registeredBlock)
    {
        super(registeredBlock);
    }

}
