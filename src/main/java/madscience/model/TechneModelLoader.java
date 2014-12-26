package madscience.model;


import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

import java.net.URL;


public class TechneModelLoader implements IModelCustomLoader
{
    private static final String[] types = {"mad"};

    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public String getType()
    {
        return "TechneModel";
    }

    @Override
    public IModelCustom loadInstance(String resourceName, URL resource) throws ModelFormatException
    {
        return new TechneModel( resourceName,
                                resource );
    }
}
