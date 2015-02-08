package madscience.content.abomination;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by robert on 2/6/2015.
 */
public class EntitySelectorAbomination implements IEntitySelector
{
    public static final EntitySelectorAbomination GENERIC = new EntitySelectorAbomination();

    @Override
    public boolean isEntityApplicable(Entity entity)
    {
        if(!(entity instanceof EntityAbomination) && entity instanceof EntityLivingBase)
        {
            if(entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;
                String username = player.getCommandSenderName();
                if (username.equals("ronwolf") || username.equals("FoxDiller") || username.equals("Prowlerwolf"))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
