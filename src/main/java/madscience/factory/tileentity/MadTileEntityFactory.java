package madscience.factory.tileentity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import madscience.MadScience;

public class MadTileEntityFactory
{
    private static final Map<String, MadTileEntityTemplate> registeredMachines = new LinkedHashMap<String, MadTileEntityTemplate>();

    public static MadTileEntityTemplate getMachineInfo(String id)
    {
        MadScience.logger.info("[MadMachineFactory]Querying machine: " + id);
        return registeredMachines.get(id);
    }

    public static Collection<MadTileEntityTemplate> getMachineInfoList()
    {
        return Collections.unmodifiableCollection(registeredMachines.values());
    }

    private static boolean isValidMachineID(String id)
    {
        return !registeredMachines.containsKey(id);
    }

    public static void registerMachine(MadTileEntityTemplate info) throws IllegalArgumentException
    {
        if (info == null)
        {
            throw new IllegalArgumentException("MadMachineTemplate cannot be null");
        }

        if (!isValidMachineID(info.getMachineName()))
        {
            throw new IllegalArgumentException("Duplicate MadMachineTemplate '" + info.getMachineName() + "' was added. Execution halted!");
        }

        MadScience.logger.info("[MadMachineFactory]Registering machine: " + info.getMachineName());
        registeredMachines.put(info.getMachineName(), info);
    }

}