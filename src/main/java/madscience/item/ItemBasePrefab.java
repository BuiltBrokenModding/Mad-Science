package madscience.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.ModMetadata;
import madscience.factory.ItemFactory;
import madscience.mod.ModLoader;
import madscience.product.ItemFactoryProduct;
import madscience.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public abstract class ItemBasePrefab extends Item
{
    /**
     * Name of this item that is also used as key in mapping lists in item factory and icon archive.
     */
    private String registeredItemName;

    /**
     * Reference to registered product from item factory.
     */
    private ItemFactoryProduct registeredItem;

    /**
     * Mapping for all registered Icons for client rendering.
     */
    private Map<String, Icon> clientRegisteredIcons = new LinkedHashMap<String, Icon>();

    public ItemBasePrefab(int itemID)
    {
        super( itemID );
    }

    public ItemBasePrefab(ItemFactoryProduct itemData)
    {
        // Pass the information along to Minecraft/Forge.
        super( itemData.getItemID() );

        // Copy over instance information we will use to further create our item in classes above.
        this.registeredItem = itemData;
        this.registeredItemName = itemData.getItemBaseName();

        // Defines the base name for this item, sub-items automatically extend this base name.
        this.setUnlocalizedName( itemData.getItemBaseName() );

        // Defines which creative tab this item will belong to.
        this.setCreativeTab( ModLoader.getCreativeTab() );

        // Determine if this item should have repair recipes disabled in Minecraft/Forge.
        if (itemData.isNoRepair())
        {
            this.setNoRepair();
        }

        // Determines the maximum amount of damage this item can take (but does not determine if it has sub-items).
        this.setMaxDamage( itemData.getMaxDamage() );

        // Determine how many of this item can be in a slot at any given time.
        this.maxStackSize = itemData.getMaxStacksize();

        // Determine if this item has subtypes (using damage value as sub-items).
        this.setHasSubtypes( itemData.hasSubItems() );
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Only displays tooltip information when SHIFT key is pressed.
        String tooltip = StatCollector.translateToLocal( getUnlocalizedName( par1ItemStack ) + ".tooltip" );
        String defaultTooltip = StatCollector.translateToLocal( "noshift.tooltip" );
        boolean isShiftPressed = Keyboard.isKeyDown( Keyboard.KEY_LSHIFT );

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed)
        {
            info.addAll( MiscUtils.splitStringPerWord( tooltip,
                                                       5 ) );
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 &&
                 tooltip != null && tooltip.length() > 0 && ! isShiftPressed)
        {
            info.addAll( MiscUtils.splitStringPerWord( String.valueOf( defaultTooltip ),
                                                       10 ) );
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        // Tells minecraft to make more than one pass in rendering this item so we can have multiple layers to it.
        return this.getRegisteredItem().requiresMultipleRenderPasses();
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        // Determine how many render passes are inside given sub-item for rendering purposes.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( this.getRegisteredItemBaseName() );
        SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( metadata );
        if (subItem != null)
        {
            return subItem.getRenderPassCount();
        }

        return 1;
    }

    @Override
    /** Returns a list of items with the same ID, but different meta (ex: dye returns 16 items). */ public void getSubItems(int itemID,
                                                                                                                            CreativeTabs creativeTab,
                                                                                                                            List list)
    {
        for (SubItemsArchive subItem : this.getRegisteredItem().getSubItems())
        {
            // Skip items that don't want to be shown in creative menu (example: logo for creative tab).
            if (subItem.isHiddenInCreativeTab())
            {
                continue;
            }

            list.add( new ItemStack( itemID,
                                     1,
                                     subItem.getMetaID() ) );
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        // Determine if the stack is one of ours.
        ItemBasePrefab madStack = null;
        if (stack.getItem() instanceof ItemBasePrefab)
        {
            madStack = (ItemBasePrefab) stack.getItem();
        }

        // String that will become final outputted name.
        StringBuilder itemNameObject = new StringBuilder();
        itemNameObject.append( "item." );
        itemNameObject.append( this.getRegisteredItemBaseName() );

        // Grab the instance of the item as we know it from item factory.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( madStack.getRegisteredItemBaseName() );
        if (referenceItemProduct != null)
        {
            // Grab the sub-item based on damage value from given stack.
            SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( stack.getItemDamage() );
            if (subItem != null)
            {
                // If sub-item exists then add additional meta item name onto base item name to keep item names organized.
                itemNameObject.append( "." );
                itemNameObject.append( subItem.getItemName() );
            }
        }

        // Returns the string builder object with or without sub-item additions.
        return itemNameObject.toString();
    }

    @Override
    public String getUnlocalizedName()
    {
        return "item." + this.getRegisteredItem().getItemBaseName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int damage, int pass)
    {
        // Grab the instance of the item as we know it from item factory.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( this.getRegisteredItemBaseName() );
        SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( damage );
        if (subItem != null)
        {
            // Grabs required icon for sub-item for given render pass.
            return clientRegisteredIcons.get( subItem.getItemName() + "_" +
                                              pass );
        }

        // Default response is to return default item icon.
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        // Determine if the stack is one of ours.
        ItemBasePrefab madStack = null;
        if (stack.getItem() instanceof ItemBasePrefab)
        {
            madStack = (ItemBasePrefab) stack.getItem();
        }

        // Return the color white if we cannot find the information we need.
        if (madStack == null)
        {
            return 16777215;
        }

        // Grab the instance of the item as we know it from item factory.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( madStack.getRegisteredItemBaseName() );

        // Grab the correct color based on item damage from sub-item archive.
        if (referenceItemProduct != null)
        {
            // Grab the sub-item for this damage value.
            SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( stack.getItemDamage() );
            if (subItem != null)
            {
                // Grabs the color that this particular sub-item for given render pass.
                return subItem.getColorForPass( pass );
            }
        }

        // Default response is return the RGB value for the color white.
        return 16777215;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass)
    {
        // Use the mapping to get Icons populated by registerIcons() to a given render pass.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( this.getRegisteredItemBaseName() );
        SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( stack.getItemDamage() );
        if (subItem != null)
        {
            // Grabs required icon for sub-item for given render pass.
            return clientRegisteredIcons.get( subItem.getItemName() + "_" +
                                              pass );
        }

        // Default response is to return item default icon.
        return this.itemIcon;
    }

    @Override
    public Icon getIconFromDamage(int damage)
    {
        // Use the mapping to get Icons populated by registerIcons() to a given render pass.
        ItemFactoryProduct referenceItemProduct =
                ItemFactory.instance().getItemInfo( this.getRegisteredItemBaseName() );
        SubItemsArchive subItem = referenceItemProduct.getSubItemByDamageValue( damage );
        if (subItem != null)
        {
            // Grabs required icon for sub-item for given render pass.
            return clientRegisteredIcons.get( subItem.getItemName() + "_0" );
        }

        // Default response is to return item default icon.
        return this.itemIcon;
    }

    public String getRegisteredItemBaseName()
    {
        return this.getRegisteredItem().getItemBaseName();
    }

    public ItemFactoryProduct getRegisteredItem()
    {
        // Only query and recreate the registered item if we need it.
        if (this.registeredItem == null)
        {
            ItemFactoryProduct reloadedItem = ItemFactory.instance().getItemInfo( this.registeredItemName );
            this.registeredItem = reloadedItem;
            this.registeredItemName = reloadedItem.getItemBaseName();
        }

        return this.registeredItem;
    }

    @Override
    public boolean canHarvestBlock(Block block)
    {
        // Determines if this item can break and pickup blocks and tiles.
        return this.getRegisteredItem().canHarvestBlocks();
    }

    public int getDamageVsEntity(Entity entity)
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
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        // Determines how quickly this item will break down blocks if can harvest returns true.
        return this.getRegisteredItem().getDamageVSBlock();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegistry)
    {
        // Grab the subtypes array for this item (some will contain one and others many).
        SubItemsArchive[] subItemsArray = this.getRegisteredItem().getSubItems();
        if (subItemsArray != null)
        {
            // Loop through all sub-items and grab rendering pass data.
            for (SubItemsArchive subItem : subItemsArray)
            {
                // Grab all of the render passes that this sub-item will need.
                RenderPassArchive[] itemRenderPasses = subItem.getRenderPassArchive();

                // Associate via mapping render passes to a given registered icon.
                for (RenderPassArchive renderPassObject : itemRenderPasses)
                {
                    //                    // Register a single icon for those items which have but one.
                    //                    if (this.getRegisteredItem().getRenderPassCount() == 1 && renderPassObject.getRenderPass() == 0)
                    //                    {
                    //                        // Use the zero index (first) item in the icon archive that should be primary icon even if no sub-types.
                    //                        this.itemIcon = iconRegistry.registerIcon(MadMod.ID + ":" + renderPassObject.getIconPath());
                    //                        this.iconString = itemIcon.getIconName();
                    //                    }

                    // Items with multiple render passes have their icons registered in a mapping local to the item instance.
                    clientRegisteredIcons.put( subItem.getItemName() + "_" +
                                               renderPassObject.getRenderPass(),
                                               iconRegistry.registerIcon( ModMetadata.ID +
                                                                          ":" +
                                                                          renderPassObject.getIconPath() ) );
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return super.onItemRightClick( par1ItemStack,
                                       par2World,
                                       par3EntityPlayer );
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return super.onLeftClickEntity( stack,
                                        player,
                                        entity );
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        super.onUpdate( par1ItemStack,
                        par2World,
                        par3Entity,
                        par4,
                        par5 );
    }
}
