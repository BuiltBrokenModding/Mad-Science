package madscience.content.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadEntities;
import madscience.MadScience;
import madscience.util.MadUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class CombinedMemoryMonsterPlacer extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon genomeReelLayer1;

    @SideOnly(Side.CLIENT)
    private IIcon genomeReelLayer2;

    static
    {
        CombinedMemoryEntityList.addMapping("Priest", 32, 5651507, Color.RED.getRGB());
        CombinedMemoryEntityList.addMapping("Farmer", 64, 5651507, Color.ORANGE.getRGB());
        CombinedMemoryEntityList.addMapping("Butcher", 128, 5651507, Color.GREEN.getRGB());
        CombinedMemoryEntityList.addMapping("Blacksmith", 256, 5651507, Color.BLUE.getRGB());
        CombinedMemoryEntityList.addMapping("Librarian", 512, 5651507, Color.MAGENTA.getRGB());
    }

    public CombinedMemoryMonsterPlacer()
    {
        this.setHasSubtypes(true);
        this.setNoRepair();
        this.setCreativeTab(MadEntities.tabMadScience);

        // Define that we can have normal stack of items.
        this.maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        String tooltip = StatCollector.translateToLocal(nameFromDamage(par1ItemStack.getItemDamage()) + ".tooltip");
        String defaultTooltip = StatCollector.translateToLocal("noshift.tooltip");
        boolean isShiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        // Use LWJGL to detect what key is being pressed.
        if (tooltip != null && tooltip.length() > 0 && isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(tooltip, 5));
        }
        else if (defaultTooltip != null && defaultTooltip.length() > 0 && !isShiftPressed)
        {
            info.addAll(MadUtils.splitStringPerWord(String.valueOf(defaultTooltip), 10));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        // Prevents coloring of the third render pass (our base of the icon).
        if (par2 == 2)
        {
            return 16777215;
        }

        EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo) CombinedMemoryEntityList.entityEggs.get(Integer.valueOf(par1ItemStack.getItemDamage()));
        return entityegginfo != null ? (par2 == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor) : 16777215;
    }

    public int getDamageVsEntity(Entity par1Entity)
    {
        return 2;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0)
        {
            return this.genomeReelLayer1;
        }
        else if (pass == 1)
        {
            return this.genomeReelLayer2;
        }
        else if (pass == 2)
        {
            return this.itemIcon;
        }

        return this.itemIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        // Fired by Minecraft/Forge so it can get the icons for different render passes.
        return getIcon(stack, renderPass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamageForRenderPass(int stack, int pass)
    {
        if (pass == 0)
        {
            return this.genomeReelLayer1;
        }
        else if (pass == 1)
        {
            return this.genomeReelLayer2;
        }
        else if (pass == 2)
        {
            return this.itemIcon;
        }

        return this.itemIcon;
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        Iterator iterator = CombinedMemoryEntityList.entityEggs.values().iterator();

        while (iterator.hasNext())
        {
            EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo) iterator.next();
            par3List.add(new ItemStack(par1, 1, entityegginfo.spawnedID));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return nameFromDamage(stack.getItemDamage());
    }

    public String nameFromDamage(int whatDamage)
    {
        // Memory profiles are hard coded.
        String theDefault = "item." + MadEntities.COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME + "name";
        switch (whatDamage)
        {
            case 32:
                return theDefault + ".Priest";
            case 64:
                return theDefault + ".Farmer";
            case 128:
                return theDefault + ".Butcher";
            case 256:
                return theDefault + ".Blacksmith";
            case 512:
                return theDefault + ".Librarian";
        }
        return theDefault;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel_overlay");
        this.genomeReelLayer1 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel1");
        this.genomeReelLayer2 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel2");

        this.iconString = itemIcon.getIconName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
}
