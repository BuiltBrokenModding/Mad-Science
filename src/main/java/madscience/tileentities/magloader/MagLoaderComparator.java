package madscience.tileentities.magloader;

import java.util.Comparator;

class MagLoaderComparator implements Comparator<MagLoaderComparatorItem>
{
    @Override
    public int compare(MagLoaderComparatorItem ob1, MagLoaderComparatorItem ob2)
    {
        return ob1.bulletCount - ob2.bulletCount;
    }
}
