package madscience.dump;


import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.io.File;


@SideOnly(Side.CLIENT)
public class ItemStackRenderer
{
    public FBOHelper fbo;
    private RenderItem itemRenderer = new RenderItem();

    public ItemStackRenderer(int textureSize)
    {
        fbo = new FBOHelper( textureSize );
    }

    public void RenderItemStack(ItemStack targetStack)
    {
        Minecraft minecraft = FMLClientHandler.instance().getClient();

        if (targetStack != null && targetStack.getItem() != null)
        {
            fbo.begin();

            GL11.glMatrixMode( GL11.GL_PROJECTION );
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho( 0,
                          16,
                          0,
                          16,
                          - 100.0,
                          100.0 );
            GL11.glMatrixMode( GL11.GL_MODELVIEW );

            RenderHelper.enableGUIStandardItemLighting();
            RenderBlocks renderBlocks = ReflectionHelper.getPrivateValue( Render.class,
                                                                          itemRenderer,
                                                                          "field_76988_d",
                                                                          "renderBlocks" );

            if (renderBlocks == null)
            {
                // Cleanup renderer when failing.
                GL11.glMatrixMode( GL11.GL_PROJECTION );
                GL11.glPopMatrix();

                RenderHelper.disableStandardItemLighting();

                fbo.end();
                fbo.restoreTexture();

                return;
            }

            if (! ForgeHooksClient.renderInventoryItem( renderBlocks,
                                                        minecraft.renderEngine,
                                                        targetStack,
                                                        true,
                                                        0.0f,
                                                        0,
                                                        0 ))
            {
                itemRenderer.renderItemIntoGUI( null,
                                                minecraft.renderEngine,
                                                targetStack,
                                                0,
                                                0 );
            }

            GL11.glMatrixMode( GL11.GL_PROJECTION );
            GL11.glPopMatrix();

            RenderHelper.disableStandardItemLighting();

            fbo.end();
            fbo.saveToFile( new File( minecraft.mcDataDir,
                                      "dump/item/" + targetStack.itemID + "-" +
                                      targetStack.getItemDamage() + ".png" ) );

            fbo.restoreTexture();
        }
    }
}
