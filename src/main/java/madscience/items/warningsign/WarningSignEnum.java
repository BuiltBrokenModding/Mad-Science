package madscience.items.warningsign;

public enum WarningSignEnum
{    
    MagneticField1("MagneticField1", "Warning for magnetic field", 0, 0),
    ExplosibleRegion("ExplosibleRegion", "Explosible region", 32, 0),
    WarningAuger("WarningAuger", "Warning of auger", 0, 32),
    Corrosive("Corrosive", "Hazard symbol corrosive Substances", 32, 32),
    Flammable("Flammable", "Warning flammable substances,", 64, 32),
    CompressedGas("CompressedGas", "Hazard symbol gas bottles", 96, 0),
    HotSurface("HotSurface", "Warning for hot surface", 96, 32),
    LaserBeam("LaserBeam", "Warning for laser beam", 0, 64),
    MagneticField2("MagneticField2", "Warning for magnetic field", 0, 96),
    OpticalRadiation("OpticalRadiation", "Warning for optical radiation", 32, 64),
    Explosive("Explosive", "Explosive substances", 64, 64),
    Poisonous("Poisonous", "Poisonous substances", 32, 96),
    Radioactive("Radioactive", "Radioactive substances or ionising radiation", 64, 96),
    Oxidising("Oxidising", "Warning for oxidising substances", 96, 64),
    ElectromagneticRadiation("ElectromagneticRadiation", "Non-ionising electromagnetic radiation, warning sign", 96, 96),
    Falling("Falling", "Risk of falling, warning sign", 128, 0),
    Biohazard("Biohazard", "Biohazard, warning sign", 160, 0),
    Battery("Battery", "Danger from batteries", 128, 32),
    RemoteStart("RemoteStart", "Remotely started equipment", 192, 64),
    FingerSandwich("FingerSandwich", "Hand injury", 160, 32),
    Slipping("Slipping", "Warning of the danger of slipping", 128, 64),
    LowCeiling("LowCeiling", "Low hanging ceiling", 160, 64),
    Pointy("Pointy", "Warning of a pointed object", 128, 96),
    Conveyor("Conveyor", "Conveyor system or track warning", 160, 96),
    Entanglement("Entanglement", "Warning of Risk of entanglement", 224, 64),
    GenericWarning("GenericWarning", "Generic warning symbol", 64, 0);

    /** Painting Title. */
    public final String title;
    public final String description;
    public final int offsetX;
    public final int offsetY;

    private WarningSignEnum(String title, String desc, int par6, int par7)
    {
        this.title = title;
        this.description = desc;
        this.offsetX = par6;
        this.offsetY = par7;
    }
}
