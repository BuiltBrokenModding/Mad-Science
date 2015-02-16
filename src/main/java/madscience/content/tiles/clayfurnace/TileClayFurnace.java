package madscience.content.tiles.clayfurnace;

import com.builtbroken.mc.api.items.ISimpleItemRenderer;
import com.builtbroken.mc.core.network.IPacketReceiver;
import com.builtbroken.mc.core.network.packet.PacketTile;
import com.builtbroken.mc.core.network.packet.PacketType;
import com.builtbroken.mc.core.registry.implement.IPostInit;
import com.builtbroken.mc.lib.render.RenderUtility;
import com.builtbroken.mc.lib.transform.region.Cube;
import com.builtbroken.mc.lib.transform.vector.Location;
import com.builtbroken.mc.lib.transform.vector.Pos;
import com.builtbroken.mc.prefab.inventory.InventoryUtility;
import com.builtbroken.mc.prefab.recipe.ItemStackWrapper;
import com.builtbroken.mc.prefab.tile.Tile;
import com.builtbroken.mc.prefab.tile.TileModuleMachine;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import madscience.MadConfig;
import madscience.MadScience;
import madscience.content.items.ItemBlockTooltip;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by robert on 2/7/2015.
 */
public class TileClayFurnace extends TileModuleMachine implements IPacketReceiver, IPostInit, ISimpleItemRenderer
{
    public static boolean dropFurnace = false;
    public static int MAX_COOK_TIME = MadScience.SECOND_IN_TICKS * MadConfig.CLAYFURNACE_COOKTIME_IN_SECONDS;

    //Very simple recipe handling since the furnace only supports ore -> Ingot Block recipes
    public static HashMap<ItemStackWrapper, ItemStack> recipeMap = new HashMap();
    public static List<ItemStackWrapper> validFuels = new ArrayList();

    public static final Cube COOLING_BOUNDS = new Cube(0.2, 0, 0.2, 0.8, .75, 0.8);

    static
    {
        //Init recipes
        recipeMap.put(new ItemStackWrapper(Blocks.iron_ore), new ItemStack(Blocks.iron_block));
        recipeMap.put(new ItemStackWrapper(Blocks.gold_ore), new ItemStack(Blocks.gold_block));
        //TODO add VE ores - Dark

        //Init fuels
        validFuels.add(new ItemStackWrapper(Blocks.coal_block));
        //TODO add mekanism charcoal block - Dark
    }

    private BurnState state = BurnState.IDLE;
    public int cookTime = 0;
    private int animationFrame = 0;

    public TileClayFurnace()
    {
        super("ClayFurnace", Material.clay);
        this.itemBlock = ItemBlockTooltip.class;
        this.renderNormalBlock = false; //We have a custom renderer
        this.renderTileEntity = true; //Renderer is internal
        hardness = 5.0F;
        resistance = 10.0F;
        this.addInventoryModule(2); //Init inventory
    }

    @Override
    public void onPostInit()
    {
        GameRegistry.addShapedRecipe(new ItemStack(MadScience.blockClayFurnace), "ccc", "cfc", "ccc", 'c', Blocks.hardened_clay, 'f', Blocks.furnace);
    }

    @Override
    public int getLightValue()
    {
        if (getState() == BurnState.COOKING)
            return 10;
        if (getState() == BurnState.COOLING)
            return 5;
        if (getState() == BurnState.SMOLDERING)
            return 3;
        return 0;
    }

    @Override
    public void update()
    {
        super.update();
        if (getState() == BurnState.COOKING)
        {
            if (isServer())
            {
                if (canSmelt())
                {
                    double percent = (double) cookTime / (double) MAX_COOK_TIME;
                    if (percent >= 75)
                    {
                        setAnimationFrame(3);
                    }
                    else if (percent >= 50)
                    {
                        setAnimationFrame(2);
                    }
                    else if (percent >= 25)
                    {
                        setAnimationFrame(1);
                    }
                    else
                    {
                        setAnimationFrame(0);
                    }
                    if (cookTime++ >= MAX_COOK_TIME)
                    {
                        cookTime = 0;
                        setState(BurnState.SMOLDERING);
                        setInventorySlotContents(1, null);
                    }
                }
                else
                {
                    //Just in case someone removes items, reset to start
                    setState(BurnState.IDLE);

                    //Spit invalid items out
                    //TODO drop in front of tile - Dark
                    if (getStackInSlot(0) != null && !isValidOreBlock(getStackInSlot(0)))
                    {
                        InventoryUtility.dropItemStack(new Location((TileEntity) this), getStackInSlot(0));
                        setInventorySlotContents(0, null);
                    }
                    if (getStackInSlot(1) != null && !isValidFuel(getStackInSlot(1)))
                    {
                        InventoryUtility.dropItemStack(new Location((TileEntity) this), getStackInSlot(1));
                        setInventorySlotContents(1, null);
                    }
                }
            }
            else
            {
                if (ticks % 5 == 0)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "fire.fire", 1.0F, 1.0F);
                    for (int l = 0; l < 2; ++l)
                    {
                        double f = x() + 0.5 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        double f1 = y() + 0.9 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        double f2 = z() + 0.5 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        world().spawnParticle("largesmoke", f, f1, f2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        else if (getState() == BurnState.COOLING)
        {
            //TODO make cool faster if near a water source - Dark
            //TODO Prevent cooling if near lava - Dark
            if (isServer())
            {
                double percent = (double) cookTime / (double) (MadScience.SECOND_IN_TICKS * 50);
                if (percent >= 80)
                {
                    setAnimationFrame(4);
                }
                else if (percent >= 60)
                {
                    setAnimationFrame(3);
                }
                else if (percent >= 40)
                {
                    setAnimationFrame(2);
                }
                else if (percent >= 20)
                {
                    setAnimationFrame(1);
                }
                else
                {
                    setAnimationFrame(0);
                }
                cookTime++;
                if (cookTime >= MadScience.SECOND_IN_TICKS * 50)
                {
                    setState(BurnState.DONE);
                }
            }
            else
            {
                if (ticks % MadScience.SECOND_IN_TICKS * 5 == 0)
                {
                    //TODO this.createRandomSmoke();
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);
                }
            }
        }
    }

    public boolean canSmelt()
    {
        return isValidOreBlock(getStackInSlot(0)) && isValidFuel(getStackInSlot(1));
    }

    public boolean isValidOreBlock(ItemStack stack)
    {
        //NBT compare is turned off to fix a slight glitch with NEI
        return stack != null && recipeMap.containsKey(new ItemStackWrapper(stack).setNBTCompare(false).setStackCompare(false));
    }

    public boolean isValidFuel(ItemStack stack)
    {
        //NBT compare is turned off to fix a slight glitch with NEI
        return stack != null && validFuels.contains(new ItemStackWrapper(stack).setNBTCompare(false).setStackCompare(false));
    }

    @Override
    protected boolean onPlayerRightClick(EntityPlayer player, int side, Pos hit)
    {
        if (getState() == BurnState.IDLE)
        {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null && (isValidOreBlock(heldItem) || heldItem.getItem() == Items.flint_and_steel || isValidFuel(heldItem)))
            {
                if (isServer())
                {
                    if (isValidOreBlock(heldItem))
                    {
                        if (getStackInSlot(0) == null)
                        {
                            //Add item to inventory
                            ItemStack copy = heldItem.copy();
                            copy.stackSize = 1;
                            setInventorySlotContents(0, copy);
                            if (getStackInSlot(0) != null)
                            {
                                //Take item from player
                                if (!player.capabilities.isCreativeMode)
                                {
                                    heldItem.stackSize--;
                                    if (heldItem.stackSize <= 0)
                                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                                    player.inventoryContainer.detectAndSendChanges();
                                }
                                //TODO add translation key - Dark
                                player.addChatComponentMessage(new ChatComponentText("Ore added"));

                                //Send changes to client
                                updateClient();
                            }
                        }
                        else
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("Furnace already contains an ore block. Sneak-right click with empty hand to remove."));
                        }
                    }
                    else if (isValidFuel(heldItem))
                    {
                        if (getStackInSlot(1) == null)
                        {
                            //Add item to inventory
                            ItemStack copy = heldItem.copy();
                            copy.stackSize = 1;
                            setInventorySlotContents(1, copy);
                            if (getStackInSlot(1) != null)
                            {
                                //Take item from player
                                if (!player.capabilities.isCreativeMode)
                                {
                                    heldItem.stackSize--;
                                    if (heldItem.stackSize <= 0)
                                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                                    player.inventoryContainer.detectAndSendChanges();
                                }
                                //TODO add translation key - Dark
                                player.addChatComponentMessage(new ChatComponentText("Fuel added"));

                                //Send changes to client
                                updateClient();
                            }
                        }
                        else
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("Furnace already contains a coal block. Sneak-right click with empty hand to remove."));
                        }
                    }
                    else if (heldItem.getItem() == Items.flint_and_steel)
                    {
                        //Only start burning the furnace if we have a valid recipe
                        if (canSmelt())
                        {
                            //State burn process
                            this.setState(BurnState.COOKING);

                            //Play flint sound effect
                            world().playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "fire.ignite", 1.0F, 1.0F);

                            //Damage flint and steel item
                            if (player.getHeldItem().getItemDamage() < player.getHeldItem().getMaxDamage())
                            {
                                player.getCurrentEquippedItem().attemptDamageItem(1, worldObj.rand);
                            }
                        }
                        else if (getStackInSlot(0) == null)
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("*No point in starting without something to smelt*"));
                        }
                        else if (getStackInSlot(1) == null)
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("*Needs Fuel*"));
                        }
                    }
                }
                return true;
            }
            else if (heldItem == null && player.isSneaking())
            {
                if (isServer() && (getStackInSlot(0) != null || getStackInSlot(1) != null))
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*Items removed from furnace*"));
                    for (int i = 0; i <= 1; i++)
                    {
                        if (getStackInSlot(i) != null)
                        {
                            ItemStack stack = getStackInSlot(i).copy();
                            setInventorySlotContents(i, null);
                            if (!player.inventory.addItemStackToInventory(stack))
                            {
                                InventoryUtility.dropItemStack(new Location((TileEntity) this), stack);
                            }
                        }
                    }
                    player.inventoryContainer.detectAndSendChanges();
                }
                return true;
            }
        }
        else if (getState() == BurnState.COOKING && player.getHeldItem() != null)
        {
            if (player.getHeldItem().getItem() == Items.water_bucket)
            {
                if (isServer())
                {
                    InventoryUtility.consumeBucketInHand(player);
                    setState(BurnState.IDLE);
                }
                else
                {
                    for (int l = 0; l < 20; ++l)
                    {
                        double f = x() + 0.5 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        double f1 = y() + 0.9 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        double f2 = z() + 0.5 + 0.2 * (world().rand.nextFloat() - world().rand.nextFloat());
                        world().spawnParticle("largesmoke", f, f1, f2, 0.0D, 0.0D, 0.0D);
                    }
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);
                }
            }
            else if (player.getHeldItem().getItem() == Items.lava_bucket)
            {
                if (isServer())
                {
                    InventoryUtility.consumeBucketInHand(player);
                    setState(BurnState.SMOLDERING);
                }
                else
                {
                    world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "liquid.lavapop", 1.0F, 1.0F);
                    world().playSoundEffect(z() + 0.5D, y() + 0.5D, z() + 0.5D, "random.fizz", 1.0F, 1.0F);
                }
            }
            return true;
        }
        else if (!player.isSneaking())
        {
            if (isServer())
            {
                if (getState() == BurnState.DONE)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*It looks like its finally cooled down*"));
                }
                else if (getState() == BurnState.COOLING)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*It looks a bit hot, best not touch it*"));
                }
                else if (getState() == BurnState.COOKING)
                {
                    //TODO add translation key - Dark
                    int percent = (int) (100 * ((double) cookTime / (double) MAX_COOK_TIME));

                    //TODO add witty remarks about the long ass wait time
                    player.addChatComponentMessage(new ChatComponentText("* " + percent + "%  Just waiting for it to melt*"));
                }
                else if (getState() == BurnState.SMOLDERING)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*Now to break the shell and let the molten core cool*"));
                }
            }
            return true;
        }
        return !player.isSneaking();
    }

    @Override
    public boolean onPlayerLeftClick(EntityPlayer player)
    {
        if (isServer() && player.canHarvestBlock(this.getTileBlock()))
        {
            if (getState() == BurnState.DONE)
            {
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "random.anvil_land", 1.0F, 1.0F);

                // Set ourselves to the end result we should be!
                ItemStack finalForm = recipeMap.get(new ItemStackWrapper(getStackInSlot(0)));
                if (finalForm != null)
                {
                    Block block = Block.getBlockFromItem(finalForm.getItem());
                    if (block != null)
                    {
                        world().setBlock(xCoord, yCoord, zCoord, block, finalForm.getItemDamage(), 3);
                    }
                    else
                    {
                        InventoryUtility.dropItemStack(new Location((TileEntity) this).add(0.5), finalForm);
                        world().setBlockToAir(xCoord, yCoord, zCoord);
                    }
                }
            }
            else if (getState() == BurnState.SMOLDERING)
            {
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "dig.sand", 1.0F, 1.0F);
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "random.fizz", 1.0F, 1.0F);
                setState(BurnState.COOLING);
            }
            else if (getState() == BurnState.COOLING)
            {
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "liquid.lavapop", 1.0F, 1.0F);
                world().playSoundEffect(z() + 0.5D, y() + 0.5D, z() + 0.5D, "random.fizz", 1.0F, 1.0F);
                world().setBlock(zi(), yi(), zi(), Blocks.lava);
            }
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(int metadata, int fortune)
    {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        if (getState() == BurnState.IDLE || getState() == BurnState.COOKING)
        {
            if (dropFurnace)
            {
                items.add(toItemStack());
            }
            else
            {
                items.add(new ItemStack(Blocks.hardened_clay, Math.max(3 + world().rand.nextInt(5), 8)));
                items.add(new ItemStack(Blocks.cobblestone, Math.max(3 + world().rand.nextInt(5), 8)));
            }
            if (getStackInSlot(0) != null)
            {
                items.add(getStackInSlot(0));
            }
            if (getStackInSlot(1) != null)
            {
                items.add(getStackInSlot(1));
            }
        }
        return items;
    }

    @Override
    public Tile newTile()
    {
        return new TileClayFurnace();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon()
    {
        //Use clay texture for breaking animation
        return Blocks.hardened_clay.getIcon(0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        //We have no icons to register
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryItem(IItemRenderer.ItemRenderType type, ItemStack itemStack, Object... data)
    {
        GL11.glPushMatrix();

        // Use the same texture we do on the block normally.
        Minecraft.getMinecraft().renderEngine.bindTexture(TexturesClayFurnace.TEXTURE_IDLE);

        // adjust rendering space to match what caller expects
        switch (type)
        {
            case EQUIPPED:
            {
                float scale = 1.4F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.1F, 0.3F, 0.3F);
                // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(GL11.GL_CULL_FACE);
                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                float scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.2F, 0.9F, 0.5F);
                // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            }
            case INVENTORY:
            {
                float scale = 0.65F;
                GL11.glScalef(scale, scale, scale);
                // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(270.0F, 0.0F, 0.5F, 0.0F);
                break;
            }
            case ENTITY:
            {
                float scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                // GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            }
            default:
                break; // never here
        }

        // Renders the model in the gameworld at the correct scale.
        TexturesClayFurnace.MODEL.renderAll();
        if (type == IItemRenderer.ItemRenderType.EQUIPPED)
        {
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        GL11.glPopMatrix();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderDynamic(Pos pos, float frame, int pass)
    {
        GL11.glPushMatrix();
        RenderUtility.disableLighting();

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TexturesClayFurnace.getTextureBasedOnState(this, getState()));

        // If we are a clay furnace we have to shrink our scale, otherwise render normal size.

        GL11.glTranslatef(pos.xf() + 0.5f, pos.yf() + 0.34f, pos.zf() + 0.5f);
        GL11.glScalef(0.6F, 0.68F, 0.6F);

        //Rotate model based on tile rotation
        switch (getBlockMetadata() % 4)
        {
            case 0:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 2:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 1:
                GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
                break;
        }

        if (getState() == TileClayFurnace.BurnState.COOLING)
        {
            TexturesClayFurnace.MODEL.renderOnly("MoltenBlock");
        }
        else if (getState() == TileClayFurnace.BurnState.DONE)
        {
            TexturesClayFurnace.MODEL.renderOnly("MoltenBlockShell");
        }
        else
        {
            TexturesClayFurnace.MODEL.renderAllExcept("MoltenBlockShell", "MoltenBlock");
        }
        RenderUtility.enableLighting();
        GL11.glPopMatrix();
    }

    public void updateClient()
    {
        if (isServer())
            sendPacket(getDescPacket());
    }

    @Override
    public void onCollide(Entity entity)
    {
        if(isServer())
        {
            if (entity.attackEntityFrom(DamageSource.inFire, 0.1f))
            {
                entity.setFire(1);
            }
        }
    }

    @Override
    public Cube getCollisionBounds()
    {
        if(state == BurnState.DONE || state == BurnState.COOLING)
        {
            return COOLING_BOUNDS;
        }
        return super.getCollisionBounds();
    }

    @Override
    public PacketTile getDescPacket()
    {
        return new PacketTile(this, getState().ordinal(), animationFrame);
    }


    @Override
    public void read(ByteBuf buf, EntityPlayer player, PacketType packet)
    {
        if (isClient())
        {
            setState(BurnState.get(buf.readInt()));
            animationFrame = buf.readInt();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        state = BurnState.get(nbt.getByte("burnState"));
        cookTime = nbt.getInteger("cookTime");

    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setByte("burnState", (byte) getState().ordinal());
        nbt.setInteger("cookTime", cookTime);
    }

    public BurnState getState()
    {
        return state;
    }

    public void setState(BurnState state)
    {
        if (this.state != state)
        {
            this.state = state;
            updateClient();
        }
    }

    public int getAnimationFrame()
    {
        return animationFrame;
    }

    private void setAnimationFrame(int animationFrame)
    {
        this.animationFrame = animationFrame;
        updateClient();
    }

    /**
     * States of the furnace
     */
    public static enum BurnState
    {
        /* Waiting for items and fire */
        IDLE,
        /**
         * Smelting items
         */
        COOKING,
        /**
         * Done smelting, and wait for casing to be removed
         */
        SMOLDERING,
        /**
         * Cooling off block
         */
        COOLING,
        /**
         * Waiting to be picked up
         */
        DONE;

        public static BurnState get(int s)
        {
            if (s >= 0 && s < BurnState.values().length)
                return BurnState.values()[s];
            return DONE;
        }
    }
}
