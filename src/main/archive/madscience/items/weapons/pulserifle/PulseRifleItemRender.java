package madscience.content.items.weapons.pulserifle;

import java.util.ArrayList;
import java.util.List;

import madscience.MadScience;
import madscience.MadWeapons;
import madscience.util.MadUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PulseRifleItemRender implements IItemRenderer
{
    private enum TransformationTypes
    {
        DROPPED, INVENTORY, NONE, THIRDPERSONEQUIPPED
    }

    private MadTechneModel MODEL_COUNTER_LEFT = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter0.mad");
    private MadTechneModel MODEL_COUNTER_RIGHT = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter1.mad");
    private MadTechneModel MODEL_FLASH0 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash0.mad");

    private MadTechneModel MODEL_FLASH1 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash1.mad");
    private MadTechneModel MODEL_FLASH2 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash2.mad");
    private MadTechneModel MODEL_FLASH3 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash3.mad");
    private MadTechneModel MODEL_FLASH4 = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash4.mad");
    private MadTechneModel MODEL_RIFLE = (MadTechneModel) AdvancedModelLoader.loadModel(MadScience.MODEL_PATH + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".mad");

    private ResourceLocation TEXTURE_COUNTER_EIGHT = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter8.png");
    private ResourceLocation TEXTURE_COUNTER_FIVE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter5.png");
    private ResourceLocation TEXTURE_COUNTER_FOUR = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter4.png");
    private ResourceLocation TEXTURE_COUNTER_NINE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter9.png");
    private ResourceLocation TEXTURE_COUNTER_ONE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter1.png");
    private ResourceLocation TEXTURE_COUNTER_SEVEN = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter7.png");
    private ResourceLocation TEXTURE_COUNTER_SIX = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter6.png");
    private ResourceLocation TEXTURE_COUNTER_THREE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter3.png");
    private ResourceLocation TEXTURE_COUNTER_TWO = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter2.png");
    private ResourceLocation TEXTURE_COUNTER_ZERO = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_counter0.png");

    private ResourceLocation TEXTURE_FLASH12 = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash12.png");
    private ResourceLocation TEXTURE_FLASH34 = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "_flash34.png");
    private ResourceLocation TEXTURE_RIFLE = new ResourceLocation(MadScience.ID, "models/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + "/" + MadWeapons.WEAPONITEM_PULSERIFLE_INTERNALNAME + ".png");

    private ResourceLocation convertIntegerToCounterTexture(Integer ammoCount)
    {
        switch (ammoCount)
        {
        case 0:
            return TEXTURE_COUNTER_ZERO;
        case 1:
            return TEXTURE_COUNTER_ONE;
        case 2:
            return TEXTURE_COUNTER_TWO;
        case 3:
            return TEXTURE_COUNTER_THREE;
        case 4:
            return TEXTURE_COUNTER_FOUR;
        case 5:
            return TEXTURE_COUNTER_FIVE;
        case 6:
            return TEXTURE_COUNTER_SIX;
        case 7:
            return TEXTURE_COUNTER_SEVEN;
        case 8:
            return TEXTURE_COUNTER_EIGHT;
        case 9:
            return TEXTURE_COUNTER_NINE;
        default:
            return TEXTURE_COUNTER_ZERO;
        }
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

    public void hideMovingParts(int randomizer)
    {
        // Hide 'moving' parts of the pulse rifle by default.
        MODEL_RIFLE.parts.get("Bolt").showModel = true;
        MODEL_RIFLE.parts.get("BoltBack").showModel = false;

        switch (randomizer)
        {
        case 0:
        {
            // Hide the muzzle flash.
            MODEL_FLASH0.parts.get("Flash0").showModel = false;
            MODEL_FLASH0.parts.get("Flash1").showModel = false;
            MODEL_FLASH0.parts.get("Flash2").showModel = false;
            MODEL_FLASH0.parts.get("Flash3").showModel = false;
            MODEL_FLASH0.parts.get("Flash4").showModel = false;
        }
            break;
        case 1:
        {
            MODEL_FLASH1.parts.get("Flash0").showModel = false;
            MODEL_FLASH1.parts.get("Flash1").showModel = false;
            MODEL_FLASH1.parts.get("Flash2").showModel = false;
        }
            break;
        case 2:
        {
            MODEL_FLASH2.parts.get("Flash0").showModel = false;
            MODEL_FLASH2.parts.get("Flash1").showModel = false;
            MODEL_FLASH2.parts.get("Flash2").showModel = false;
        }
            break;
        case 3:
        {
            MODEL_FLASH3.parts.get("Flash0").showModel = false;
            MODEL_FLASH3.parts.get("Flash1").showModel = false;
            MODEL_FLASH3.parts.get("Flash2").showModel = false;
        }
            break;
        case 4:
        {
            MODEL_FLASH4.parts.get("Flash0").showModel = false;
            MODEL_FLASH4.parts.get("Flash1").showModel = false;
            MODEL_FLASH4.parts.get("Flash2").showModel = false;
        }
            break;
        }

        // Hide the pump.
        MODEL_RIFLE.parts.get("PumpFrontBack").showModel = false;
        MODEL_RIFLE.parts.get("PumpBackBack").showModel = false;
        MODEL_RIFLE.parts.get("PumpFront").showModel = true;
        MODEL_RIFLE.parts.get("PumpBack").showModel = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        // Grab client side instance of the players world.
        World clientWorld = MadScience.proxy.getClientWorld();

        // Grab client side instance of the player.
        EntityClientPlayerMP clientEntity = Minecraft.getMinecraft().thePlayer;

        // Quit if there is no client entity.
        if (clientEntity == null)
        {
            return;
        }

        // Quit if there is no client world.
        if (clientWorld == null)
        {
            return;
        }

        // Ensure that we are on a client side only world.
        if (!clientWorld.isRemote)
        {
            return;
        }

        // Initialize variables we will use to communicate the status of the pulse rifle.
        int renderPass = 0;
        int pulseRifleFireTime = 0;
        int previousFireTime = 0;
        int primaryAmmoCount = 0;
        int secondaryAmmoCount = 0;
        boolean primaryFireModeEnabled = true;
        boolean isLeftPressed = false;
        boolean isRightPressed = false;

        // Create NBT data if required.
        if (item.stackTagCompound == null)
        {
            item.stackTagCompound = new NBTTagCompound();
        }

        // Grab NBT data from the item the player is holding.
        if (!item.stackTagCompound.hasNoTags())
        {
            // Client Only - Render Pass
            if (item.stackTagCompound.hasKey("renderPass"))
            {
                renderPass = item.stackTagCompound.getInteger("renderPass");
            }

            // Fire time.
            if (item.stackTagCompound.hasKey("playerFireTime"))
            {
                pulseRifleFireTime = item.stackTagCompound.getInteger("playerFireTime");
            }

            if (item.stackTagCompound.hasKey("previousFireTime"))
            {
                previousFireTime = item.stackTagCompound.getInteger("previousFireTime");
            }

            // Ammo Count
            if (item.stackTagCompound.hasKey("primaryAmmoCount"))
            {
                primaryAmmoCount = item.stackTagCompound.getInteger("primaryAmmoCount");
            }

            if (item.stackTagCompound.hasKey("secondaryAmmoCount"))
            {
                secondaryAmmoCount = item.stackTagCompound.getInteger("secondaryAmmoCount");
            }

            // Firing mode
            if (item.stackTagCompound.hasKey("primaryFireModeEnabled"))
            {
                primaryFireModeEnabled = item.stackTagCompound.getBoolean("primaryFireModeEnabled");
            }

            // Mouse Buttons
            if (item.stackTagCompound.hasKey("isLeftPressed"))
            {
                isLeftPressed = item.stackTagCompound.getBoolean("isLeftPressed");
            }

            if (item.stackTagCompound.hasKey("isRightPressed"))
            {
                isRightPressed = item.stackTagCompound.getBoolean("isRightPressed");
            }
        }

        // Prepare OpenGL for a transformation.
        GL11.glPushMatrix();

        // Split the ammo count up based on firing mode and by digits.
        List<Integer> splitAmmoCount = null;
        if (primaryFireModeEnabled)
        {
            // Bullets.
            splitAmmoCount = MadUtils.splitIntegerPerDigit(primaryAmmoCount);
        }
        else
        {
            // Grenades.
            splitAmmoCount = MadUtils.splitIntegerPerDigit(secondaryAmmoCount);
        }

        // If the ammo count is still somehow null even after all this we will make it zero.
        if (splitAmmoCount == null)
        {
            splitAmmoCount = new ArrayList<Integer>();
            splitAmmoCount.add(0);
            splitAmmoCount.add(0);
        }

        // If the ammo count is zero then add two of them!
        if (splitAmmoCount.size() == 0)
        {
            splitAmmoCount.add(0);
            splitAmmoCount.add(0);
        }

        // Decide what muzzle flash we are going to use this tick.
        int muzzleFlashRandomizer = clientWorld.rand.nextInt(5);

        // Change numbers on pulse rifle to match current firing mode ammo count.
        switch (renderPass)
        {
        case 0:
        {
            // Base Weapon Texture.
            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_RIFLE);
        }
            break;
        case 1:
        {
            // Weapon Counter Digit 1.
            if (splitAmmoCount.size() == 1)
            {
                // If ammo count is a single digit then add a leading zero.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_COUNTER_ZERO);
            }
            else
            {
                // Otherwise, show the other digit.
                Minecraft.getMinecraft().renderEngine.bindTexture(this.convertIntegerToCounterTexture(splitAmmoCount.get(1)));
            }

        }
            break;
        case 2:
        {
            // Weapon Counter Digit 2.
            Minecraft.getMinecraft().renderEngine.bindTexture(this.convertIntegerToCounterTexture(splitAmmoCount.get(0)));
        }
            break;
        case 3:
        {
            // Muzzle flash texture.
            switch (muzzleFlashRandomizer)
            {
            case 0:
            {
                // First muzzle flash is default one which is apart of weapon texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_RIFLE);
            }
                break;
            case 1:
            {
                // Muzzle flash one and two share the same texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH12);
            }
                break;
            case 2:
            {
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH12);
            }
                break;
            case 3:
            {
                // Muzzle flash three and four share the same texture map.
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH34);
            }
                break;
            case 4:
            {
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FLASH34);
            }
                break;
            }
        }
            break;
        }

        // Adjust rendering space to match what caller expects.
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

            // Change position and rotation based on firing status.
            GL11.glScalef(scale, scale, scale);
            if (clientEntity.isUsingItem() && clientEntity.getItemInUse().itemID == MadWeapons.WEAPONITEM_PULSERIFLE.itemID)
            {
                GL11.glTranslatef(5.5F, 5.0F, 4.5F);
                GL11.glRotatef(155.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(25.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(13.0F, 0.0F, 0.0F, 1.0F);
            }
            else
            {
                GL11.glTranslatef(8.5F, 8.0F, 5.5F);
                GL11.glRotatef(142.0F, 0.0F, 1.0F, 0.0F);
            }
            break;
        }
        case INVENTORY:
        {
            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(1.5F, 1.0F, 0.0F);
            GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
            GL11.glRotatef(45.0F, 0.5F, 0.0F, 0.0F);
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

        switch (renderPass)
        {
        case 0:
        {
            // Move the bolt and show muzzle flash on the rifle when firing.
            if (pulseRifleFireTime > 0 && isLeftPressed && primaryAmmoCount > 0 && primaryFireModeEnabled)
            {
                // BULLETS
                if (clientWorld.getWorldTime() % 2F == 0L)
                {
                    this.showMovingParts(muzzleFlashRandomizer);
                }
                else
                {
                    this.hideMovingParts(muzzleFlashRandomizer);
                }
            }
            else if (pulseRifleFireTime > 0 && !primaryFireModeEnabled && isLeftPressed && secondaryAmmoCount > 0)
            {
                // GRENADES
                MODEL_RIFLE.parts.get("PumpFront").showModel = false;
                MODEL_RIFLE.parts.get("PumpBack").showModel = false;
                MODEL_RIFLE.parts.get("PumpFrontBack").showModel = true;
                MODEL_RIFLE.parts.get("PumpBackBack").showModel = true;
            }
            else
            {
                // Weapon in normal firing position.
                this.hideMovingParts(muzzleFlashRandomizer);
            }

            // Renders the base pulse rifle in the gameworld at the correct scale.
            MODEL_RIFLE.renderAll();
        }
            break;
        case 1:
        {
            // Weapon Counter Digit 1.
            GL11.glDisable(GL11.GL_LIGHTING);
            MODEL_COUNTER_LEFT.renderAll();
            GL11.glEnable(GL11.GL_LIGHTING);
        }
            break;
        case 2:
        {
            // Weapon Counter Digit 2.
            GL11.glDisable(GL11.GL_LIGHTING);
            MODEL_COUNTER_RIGHT.renderAll();
            GL11.glEnable(GL11.GL_LIGHTING);
        }
            break;
        case 3:
        {
            if (pulseRifleFireTime > 1 && isLeftPressed && primaryAmmoCount > 0 && primaryFireModeEnabled)
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                // Muzzle Flash
                switch (muzzleFlashRandomizer)
                {
                case 0:
                {
                    MODEL_FLASH0.renderAll();
                }
                    break;
                case 1:
                {
                    MODEL_FLASH1.renderAll();
                }
                    break;
                case 2:
                {
                    MODEL_FLASH2.renderAll();
                }
                    break;
                case 3:
                {
                    MODEL_FLASH3.renderAll();
                }
                    break;
                case 4:
                {
                    MODEL_FLASH4.renderAll();
                }
                    break;
                }
                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }
            break;
        }

        // Pop OpenGL matrix with our latest transformation (bring on the final form jokes!).
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

    public void showMovingParts(int randomizer)
    {
        // Show 'moving' parts of the rifle during firing.
        MODEL_RIFLE.parts.get("Bolt").showModel = false;
        MODEL_RIFLE.parts.get("BoltBack").showModel = true;

        // Show the muzzle flash.
        switch (randomizer)
        {
        case 0:
        {
            MODEL_FLASH0.parts.get("Flash0").showModel = true;
            MODEL_FLASH0.parts.get("Flash1").showModel = true;
            MODEL_FLASH0.parts.get("Flash2").showModel = true;
            MODEL_FLASH0.parts.get("Flash3").showModel = true;
            MODEL_FLASH0.parts.get("Flash4").showModel = true;
        }
            break;
        case 1:
        {
            MODEL_FLASH1.parts.get("Flash0").showModel = true;
            MODEL_FLASH1.parts.get("Flash1").showModel = true;
            MODEL_FLASH1.parts.get("Flash2").showModel = true;
        }
            break;
        case 2:
        {
            MODEL_FLASH2.parts.get("Flash0").showModel = true;
            MODEL_FLASH2.parts.get("Flash1").showModel = true;
            MODEL_FLASH2.parts.get("Flash2").showModel = true;
        }
            break;
        case 3:
        {
            MODEL_FLASH3.parts.get("Flash0").showModel = true;
            MODEL_FLASH3.parts.get("Flash1").showModel = true;
            MODEL_FLASH3.parts.get("Flash2").showModel = true;
        }
            break;
        case 4:
        {
            MODEL_FLASH4.parts.get("Flash0").showModel = true;
            MODEL_FLASH4.parts.get("Flash1").showModel = true;
            MODEL_FLASH4.parts.get("Flash2").showModel = true;
        }
            break;
        }
    }
}
