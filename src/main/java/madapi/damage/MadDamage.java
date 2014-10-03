package madapi.damage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadDamage
{
    @Expose
    @SerializedName("DamageMinimum")
    private int damageMinimum;
    
    @Expose
    @SerializedName("DamageMaximum")
    private int damageMaximum;
    
    @SuppressWarnings("ucd")
    public MadDamage(int damageMinimum, int damageMaximum, int damageValueStarting)
    {
        super();
        this.damageMinimum = damageMinimum;
        this.damageMaximum = damageMaximum;
    }

    public int getDamageMinimum()
    {
        return damageMinimum;
    }

    public void setDamageMinimum(int damageMinimum)
    {
        this.damageMinimum = damageMinimum;
    }

    public int getDamageMaximum()
    {
        return damageMaximum;
    }

    public void setDamageMaximum(int damageMaximum)
    {
        this.damageMaximum = damageMaximum;
    }
}
