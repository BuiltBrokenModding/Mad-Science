package madscience.items.warningsign;

public enum WarningSignEnum
{    
    ExplosibleRegion("ExplosibleRegion", 0, 0),
    SharpElement("SharpElement" , 16, 0),
    FireHazard("FireHazard" , 16, 16),
    MagneticField("MagneticField" , 32, 0),
    ExplosiveSubstances("ExplosiveSubstances" , 32, 16),
    PoisonousSubstances("PoisonousSubstances" , 48, 0),
    RadioactiveSubstances("RadioactiveSubstances" , 48, 16),
    OxidisingSubstances("OxidisingSubstances" , 0, 32),
    RemotelyStartedEquipment("RemotelyStartedEquipment" , 0, 48),
    SafetyMarking("SafetyMarking" , 16, 32),
    MovingPartsHazard("MovingPartsHazard" , 32, 32),
    OverheadObstacles("OverheadObstacles" , 16, 48),
    RadioactiveClassic("RadioactiveClassic" , 32, 48),
    RotationalHazard("RotationalHazard" , 48, 32),
    GenericWarning("GenericWarning" , 0, 16);

    /** Painting Title. */
    public final String title;
    public final int offsetX;
    public final int offsetY;

    private WarningSignEnum(String par3Str, int par6, int par7)
    {
        this.title = par3Str;
        this.offsetX = par6;
        this.offsetY = par7;
    }
}
