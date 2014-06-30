package madscience.tileentities.voxbox;

class VoxBoxSoundItem
{
    float duration;
    String fileName;
    String internalName;

    VoxBoxSoundItem(float length, String name, String file)
    {
        duration = length;
        internalName = name;
        fileName = file;
    }
}
