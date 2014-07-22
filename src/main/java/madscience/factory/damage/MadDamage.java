package madscience.factory.damage;

import com.google.gson.annotations.Expose;

public class MadDamage
{
    @Expose
    private int damageMinimum;
    
    @Expose
    private int damageMaximum;
    
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
