package madscience.items.combinedgenomes;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MadGenomeRegistry
{
    private static final Map<Short, MadGenomeInfo> genomes = new LinkedHashMap<Short, MadGenomeInfo>();

    public static MadGenomeInfo getGenomeInfo(short id)
    {
        return genomes.get(id);
    }

    public static Collection<MadGenomeInfo> getGenomeInfoList()
    {
        return Collections.unmodifiableCollection(genomes.values());
    }

    private static boolean isValidSpawnGenomeID(short id)
    {
        return !genomes.containsKey(id);
    }

    public static void registerGenome(MadGenomeInfo info) throws IllegalArgumentException
    {
        if (info == null)
        {
            throw new IllegalArgumentException("MadSpawnEggInfo cannot be null");
        }

        if (!isValidSpawnGenomeID(info.genomeID))
        {
            throw new IllegalArgumentException("Duplicate genome data reel with id " + info.genomeID);
        }

        genomes.put(info.genomeID, info);
    }

}