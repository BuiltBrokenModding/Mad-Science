package madscience.tiles.clayfurnace;

import com.builtbroken.mc.lib.transform.vector.Location;
import com.builtbroken.mc.lib.transform.vector.Pos;
import com.builtbroken.mc.prefab.inventory.InventoryUtility;
import com.builtbroken.mc.prefab.recipe.ItemStackWrapper;
import com.builtbroken.mc.prefab.tile.Tile;
import com.builtbroken.mc.prefab.tile.TileModuleMachine;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.common.block.Item;
import madscience.MadConfig;
import madscience.MadScience;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by robert on 2/7/2015.
 */
public class TileClayFurnace extends TileModuleMachine
{
    public static boolean dropFurnace = false;
    public static HashMap<ItemStackWrapper, ItemStack> oreToResultMap = new HashMap();
    public static List<ItemStackWrapper> validCoalBlocks = new ArrayList();

    static
    {
        //Init recipes
        oreToResultMap.put(new ItemStackWrapper(Blocks.iron_ore), new ItemStack(Blocks.iron_block));
        oreToResultMap.put(new ItemStackWrapper(Blocks.gold_ore), new ItemStack(Blocks.gold_block));
        //TODO add VE ores - Dark

        //Init fuels
        validCoalBlocks.add(new ItemStackWrapper(Blocks.coal_block));
        //TODO add mekanism charcoal block - Dark
    }

    public BurnState state = BurnState.IDLE;
    public int cookTime = 0;

    @SideOnly(Side.CLIENT)
    public int animationFrame = 0;

    public TileClayFurnace()
    {
        super("ClayFurnace", Material.clay);
        hardness = 5.0F;
        resistance = 10.0F;
        this.addInventoryModule(2);
    }

    @Override @SideOnly(Side.CLIENT)
    public void onClientRegistered()
    {
        RenderClayfurnace renderer = new RenderClayfurnace();
        ClientRegistry.bindTileEntitySpecialRenderer(this.getClass(), renderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(getTileBlock()), renderer);
    }

    public void updateClient()
    {

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
                    if(getStackInSlot(0) != null && !oreToResultMap.containsKey(getStackInSlot(0)))
                    {
                        InventoryUtility.dropItemStack(new Location((TileEntity)this), getStackInSlot(0));
                        setInventorySlotContents(0, null);
                    }
                    if(getStackInSlot(1) != null && !validCoalBlocks.contains(getStackInSlot(1)))
                    {
                        InventoryUtility.dropItemStack(new Location((TileEntity)this), getStackInSlot(1));
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
        return oreToResultMap.containsKey(getStackInSlot(0)) && validCoalBlocks.contains(getStackInSlot(1));
    }

    @Override
    protected boolean onPlayerRightClick(EntityPlayer player, int side, Pos hit)
    {
        if (state == BurnState.IDLE && !player.isSneaking())
        {
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null && (oreToResultMap.containsKey(heldItem) || heldItem.getItem() == Items.flint_and_steel || validCoalBlocks.contains(heldItem)))
            {
                if (isServer())
                {
                    //TODO add sneak right clicks support to take the item back out - Dark
                    if (oreToResultMap.containsKey(heldItem))
                    {
                        if (getStackInSlot(0) == null)
                        {
                            //Add item to inventory
                            ItemStack copy = heldItem.copy();
                            copy.stackSize = 1;
                            setInventorySlotContents(0, copy);

                            //Take item from player
                            heldItem.stackSize--;
                            if (heldItem.stackSize <= 0)
                                player.inventory.mainInventory[player.inventory.currentItem] = null;
                            player.inventoryContainer.detectAndSendChanges();

                            //Send changes to client
                            updateClient();
                        }
                        else
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("Furnace already contains an ore block"));
                        }
                    }
                    else if (validCoalBlocks.contains(heldItem))
                    {
                        if (getStackInSlot(1) == null)
                        {
                            //Add item to inventory
                            ItemStack copy = heldItem.copy();
                            copy.stackSize = 1;
                            setInventorySlotContents(1, copy);

                            //Take item from player
                            heldItem.stackSize--;
                            if (heldItem.stackSize <= 0)
                                player.inventory.mainInventory[player.inventory.currentItem] = null;
                            player.inventoryContainer.detectAndSendChanges();

                            //Send changes to client
                            updateClient();
                        }
                        else
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("Furnace already contains a coal block"));
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
                        else if(getStackInSlot(0) == null)
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("*No point in starting without something to smelt*"));
                        }
                        else if(getStackInSlot(1) == null)
                        {
                            //TODO add translation key - Dark
                            player.addChatComponentMessage(new ChatComponentText("*Needs Fuel*"));
                        }
                    }
                }
                return true;
            }
        }
        else if(state == BurnState.DONE)
        {
            //TODO add translation key - Dark
            player.addChatComponentMessage(new ChatComponentText("*It looks like its finally cooled down*"));
        }
        else if(state == BurnState.COOLING)
        {
            //TODO add translation key - Dark
            player.addChatComponentMessage(new ChatComponentText("*It looks a bit hot, best not touch it*"));
        }
        else if(state == BurnState.COOKING)
        {
            //TODO add translation key - Dark
            player.addChatComponentMessage(new ChatComponentText("*Just waiting for it to melt*"));
        }
        else if(state == BurnState.SMOLDERING)
        {
            //TODO add translation key - Dark
            player.addChatComponentMessage(new ChatComponentText("*Now to break the shell to let the molten core cool*"));
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
                ItemStack finalForm = oreToResultMap.get(getStackInSlot(0));
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
        if(state == BurnState.IDLE)
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
    }
}
