package com.teamabnormals.clayworks.integration.jei;

import com.teamabnormals.clayworks.client.gui.screens.inventory.KilnScreen;
import com.teamabnormals.clayworks.common.inventory.KilnMenu;
import com.teamabnormals.clayworks.common.item.crafting.BakingRecipe;
import com.teamabnormals.clayworks.core.Clayworks;
import com.teamabnormals.clayworks.core.registry.ClayworksBlocks;
import com.teamabnormals.clayworks.core.registry.ClayworksRecipes.ClayworksRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class ClayworksPlugin implements IModPlugin {
	public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
	public static final RecipeType<BakingRecipe> BAKING = RecipeType.create(Clayworks.MOD_ID, "baking", BakingRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Clayworks.MOD_ID, Clayworks.MOD_ID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registration.addRecipeCategories(new BakingRecipeCategory(guiHelper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<BakingRecipe> bakingRecipes = new ArrayList<>();
		bakingRecipes.addAll(getRecipes(Minecraft.getInstance().level.getRecipeManager(), ClayworksRecipeTypes.BAKING.get()));
		registration.addRecipes(BAKING, bakingRecipes);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(KilnScreen.class, 78, 32, 28, 23, BAKING, RecipeTypes.FUELING);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(KilnMenu.class, BAKING, 0, 1, 3, 36);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ClayworksBlocks.KILN.get()), BAKING, RecipeTypes.FUELING);
	}

	@SuppressWarnings("unchecked")
	private static <C extends Container, T extends Recipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, net.minecraft.world.item.crafting.RecipeType<T> recipeType) {
		Map<ResourceLocation, Recipe<C>> recipesMap = recipeManager.byType(recipeType);
		return (Collection<T>) recipesMap.values();
	}

}