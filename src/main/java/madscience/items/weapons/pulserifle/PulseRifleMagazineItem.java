package madscience.items.weapons.pulserifle;

import java.util.List;

import madscience.MadEntities;
import madscience.MadScience;
import madscience.MadWeapons;
import madscience.util.MadTechneModel;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PulseRifleMagazineItem extends Item implements IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private MadTechneModel MODEL = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".mad");
    private ResourceLocation TEXTURE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".png");

    public PulseRifleMagazineItem(int itemID)
    {
        super(itemID);
        this.setUnlocalizedName(MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME);
        this.setCreativeTab(MadEntities.tabMadScience);

        // You cannot repair magazines since we use them to measure how many bullets are inside of them.
        this.setNoRepair();

        // Magazines can hold up to 99 bullets.
        this.setMaxDamage(99);

        // Magazines will stack up to 64 like normal minecraft items.
        this.setMaxStackSize(64);

        // We do not have any sub-types.
        this.setHasSubtypes(false);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List info, boolean par4)
    {
        // Display localalized tooltip for the item from the language file that is currently active.
        if (par1ItemStack.getItemDamage() == 0)
        {
            // Magazine has no bullets inside of it.
            info.add("Empty Magazine");
        }
        else
        {
            // Magazine has bullets inside of it.
            String tooltip = StatCollector.translateToLocal("item." + MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME + ".tooltip");
            if (tooltip != null && tooltip.length() > 0)
            {
                // Replace the tooltip with information about current rounds.
                tooltip = tooltip.replace("%0", String.valueOf(this.getDamage(par1ItemStack)));
                tooltip = tooltip.replace("%1", String.valueOf(this.getMaxDamage()));

                info.addAll(MadUtils.splitStringPerWord(tooltip, 4));
            }
        }
    }

    @Override
    public boolean canHarvestBlock(Block par1Block)
    {
        return false;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        // Keep the magazine on the crafting table.
        return false;
    }

    @Override
    public int getItemEnchantability()
    {
        // Weapons are not enchantable.
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        // Displays the localized name for the item from the language file that is currently active.
        String name = ("item." + StatCollector.translateToLocal(MadWeapons.WEAPONITEM_PULSERIFLE_MAGAZINE_INTERNALNAME)).trim();
        return name;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
        case ENTITY:
        case EQUIPPED:
        case EQUIPPED_FIRST_PERSON:
        case INVENTORY:
            return true;
        default:
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(MadScience.ID + ":" + (this.getUnlocalizedName().substring(5)));
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();

        // Use the same texture we do on the block normally.
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

        // adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type)
        {
        case EQUIPPED:
        {
            float scale = 0.20F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(3.0F, -1.0F, 2.0F);
            GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(42.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(22.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_CULL_FACE);
            transformationToBeUndone = TransformationTypes.THIRDPERSONEQUIPPED;
            break;
        }
        case EQUIPPED_FIRST_PERSON:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(8.66F, 9.0F, 3.0F);
            GL11.glRotatef(142.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        case INVENTORY:
        {
            float scale = 0.48F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(2.0F, -0.5F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            GL11.glRotatef(-45.0F, 0.5F, 0.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.INVENTORY;
            break;
        }
        case ENTITY:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            transformationToBeUndone = TransformationTypes.DROPPED;
            break;
        }
        default:
            break; // never here
        }

        // Renders the model in the gameworld at the correct scale.
        MODEL.renderAll();
        GL11.glPopMatrix();

        switch (transformationToBeUndone)
        {
        case NONE:
        {
            break;
        }
        case DROPPED:
        {
            GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            float scale = 1.0F;
            GL11.glScalef(scale, scale, scale);
            break;
        }
        case INVENTORY:
        {
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            break;
        }
        case THIRDPERSONEQUIPPED:
        {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
        default:
            break;
        }
    }

    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        // Prevents us having to rotate the weapon 180 degrees in renderer.
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (type)
        {
        case ENTITY:
        {
            return (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D);
        }
        case EQUIPPED:
        {
            return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case EQUIPPED_FIRST_PERSON:
        {
            return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
        }
        case INVENTORY:
        {
            return (helper == ItemRendererHelper.INVENTORY_BLOCK);
        }
        default:
        {
            return false;
        }
        }
    }
}
