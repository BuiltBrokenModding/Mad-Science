package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.block.MadBlockFactoryProductData;
import madscience.factory.block.MadBlockRenderPass;
import madscience.factory.block.MadMetaBlockData;
import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;

public class MadManualBlocks
{
    private List<MadBlockFactoryProductData> manualBlocks = null;
    
    public List<MadBlockFactoryProductData> getManualBlocks()
    {
        return manualBlocks;
    }
    
    public MadManualBlocks()
    {
        manualBlocks = new ArrayList<MadBlockFactoryProductData>();
        
        // ----------
        // ENDERSLIME
        // ----------
        {
            List<MadMetaBlockData> enderSlimeSubblocks = new ArrayList<MadMetaBlockData>();
            
            {
                List<MadBlockRenderPass> iconEnderslime = new ArrayList<MadBlockRenderPass>();
                iconEnderslime.add(new MadBlockRenderPass(0, "enderslimeBlock", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subBlockCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subBlockCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentEnderslime:0:1",
                        "1:madscience:componentEnderslime:0:1",
                        "2:madscience:componentEnderslime:0:1",
                        "3:madscience:componentEnderslime:0:1",
                        "4:madscience:componentEnderslime:0:1",
                        "5:madscience:componentEnderslime:0:1",
                        "6:madscience:componentEnderslime:0:1",
                        "7:madscience:componentEnderslime:0:1",
                        "8:madscience:componentEnderslime:0:1"));
                
                enderSlimeSubblocks.add(new MadMetaBlockData(0, "enderslime", subBlockCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, null, iconEnderslime.toArray(new MadBlockRenderPass[]{})));
            }
            
            MadBlockFactoryProductData blockComponents = new MadBlockFactoryProductData("components", null, 5.0F, 8, 42, 1, 0.1F, enderSlimeSubblocks.toArray(new MadMetaBlockData[]{}));
            manualBlocks.add(blockComponents);
        }
    }
}
