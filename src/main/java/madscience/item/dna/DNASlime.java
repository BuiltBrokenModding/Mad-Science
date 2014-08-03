package madscience.item.dna;

import madscience.factory.mod.MadMod;
import madscience.item.ItemDNASampleLogic;

public class DNASlime extends ItemDNASampleLogic
{

    public DNASlime(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}