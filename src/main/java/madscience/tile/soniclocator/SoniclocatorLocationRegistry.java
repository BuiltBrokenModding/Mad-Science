package madscience.tile.soniclocator;

import java.util.HashSet;

import madscience.MadScience;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

final class SoniclocatorLocationRegistry
{
    /** Defines a list of known sonic locators that have been placed in the world */
    static final HashSet <SoniclocatorLocationItem> otherSoniclocators;
    
    /** Stores the instance of the Minecraft server we will query for tile entities. */
    private static MinecraftServer server;
    
    /** World instance from the server which we need to query tile entities. */
    private static World world;

    static
    {
        // Init our HashSet.
        otherSoniclocators = new HashSet <SoniclocatorLocationItem>();
    }
    
    static void addLocation(SoniclocatorLocationItem newLocation)
    {
        // Attempts to add a given location of a sonic locator to the registry.
        if (!otherSoniclocators.contains(newLocation))
        {
            otherSoniclocators.add(newLocation);
            MadScience.logger.info("SoniclocatorRegistry: Adding new location " + String.valueOf(newLocation.posX) + "x" + String.valueOf(newLocation.posY) + "x" + String.valueOf(newLocation.posZ));
        }
    }
    
    static void removeLocation(SoniclocatorLocationItem rmLocation)
    {
        // Attempts to add a given location of a sonic locator to the registry.
        if (!otherSoniclocators.contains(rmLocation))
        {
            otherSoniclocators.remove(rmLocation);
            MadScience.logger.info("SoniclocatorRegistry: Removed location " + String.valueOf(rmLocation.posX) + "x" + String.valueOf(rmLocation.posY) + "x" + String.valueOf(rmLocation.posZ));
        }
    }
    
    static long queryDistanceBetweenSonicLocators (SoniclocatorLocationItem startPoint, SoniclocatorLocationItem endPoint)
    {
        try
        {
            // Attempt to look up the start and end point in the registry.
            server = FMLCommonHandler.instance().getMinecraftServerInstance();
            world = server.worldServers[0];
        }
        catch (Exception err)
        {
            MadScience.logger.info("queryDistanceBetweenSonicLocators: Attempted to grab server instance and failed!");
            return 0;
        }
        
        // Ensure that out grabbed instance of the server is real.
        if (server == null || world == null)
        {
            return 0;
        }
        
        if (world.isRemote)
        {
            return 0;
        }
        
        // Now attempt to locate the start and end points in the registry.
        if (!SoniclocatorLocationRegistry.otherSoniclocators.contains(startPoint))
        {
            SoniclocatorLocationRegistry.addLocation(startPoint);
        }
        
        if (!SoniclocatorLocationRegistry.otherSoniclocators.contains(endPoint))
        {
            SoniclocatorLocationRegistry.addLocation(endPoint);
        }

        // Grab running instance of both our tile entities.
        SoniclocatorEntity startTile = (SoniclocatorEntity) world.getBlockTileEntity(startPoint.posX, startPoint.posY, startPoint.posZ);
        SoniclocatorEntity endTile = (SoniclocatorEntity) world.getBlockTileEntity(endPoint.posX, endPoint.posY, endPoint.posZ);
        
        // Ensure that they are real and not null or empty.
        if (startTile == null || endTile == null)
        {
            return 0;
        }
        
        // Perform the distance calculation.
        Double totalDistanceApart = startTile.getDistanceFrom(endTile.xCoord, endTile.yCoord, endTile.zCoord);
        return Math.abs(totalDistanceApart.longValue());
    }
}
