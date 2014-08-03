package madscience.item.weapon.pulseriflemagazine;

import java.util.Comparator;

public class PulseRifleMagazineComparator implements Comparator<PulseRifleMagazineComparatorItem>
{
    @Override
    public int compare(PulseRifleMagazineComparatorItem ob1, PulseRifleMagazineComparatorItem ob2)
    {
        return ob1.bulletCount - ob2.bulletCount;
    }
}
