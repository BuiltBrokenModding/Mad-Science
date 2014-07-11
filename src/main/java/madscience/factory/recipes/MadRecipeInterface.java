package madscience.factory.recipes;

import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;

public interface MadRecipeInterface
{

    public abstract int getCreationTimeInSeconds();

    public abstract int getCreationTimeInTicks();

    /** Converts input parameters from recipe creation into components for machine recipe system. */
    public abstract MadRecipeComponent parseParametersToComponents(MadSlotContainerTypeEnum slotType, String fullName);

    public abstract MadRecipeComponentInterface[] getRecipeResultBySlotType(MadSlotContainerTypeEnum slotType);

    /** Returns array of results for the given parameters, returns empty array if nothing. */
    public abstract MadRecipeComponentInterface[] getRecipeResultByIngredients(MadRecipeComponent ingredient1, MadRecipeComponent ingredient2, MadRecipeComponent ingredient3, MadRecipeComponent ingredient4, MadRecipeComponent ingredient5);

    public abstract MadRecipeComponentInterface[] getInputIngredientsArray();

    public abstract float getExperienceFromCreation();

    public abstract MadRecipeComponentInterface[] getOutputResultsArray();

}