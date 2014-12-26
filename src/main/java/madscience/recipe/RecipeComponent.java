package madscience.recipe;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import madscience.container.SlotContainerTypeEnum;
import madscience.mod.ModLoader;
import net.minecraft.item.ItemStack;


public class RecipeComponent
{
    /**
     * Determines where an individual recipe item will go in slot.
     */
    @Expose
    @SerializedName("SlotDestination")
    private SlotContainerTypeEnum slotDestination;

    /**
     * Holds reference to the item (meta or otherwise) that we expect in given slot.
     */
    @Expose
    @SerializedName("SlotExpectedItem")
    private String slotExpectedItem;

    /**
     * Reference to how many of the given item we should have for this slot.
     */
    @Expose
    @SerializedName("SlotExpectedAmount")
    private int slotExpectedAmount;

    /**
     * Reference to mod ID the given item should come from.
     */
    @Expose
    @SerializedName("ParentModID")
    private String parentModID;

    /**
     * Reference to how much damage this item has or in the case of meta items determines which one it will be.
     */
    @Expose
    @SerializedName("MetaDamage")
    private int metaDamage;

    /**
     * Determines if this recipe has been recognized by Minecraft/Forge GameRegistry as being valid.
     */
    private boolean hasLoaded = false;

    /**
     * Stores reference to recipe component ItemStack (with metadata). Populated when loadRecipes() is called.
     */
    private ItemStack associatedItemStack = null;

    RecipeComponent(SlotContainerTypeEnum slotDestination,
                    String parentModID,
                    String internalName,
                    int amount,
                    int metaDamage)
    {
        super();

        this.slotDestination = slotDestination;
        this.parentModID = parentModID;
        this.slotExpectedItem = internalName;
        this.slotExpectedAmount = amount;
        this.metaDamage = metaDamage;
    }

    public SlotContainerTypeEnum getSlotType()
    {
        return this.slotDestination;
    }

    public String getInternalName()
    {
        return this.slotExpectedItem;
    }

    public int getAmount()
    {
        return this.slotExpectedAmount;
    }

    public String getNameWithModID()
    {
        return this.parentModID + ":" + this.slotExpectedItem;
    }

    public int getMetaDamage()
    {
        return this.metaDamage;
    }

    public boolean isLoaded()
    {
        return this.hasLoaded;
    }

    public ItemStack getAssociatedItemStack()
    {
        if (! this.hasLoaded)
        {
            ModLoader.log().warning( "[RecipeComponent]Cannot return associated itemstack for recipe since it was never loaded!" );
            return null;
        }

        return this.associatedItemStack;
    }

    public void associateItemStackToRecipeComponent(ItemStack associatedItemStack)
    {
        // Prevent double-loading!
        if (hasLoaded)
        {
            ModLoader.log().warning( "[RecipeComponent]Already loaded and verified this recipe with GameRegistry!" );
            return;
        }

        if (this.associatedItemStack != null)
        {
            ModLoader.log().warning( "[RecipeComponent]Associated item stack is not null! How can this be?!" );
            return;
        }

        // Make sure this cannot happen twice.
        hasLoaded = true;

        // Take a copy of the inputed parameter item for future reference.
        this.associatedItemStack = associatedItemStack;
    }

    public String getModID()
    {
        return this.parentModID;
    }

    public String getExpectedItemName()
    {
        return this.slotExpectedItem;
    }
}
