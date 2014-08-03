package madscience.item.dna;

import madscience.factory.mod.MadMod;
import madscience.item.ItemDNASampleLogic;

public class DNAWitch extends ItemDNASampleLogic
{

    public DNAWitch(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}
