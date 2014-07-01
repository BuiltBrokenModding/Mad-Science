package madscience.items.dna;

import madscience.MadEntities;

public class DNASheep extends ItemDecayDNABase
{

    public DNASheep(int id, int primaryColor, int secondaryColor)
    {
        super(id, primaryColor, secondaryColor);
        this.setCreativeTab(MadEntities.tabMadScience);
    }

}
