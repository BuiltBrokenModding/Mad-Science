package madscience.factory.item.prefab;

import java.util.List;

import madscience.factory.MadItemFactory;
import madscience.factory.item.MadItemFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MadItemBasePrefab extends Item
{
    private String registeredItemName;
    
    private MadItemFactoryProduct registeredItem;
    
    public MadItemBasePrefab(int itemID)
    {
        super(itemID);
    }
    
    public MadItemBasePrefab(MadItemFactoryProduct itemData)
    {
        // Pass the information along to Minecraft/Forge.
        super(itemData.getItemID());
        
        // Defines which creative tab this item will belong to.
        this.setCreativeTab(MadMod.getCreativeTab());
        
        // Copy over instance information we will use to further create our item in classes above.
        this.registeredItem = itemData;
        this.registeredItemName = itemData.getItemBaseName();
        
        // Determine if this item should have repair recipes disabled in Minecraft/Forge.
        if (itemData.isNoRepair())
        {
            this.setNoRepair();
        }
        
        // Determines the maximum amount of damage this item can take (but does not determine if it has sub-items).
        this.setMaxDamage(itemData.getMaxDamage());

        // Determine how many of this item can be in a slot at any given time.
        this.maxStackSize = itemData.getMaxStacksize();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        // Tells minecraft to make more than one pass in rendering this item so we can have multiple layers to it.
        return this.getRegisteredItem().requiresMultipleRenderPasses();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        // White?! 16777215
        
        // TODO: Use the mapping to get correct color for given render pass.
        return 0;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass)
    {
        // TODO: Use the mapping to get Icons populated by registerIcons() to a given render pass.
        if (pass == 0)
        {
            return this.itemIcon;
        }
        else if (pass == 1)
        {
            return this.dnaSampleOverlay;
        }

        return this.itemIcon;
    }

    @Override
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        // Fired by Minecraft/Forge so it can get the icons for different render passes.
        return getIcon(stack, renderPass);
    }
    
    public MadItemFactoryProduct getRegisteredItem()
    {
        // Only query and recreate the registered item if we need it.
        if (this.registeredItem == null)
        {
            MadItemFactoryProduct reloadedItem = MadItemFactory.instance().getItemInfo(this.registeredItemName);
            this.registeredItem = reloadedItem;
            this.registeredItemName = reloadedItem.getItemBaseName();
        }
        
        return this.registeredItem;
    }
    
    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        // Determines if this item can break and pickup blocks and tiles.
        return this.getRegisteredItem().canHarvestBlocks();
    }

    public int getDamageVsEntity(Entity par1Entity) // NO_UCD (unused code)
    {
        // Determines how many hearts of damage this item does against mobs and entities.
        return this.getRegisteredItem().getDamageVSEntity();
    }

    @Override
    public int getItemEnchantability()
    {
        // Determines how Minecraft/Forge will be able to enchant this item.
        return this.getRegisteredItem().getEnchantability();
    }

    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        // Determines how quickly this item will break down blocks if can harvest returns true.
        return this.getRegisteredItem().getDamageVSBlock();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        // TODO: Loop through all render passes in the registered item and register their icons with Minecraft/Forge.
        // TODO: Associate via mapping render passes to a given registered icon.
        this.itemIcon = par1IconRegister.registerIcon(MadMod.ID + ":" + (this.getUnlocalizedName().substring(5)));
        this.iconString = itemIcon.getIconName();
    }
}
