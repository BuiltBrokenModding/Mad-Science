package madscience.tiles.clayfurnace;

import com.builtbroken.mc.api.ISave;
import com.builtbroken.mc.api.tile.node.ITileModule;
import com.builtbroken.mc.core.network.IPacketReceiver;
import com.builtbroken.mc.core.network.packet.AbstractPacket;
import com.builtbroken.mc.core.network.packet.PacketTile;
import com.builtbroken.mc.core.network.packet.PacketType;
import com.builtbroken.mc.lib.render.RenderUtility;
import com.builtbroken.mc.lib.transform.vector.Location;
import com.builtbroken.mc.lib.transform.vector.Pos;
import com.builtbroken.mc.prefab.inventory.InventoryUtility;
import com.builtbroken.mc.prefab.recipe.ItemStackWrapper;
import com.builtbroken.mc.prefab.tile.Tile;
import com.builtbroken.mc.prefab.tile.TileModuleMachine;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import madscience.MadConfig;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by robert on 2/7/2015.
 */
public class TileClayFurnace extends TileModuleMachine implements IPacketReceiver
{
    public static boolean dropFurnace = false;

    //Very simple recipe handling since the furnace only supports ore -> Ingot Block recipes
    public static HashMap<ItemStackWrapper, ItemStack> recipeMap = new HashMap();
    public static List<ItemStackWrapper> validFuels = new ArrayList();

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

    public BurnState state = BurnState.IDLE;
    public int cookTime = 0;

    @SideOnly(Side.CLIENT)
    public int animationFrame = 0;

    public TileClayFurnace()
    {
        super("ClayFurnace", Material.clay);
        this.renderNormalBlock = false; //We have a custom renderer
        this.renderTileEntity = true; //Renderer is internal
        hardness = 5.0F;
        resistance = 10.0F;
        this.addInventoryModule(2); //Init inventory
    }

    @Override
    public void update()
    {
        super.update();
        if (state == BurnState.COOKING)
        {
            if (isServer())
            {
                if (canSmelt())
                {
                    cookTime++;
                    if (cookTime >= MadScience.SECOND_IN_TICKS * MadConfig.CLAYFURNACE_COOKTIME_IN_SECONDS)
                    {
                        cookTime = 0;
                        state = BurnState.SMOLDERING;
                        setInventorySlotContents(1, null);
                    }
                }
                else
                {
                    //Just in case someone removes items, reset to start
                    state = BurnState.IDLE;

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
                if (ticks % MadScience.SECOND_IN_TICKS * 5 == 0)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "fire.fire", 1.0F, 1.0F);
                }
            }
        }
        else if (state == BurnState.COOLING)
        {
            //TODO make cool faster if near a water source - Dark
            //TODO Prevent cooling if near lava - Dark
            if (isServer())
            {
                cookTime++;
                if (cookTime >= MadScience.SECOND_IN_TICKS * 50)
                {
                    state = BurnState.DONE;
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
        if (state == BurnState.IDLE)
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
                            if(getStackInSlot(0) != null)
                            {
                                //Take item from player
                                if(!player.capabilities.isCreativeMode)
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
                            if(getStackInSlot(1) != null)
                            {
                                //Take item from player
                                if(!player.capabilities.isCreativeMode)
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
                            this.state = BurnState.COOKING;

                            //Play flint sound effect
                            world().playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "fire.ignite", 1.0F, 1.0F);

                            //Damage flint and steel item
                            if (player.getHeldItem().getItemDamage() < player.getHeldItem().getMaxDamage())
                            {
                                player.getCurrentEquippedItem().attemptDamageItem(1, worldObj.rand);
                            }

                            //Send changes to client
                            updateClient();
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
            else if(!player.isSneaking())
            {
                return true;
            }
        }
        else if (!player.isSneaking())
        {
            if (isServer())
            {
                if (state == BurnState.DONE)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*It looks like its finally cooled down*"));
                }
                else if (state == BurnState.COOLING)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*It looks a bit hot, best not touch it*"));
                }
                else if (state == BurnState.COOKING)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*Just waiting for it to melt*"));
                }
                else if (state == BurnState.SMOLDERING)
                {
                    //TODO add translation key - Dark
                    player.addChatComponentMessage(new ChatComponentText("*Now to break the shell and let the molten core cool*"));
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onPlayerLeftClick(EntityPlayer player)
    {
        if (isServer() && player.canHarvestBlock(this.getTileBlock()))
        {
            if (state == BurnState.DONE)
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
            else if (state == BurnState.SMOLDERING)
            {
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "dig.sand", 1.0F, 1.0F);
                world().playSoundEffect(x() + 0.5D, y() + 0.5D, z() + 0.5D, "random.fizz", 1.0F, 1.0F);
                state = BurnState.COOLING;
            }
            else if (state == BurnState.COOLING)
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
        if (state == BurnState.IDLE || state == BurnState.COOKING)
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

    @Override @SideOnly(Side.CLIENT)
    public IIcon getIcon()
    {
        //Use clay texture for breaking animation
        return Blocks.hardened_clay.getIcon(0, 0);
    }

    @Override @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        //We have no icons to register
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderDynamic(Pos pos, float frame, int pass)
    {
        GL11.glPushMatrix();
        RenderUtility.disableLighting();

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TexturesClayFurnace.getTextureBasedOnState(this, state));

        // If we are a clay furnace we have to shrink our scale, otherwise render normal size.
        if (state != TileClayFurnace.BurnState.SMOLDERING)
        {
            GL11.glTranslatef(pos.xf() + 0.5f, pos.yf() + 0.34f, pos.zf() + 0.5f);
            GL11.glScalef(0.6F, 0.68F, 0.6F);
        }
        else
        {
            GL11.glTranslatef(pos.xf() + 0.5f, pos.yf() + 0.5f, pos.zf() + 0.5f);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
        }

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

        if (state == TileClayFurnace.BurnState.COOLING)
        {
            TexturesClayFurnace.MODEL.renderOnly("MoltenBlock");
        }
        else if (state == TileClayFurnace.BurnState.DONE)
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
        sendPacket(getDescPacket());
    }

    @Override
    public PacketTile getDescPacket()
    {
        return new PacketTile(this, state.ordinal(), cookTime);
    }


    @Override
    public void read(ByteBuf buf, EntityPlayer player, PacketType packet)
    {
        if(isClient())
        {
            state = BurnState.get(buf.readInt());
            cookTime = buf.readInt();
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
        nbt.setByte("burnState", (byte)state.ordinal());
        nbt.setInteger("cookTime", cookTime);
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
            if(s >= 0 && s < BurnState.values().length)
                return BurnState.values()[s];
            return DONE;
        }
    }
}
