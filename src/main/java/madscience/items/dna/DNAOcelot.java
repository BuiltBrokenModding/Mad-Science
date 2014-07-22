package madscience.items.dna;

import madscience.factory.mod.MadMod;

public class DNAOcelot extends ItemDecayDNABase
{

    public DNAOcelot(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadMod.getCreativeTab());
    }

}
