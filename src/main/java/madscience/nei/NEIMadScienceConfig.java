package madscience.nei;

import static codechicken.nei.api.API.addSetRange;

import java.util.Arrays;
import java.util.Comparator;

import madscience.MadFurnaces;
import madscience.MadScience;
import net.minecraft.item.Item;
import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.forge.GuiContainerManager;
import cpw.mods.fml.common.Mod;

public class NEIMadScienceConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
        // Hides the developer blocks for cryotube ghost block. Used in multi-part code.
        API.hideItem(MadFurnaces.CRYOTUBEGHOST.blockID);
    }

    @Override
    public String getName()
    {
        return MadScience.class.getAnnotation(Mod.class).name();
    }

    @Override
    public String getVersion()
    {
        return MadScience.class.getAnnotation(Mod.class).version();
    }

}