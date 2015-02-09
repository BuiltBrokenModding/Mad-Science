package madscience.content.tiles.clayfurnace;

import madscience.MadScience;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created by robert on 2/8/2015.
 */
public class TexturesClayFurnace
{
    public static IModelCustom MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(MadScience.ID, "models/clayFurnace/clayFurnace.tcn"));

    public static ResourceLocation TEXTURE_IDLE = new ResourceLocation(MadScience.ID, "models/clayFurnace/idle.png");
    public static ResourceLocation TEXTURE_SHELL = new ResourceLocation(MadScience.ID, "models/clayFurnace/shell.png");
    public static ResourceLocation TEXTURE_DONE = new ResourceLocation(MadScience.ID, "models/clayFurnace/done.png");

    public static ResourceLocation[] TEXTURE_WORK = new ResourceLocation[]
            {
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/work0.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/work1.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/work2.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/work3.png")
            };

    public static ResourceLocation[] TEXTURE_REDHOT = new ResourceLocation[]
            {
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/redhot0.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/redhot1.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/redhot2.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/redhot3.png"),
                    new ResourceLocation(MadScience.ID, "models/clayFurnace/redhot4.png")
            };

    public static ResourceLocation getTextureBasedOnState(TileClayFurnace tile, TileClayFurnace.BurnState state)
    {
        // Active state has many textures based on item cook progress.
        if(tile != null && tile.world() != null)
        {
            if (state == TileClayFurnace.BurnState.DONE)
            {
                // COOLED DOWN (WAITING FOR PLAYER TO HIT US)
                return TEXTURE_DONE;
            }
            else if (state == TileClayFurnace.BurnState.SMOLDERING)
            {
                // SMOLDERING FURNACE MODE
                return TEXTURE_SHELL;
            }
            else if (state == TileClayFurnace.BurnState.COOLING || state == TileClayFurnace.BurnState.COOKING)
            {
                ResourceLocation l;

                if (state == TileClayFurnace.BurnState.COOLING)
                {
                    if (tile.animationFrame >= TEXTURE_REDHOT.length)
                        tile.animationFrame = 0;
                    l = TEXTURE_REDHOT[tile.animationFrame];
                }
                else
                {
                    if (tile.animationFrame >= TEXTURE_WORK.length)
                        tile.animationFrame = 0;
                    l = TEXTURE_WORK[tile.animationFrame];
                }
                // COOL DOWN (RED HOT MODE)
                if (tile.ticks % (MadScience.SECOND_IN_TICKS * 5) == 0)
                {
                    // Update animation frame.
                    ++tile.animationFrame;
                }
                return l;
            }
        }
        return TEXTURE_IDLE;
    }
}
