package madscience.tile;


import madscience.damage.DamageTrackingSupported;
import madscience.product.TileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;


public class TileEntityDamagePrefab extends TileEntityHeatPrefab
{
    /**
     * Current and starting level of damage.
     */
    private int damageValue;

    /**
     * Total amount of damage that may be accumulated.
     */
    private int damageMaximum;

    /**
     * Determines if damage is tracked at all on this tile entity.
     */
    private boolean damageSupported = false;

    public TileEntityDamagePrefab()
    {
        super();
    }

    TileEntityDamagePrefab(TileEntityFactoryProduct registeredMachine)
    {
        super( registeredMachine );

        DamageTrackingSupported[] damageSupported = registeredMachine.getDamageTrackingSupported();

        if (damageSupported != null)
        {
            this.damageSupported = true;

            for (DamageTrackingSupported damageSupport : damageSupported)
            {
                // TODO: Only one damage tracking system is supported at this time.
                this.damageValue = damageSupport.getDamageMinimum();
                this.damageMaximum = damageSupport.getDamageMaximum();
            }
        }
    }

    public int getDamageValueScaled(int pxl)
    {
        if (! this.damageSupported)
        {
            return 0;
        }

        return (int) (this.damageValue * (pxl / (float) this.damageMaximum));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Debugging.
        //MadMod.log().info("[" + this.getMachineInternalName() + "]Damage: " + this.getDamageValue() + "/" + this.getDamageMaximum());
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT( nbt );

        if (nbt.hasKey( "DamageSupported" ))
        {
            // Damage supported.
            this.damageSupported = nbt.getBoolean( "DamageSupported" );

            if (this.damageSupported)
            {
                // Damage value.
                this.damageValue = nbt.getInteger( "DamageValue" );

                // Damage maximum.
                this.damageMaximum = nbt.getInteger( "DamageMaximum" );
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT( nbt );

        if (this.damageSupported)
        {
            // Damage supported.
            nbt.setBoolean( "DamageSupported",
                            this.damageSupported );

            // Damage value. 
            nbt.setInteger( "DamageValue",
                            this.damageValue );

            // Damage maximum.
            nbt.setInteger( "DamageMaximum",
                            this.damageMaximum );
        }
    }

    public void increaseDamageValue()
    {
        if (! this.damageSupported)
        {
            return;
        }

        if (this.damageValue < this.damageMaximum)
        {
            this.damageValue++;
        }
    }

    public void decreaseDamageValue()
    {
        if (! this.damageSupported)
        {
            return;
        }

        if (this.damageValue > 0)
        {
            this.damageValue--;
        }
    }

    public int getDamageValue()
    {
        return damageValue;
    }

    public void setDamageValue(int damageValue)
    {
        this.damageValue = damageValue;
    }

    public int getDamageMaximum()
    {
        return damageMaximum;
    }

    public void setDamageMaximum(int damageMaximum)
    {
        this.damageMaximum = damageMaximum;
    }

    public boolean isDamageSupported()
    {
        return damageSupported;
    }

    public void setDamageSupported(boolean damageSupported)
    {
        this.damageSupported = damageSupported;
    }
}
