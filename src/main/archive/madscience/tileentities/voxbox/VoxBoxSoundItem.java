package madscience.tileentities.voxbox;

public class VoxBoxSoundItem
{
    public float duration;
    public String fileName;
    public String internalName;

    public VoxBoxSoundItem(float length, String name, String file)
    {
        duration = length;
        internalName = name;
        fileName = file;
    }
}
