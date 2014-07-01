package madscience.items.dna;

import madscience.MadEntities;

public class DNAOcelot extends ItemDecayDNABase
{

    public DNAOcelot(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}
