package madscience.item.warningsign;

enum WarningSignEnum
{    
    MagneticField1("MagneticField1", 0, 0),
    ExplosibleRegion("ExplosibleRegion", 32, 0),
    WarningAuger("WarningAuger", 0, 32),
    Corrosive("Corrosive", 32, 32),
    Flammable("Flammable", 64, 32),
    CompressedGas("CompressedGas", 96, 0),
    HotSurface("HotSurface", 96, 32),
    LaserBeam("LaserBeam", 0, 64),
    MagneticField2("MagneticField2", 0, 96),
    OpticalRadiation("OpticalRadiation", 32, 64),
    Explosive("Explosive", 64, 64),
    Poisonous("Poisonous", 32, 96),
    Radioactive("Radioactive", 64, 96),
    Oxidising("Oxidising", 96, 64),
    ElectromagneticRadiation("ElectromagneticRadiation", 96, 96),
    Falling("Falling", 128, 0),
    Biohazard("Biohazard", 160, 0),
    Battery("Battery", 128, 32),
    RemoteStart("RemoteStart", 192, 64),
    FingerSandwich("FingerSandwich", 160, 32),
    Slipping("Slipping", 128, 64),
    LowCeiling("LowCeiling", 160, 64),
    Pointy("Pointy", 128, 96),
    Conveyor("Conveyor", 160, 96),
    Entanglement("Entanglement", 224, 64),
    GenericWarning("GenericWarning", 64, 0);

    /** Painting Title. */
    public final String title;
    public final int offsetX;
    public final int offsetY;

    private WarningSignEnum(String title, int par6, int par7)
    {
        this.title = title;
        this.offsetX = par6;
        this.offsetY = par7;
    }
}
