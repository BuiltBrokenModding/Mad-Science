package madscience.metaitems;

import java.util.List;

import madscience.MadComponents;
import madscience.MadEntities;
import madscience.MadScience;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MainframeComponents extends Item
{
    @SideOnly(Side.CLIENT)
    public static Icon[] icons;

    public MainframeComponents(int itemID)
    {
        super(itemID);

        // Determines how many of an item can be in one stack
        maxStackSize = 64;

        // Sets the creative tab the item is in
        setCreativeTab(MadEntities.tabMadScience);
        
        // Sets unlocalized name that showed up in language registry.
        setUnlocalizedName(MadComponents.MAINFRAME_COMPONENTS_INTERNALNAME);

        // Allows the item to be marked as a metadata item.
        setHasSubtypes(true);

        // Makes it so your item doesn't have the damage bar at the bottom of
        // its icon, when "damaged" similar to the Tools.
        setMaxDamage(0);
        
        // No components may be repaired.
        setNoRepair();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damage)
    {
        // Returns proper icon based on damage value.
        return icons[damage];
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
        // Creates all the meta items associated with the single item ID.
        for (int i = 0; i < icons.length; i++)
        {
            ItemStack itemstack = new ItemStack(id, 1, i);
            list.add(itemstack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        // References external list for internal name based on damage value.
        return MainframeComponentsMetadata.getInternalNameFromDamage[itemstack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister icon)
    {
        // Reference external list of internal item names which also double as texture names.
        icons = new Icon[MainframeComponentsMetadata.getInternalNameFromDamage.length];

        // Register all possible icons that this item may be.
        for (int i = 0; i < icons.length; i++)
        {
            icons[i] = icon.registerIcon(MadScience.ID + ":" + MainframeComponentsMetadata.getInternalNameFromDamage[i]);
        }
    }
}
