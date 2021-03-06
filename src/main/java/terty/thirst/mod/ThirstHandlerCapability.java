package terty.thirst.mod;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import terty.thirst.api.DrinkableFluidRegistry;
import terty.thirst.api.IDrinkableFluidStatProvider;

import static java.lang.Float.min;
import static java.lang.Integer.min;

public final class ThirstHandlerCapability {

	/* Defaults */
	/**
	 * Default value for {@link ThirstHandlerCapability#maxSatisfaction}.
	 */
	private static int defaultMaxSatisfaction = 20;
	/**
	 * Default value for {@link ThirstHandlerCapability#maxSaturation}.
	 */
	private static float defaultMaxSaturation = 20;

	/* Limits */
	/**
	 * The highest value a player's thirst satisfaction can be.
	 *
	 * @see ThirstHandlerCapability#thirstSaturation
	 */
	private int maxSatisfaction;
	/**
	 * The highest value a player's thirst saturation can be.
	 *
	 * @see ThirstHandlerCapability#thirstSaturation
	 */
	private float maxSaturation;

	/**
	 * How satiated the player's thirst level is.
	 * <p>
	 * Higher is better, i.e. when this value hits 0 the player is completely dehydrated.
	 */
	private int thirstSatisfaction;
	/**
	 * Player's thirst saturation.
	 * <p>
	 * Thirst satisfaction will only start to deplete once this value hits zero.
	 */
	private float thirstSaturation;

	public ThirstHandlerCapability() {
		this(defaultMaxSatisfaction, defaultMaxSaturation);
	}

	public ThirstHandlerCapability(int maxSatisfaction, float maxSaturation) {
		this.maxSatisfaction = maxSatisfaction;
		this.thirstSatisfaction = maxSatisfaction;

		this.maxSaturation = maxSaturation;
		this.thirstSaturation = maxSaturation;
	}

	/**
	 * Checks how thirsty a player is.
	 * <p>
	 * The lower the value the thirstier the player. At 0 the player is completely dehydrated.
	 *
	 * @return the player's thirst level, the lower the value the thirstier the player is.
	 */
	public int getThirstLevel() {
		return (thirstSatisfaction);
	}

	/**
	 * Gets the player's thirst saturation level.
	 *
	 * @return the player's thirst saturation level.
	 * @see ThirstHandlerCapability#thirstSaturation
	 */
	public float getThirstSaturation() {
		return (thirstSaturation);
	}

	/**
	 * Consume fluid to increase thirst satisfaction and saturation.
	 * <p>
	 * If the fluid does not provide any thirst satisfaction or saturation it cannot be consumed.
	 *
	 * @param fluidStack fluid to consume.
	 * @return true fluid was consumed.
	 */
	public boolean drink(FluidStack fluidStack) {
		IDrinkableFluidStatProvider statProvider = DrinkableFluidRegistry.INSTANCE.getFluidStatProvider(fluidStack.getFluid());

		if (statProvider == null) {
			return false;
		}

		if (fluidStack.amount < Fluid.BUCKET_VOLUME) {
			return false;
		}

		if (needFluid()) {
			fluidStack.amount -= Fluid.BUCKET_VOLUME;
			fillSatisfaction(statProvider.getSatisfaction());
			fillSaturation(statProvider.getSaturation());
			return true;
		}

		return false;
	}

	private void fillSatisfaction(int amount) {
		thirstSatisfaction = min(maxSatisfaction, thirstSatisfaction + amount);
	}

	private void fillSaturation(float amount) {
		thirstSaturation = min((float) thirstSatisfaction, thirstSatisfaction + amount);
	}

	/**
	 * Checks whether the player can drink or not.
	 *
	 * @return true if the player can drink.
	 */
	boolean needFluid() {
		return (thirstSatisfaction < maxSatisfaction);
	}

	/**
	 * Check if the player is completely dehydrated.
	 *
	 * @return true if the player's thirst satisfaction is completely depleted.
	 */
	boolean dehydrated() {
		return (thirstSatisfaction <= 0);
	}

}
