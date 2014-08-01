package madscience.items.dna;

import madscience.factory.mod.MadMod;
import madscience.items.ItemDNASampleLogic;

public class DNAHorse extends ItemDNASampleLogic
{

    public DNAHorse(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}
