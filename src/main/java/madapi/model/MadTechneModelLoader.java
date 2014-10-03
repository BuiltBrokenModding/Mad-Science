package madapi.model;

import java.net.URL;

import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

public class MadTechneModelLoader implements IModelCustomLoader
{
    private static final String[] types =
    { "mad" };

    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public String getType()
    {
        return "MadTechneModel";
    }

    @Override
    public IModelCustom loadInstance(String resourceName, URL resource) throws ModelFormatException
    {
        return new MadTechneModel(resourceName, resource);
    }
}
