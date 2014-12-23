package madscience.dump;


import com.google.gson.annotations.Expose;


public class MinecraftItemJSONObject
{
    @Expose
    public int type;

    @Expose
    public int meta;

    @Expose
    public String name;

    @Expose
    public String text_type;

    public MinecraftItemJSONObject(int type, int meta, String name, String text_type)
    {
        super();

        this.type = type;
        this.meta = meta;
        this.name = name;
        this.text_type = text_type;
    }

    @Override
    public boolean equals(Object other)
    {
        if (! (other instanceof MinecraftItemJSONObject))
        {
            return false;
        }

        MinecraftItemJSONObject that = (MinecraftItemJSONObject) other;

        // Custom equality check here.
        return this.name.equals( that.name ) && this.text_type.equals( that.text_type );
    }

    @Override
    public int hashCode()
    {
        int hashCode = 0;

        hashCode = hashCode * 37 + this.name.hashCode();
        hashCode = hashCode * 37 + this.text_type.hashCode();

        return hashCode;
    }
}
