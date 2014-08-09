package madscience.factory.block.prefab;

import madscience.factory.block.MadBlockFactoryProduct;
import net.minecraft.block.material.Material;

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
