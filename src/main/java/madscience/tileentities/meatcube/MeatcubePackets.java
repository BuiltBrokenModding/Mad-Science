package madscience.tileentities.meatcube;

import madscience.MadFluids;
import madscience.network.MadPackets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class MeatcubePackets extends MadPackets
{
    // XYZ coordinates of tile entity source.
    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;

    // Tile entity that we want to hook in the world.
    private MeatcubeEntity meatcubeTileEntity;

    // Cook time.
    private int lastItemCookTimeValue;
    private int lastItemCookTimeMaximum;

    // Water level.
    private int lastLiquidDNAMutantLevel;
    private int lastLiquidDNAMutantMaximum;

    // Damage level.
    private int meatDamageValue;
    private int meatDamageValueMax;

    // Last texture to be displayed.
    private String lastTexture;

    public MeatcubePackets()
    {
        // Required for reflection.
    }

    MeatcubePackets(int posX, int posY, int posZ, int cookTime, int cookTimeMax, int waterLevel, int waterMax, int damageLevel, int damageMax, String tileTexture)
    {
        // World position information.
        tilePosX = posX;
        tilePosY = posY;
        tilePosZ = posZ;

        // Cook time.
        lastItemCookTimeValue = cookTime;
        lastItemCookTimeMaximum = cookTimeMax;

        // Water level.
        lastLiquidDNAMutantLevel = waterLevel;
        lastLiquidDNAMutantMaximum = waterMax;

        // Damage level.
        meatDamageValue = damageLevel;
        meatDamageValueMax = damageMax;

        // Last texture to be displayed.
        lastTexture = tileTexture;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------

        // Packet received by client, executing payload.
        if (side.isClient())
        {
            meatcubeTileEntity = (MeatcubeEntity) player.worldObj.getBlockTileEntity(tilePosX, tilePosY, tilePosZ);
            if (meatcubeTileEntity == null)
                return;

            // Cook time.
            this.meatcubeTileEntity.currentItemCookingValue = lastItemCookTimeValue;
            this.meatcubeTileEntity.currentItemCookingMaximum = lastItemCookTimeMaximum;

            // Water level.
            this.meatcubeTileEntity.internalLiquidDNAMutantTank.setFluid(new FluidStack(MadFluids.LIQUIDDNA_MUTANT, lastLiquidDNAMutantLevel));
            this.meatcubeTileEntity.internalLiquidDNAMutantTank.setCapacity(lastLiquidDNAMutantMaximum);

            // Damage level.
            this.meatcubeTileEntity.currentMeatCubeDamageValue = meatDamageValue;
            this.meatcubeTileEntity.currentMaximumMeatCubeDamage = meatDamageValueMax;

            // Last texture to be displayed.
            this.meatcubeTileEntity.meatcubeTexturePath = lastTexture;
        }
        else
        {
            throw new ProtocolException("Cannot send this packet to the server!");
        }
    }

    @Override
    public void read(ByteArrayDataInput in) throws ProtocolException
    {
        // ------
        // CLIENT
        // ------

        // World coordinate information.
        this.tilePosX = in.readInt();
        this.tilePosY = in.readInt();
        this.tilePosZ = in.readInt();

        // Cook time.
        this.lastItemCookTimeValue = in.readInt();
        this.lastItemCookTimeMaximum = in.readInt();

        // Water level.
        this.lastLiquidDNAMutantLevel = in.readInt();
        this.lastLiquidDNAMutantMaximum = in.readInt();

        // Damage level.
        this.meatDamageValue = in.readInt();
        this.meatDamageValueMax = in.readInt();

        // Last texture to be displayed.
        this.lastTexture = in.readUTF();
    }

    @Override
    public void write(ByteArrayDataOutput out)
    {
        // ------
        // SERVER
        // ------

        // World coordinate information.
        out.writeInt(tilePosX);
        out.writeInt(tilePosY);
        out.writeInt(tilePosZ);

        // Cook time.
        out.writeInt(lastItemCookTimeValue);
        out.writeInt(lastItemCookTimeMaximum);

        // Water level.
        out.writeInt(lastLiquidDNAMutantLevel);
        out.writeInt(lastLiquidDNAMutantMaximum);

        // Damage level.
        out.writeInt(meatDamageValue);
        out.writeInt(meatDamageValueMax);

        // Last texture to be displayed.
        out.writeUTF(lastTexture);
    }
}