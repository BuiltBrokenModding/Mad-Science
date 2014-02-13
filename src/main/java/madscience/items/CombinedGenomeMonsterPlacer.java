package madscience.items;

import java.util.Collection;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.GenomeRegistry;
import madscience.MadEntities;
import madscience.MadScience;
import madscience.metaitems.MainframeComponentsMetadata;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class CombinedGenomeMonsterPlacer extends Item
{
    private Icon genomeReelLayer1;
    private Icon genomeReelLayer2;

    public CombinedGenomeMonsterPlacer(int id)
    {
        super(id);
        setHasSubtypes(true);
        this.setUnlocalizedName("genomeMonsterPlacer");

        this.setNoRepair();
        this.setCreativeTab(MadEntities.tabMadScience);

        // Define that we can have normal stack of items.
        this.maxStackSize = 1;
    }

    @Override
	public int getColorFromItemStack(ItemStack stack, int par2)
    {
        MadGenomeInfo info = GenomeRegistry.getGenomeInfo((short) stack.getItemDamage());

        // Return white if info is null.
        if (info == null)
        {
            return 16777215;
        }

        // Prevents coloring of the third render pass (our base of the icon).
        if (par2 == 2)
        {
            return 16777215;
        }

        int color = (par2 == 0) ? info.primaryColor : info.secondaryColor;

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (par2 == 0 && compound.hasKey("primaryColor"))
                color = compound.getInteger("primaryColor");
            if (par2 != 0 && compound.hasKey("secondaryColor"))
                color = compound.getInteger("secondaryColor");
        }

        return color;
    }

    private static void addNBTData(Entity entity, NBTTagCompound spawnData)
    {
        NBTTagCompound newTag = new NBTTagCompound();
        entity.writeToNBTOptional(newTag);

        for (NBTBase nbt : (Collection<NBTBase>) spawnData.getTags())
            newTag.setTag(nbt.getName(), nbt.copy());

        entity.readFromNBT(newTag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass)
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

    public int getDamageVsEntity(Entity par1Entity)
    {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int stack, int pass)
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
    public String getUnlocalizedName(ItemStack stack)
    {
        String name = ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim();
        MadGenomeInfo info = GenomeRegistry.getGenomeInfo((short) stack.getItemDamage());

        if (info == null)
            return name;

        String mobID = info.mobID;
        String displayName = info.displayName;

        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("mobID"))
                mobID = compound.getString("mobID");
            if (compound.hasKey("displayName"))
                displayName = compound.getString("displayName");
        }

        name = "item." + mobID;
        return name;
    }

    @Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
        for (MadGenomeInfo info : GenomeRegistry.getGenomeInfoList())
            list.add(new ItemStack(par1, 1, info.genomeID));
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
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel_overlay");
        this.genomeReelLayer1 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel1");
        this.genomeReelLayer2 = par1IconRegister.registerIcon(MadScience.ID + ":genomeDataReel2");

        this.iconString = itemIcon.getIconName();
    }
}