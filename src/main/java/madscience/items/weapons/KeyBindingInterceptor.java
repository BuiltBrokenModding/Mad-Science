package madscience.items.weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.MadScience;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class KeyBindingInterceptor extends KeyBinding
{
    /** Create an Interceptor based on an existing binding. The initial interception mode is OFF. If existingKeyBinding is already a KeyBindingInterceptor, a reinitialised copy will be created but no further effect.
     * 
     * @param existingKeyBinding - the binding that will be intercepted. */
    public KeyBindingInterceptor(KeyBinding existingKeyBinding)
    {
        super(existingKeyBinding.keyDescription, existingKeyBinding.keyCode);
        // the base constructor automatically adds the class to the keybindArray and hash, which we don't want, so undo it
        keybindArray.remove(this);

        this.interceptionActive = false;
        this.pressed = false;
        this.pressTime = 0;
        this.interceptedPressTime = 0;

        if (existingKeyBinding instanceof KeyBindingInterceptor)
        {
            interceptedKeyBinding = ((KeyBindingInterceptor) existingKeyBinding).getOriginalKeyBinding();
        }
        else
        {
            interceptedKeyBinding = existingKeyBinding;
        }

        KeyBinding.resetKeyBindingArrayAndHash();
    }

    public void setInterceptionActive(boolean newMode)
    {
        if (newMode && !interceptionActive)
        {
            this.interceptedPressTime = 0;
        }
        interceptionActive = newMode;
    }

    public boolean isKeyDown()
    {
        copyKeyCodeToOriginal();
        return interceptedKeyBinding.pressed;
    }

    /** @return returns false if interception isn't active. Otherwise, retrieves one of the clicks (true) or false if no clicks left */
    public boolean retrieveClick()
    {
        copyKeyCodeToOriginal();
        if (interceptionActive)
        {
            copyClickInfoFromOriginal();

            if (this.interceptedPressTime == 0)
            {
                return false;
            }
            else
            {
                --this.interceptedPressTime;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /** A better name for this method would be retrieveClick. If interception is on, resets .pressed and .pressTime to zero. Otherwise, copies these from the intercepted KeyBinding.
     * 
     * @return If interception is on, this will return false; Otherwise, it will pass on any clicks in the intercepted KeyBinding */
    @Override
    public boolean isPressed()
    {
        copyKeyCodeToOriginal();
        copyClickInfoFromOriginal();

        if (interceptionActive)
        {
            this.pressTime = 0;
            this.pressed = false;
            return false;
        }
        else
        {
            if (this.pressTime == 0)
            {
                return false;
            }
            else
            {
                --this.pressTime;
                return true;
            }
        }
    }

    public KeyBinding getOriginalKeyBinding()
    {
        return interceptedKeyBinding;
    }

    protected KeyBinding interceptedKeyBinding;
    private boolean interceptionActive;

    private int interceptedPressTime;

    protected void copyClickInfoFromOriginal()
    {
        //MadScience.logger.info("PRESS TIME: " + String.valueOf(this.pressTime));
        this.pressTime += interceptedKeyBinding.pressTime;
        this.interceptedPressTime += interceptedKeyBinding.pressTime;
        interceptedKeyBinding.pressTime = 0;
        this.pressed = interceptedKeyBinding.pressed;
    }

    protected void copyKeyCodeToOriginal()
    {
        // only copy if necessary
        if (this.keyCode != interceptedKeyBinding.keyCode)
        {
            this.keyCode = interceptedKeyBinding.keyCode;
            resetKeyBindingArrayAndHash();
        }
    }

}