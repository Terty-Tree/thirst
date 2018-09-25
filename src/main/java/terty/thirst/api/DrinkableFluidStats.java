package terty.thirst.api;

/**
 * Defines the statistics related to thirst for a drinkable fluid.
 */
public class DrinkableFluidStats implements IDrinkableFluidStatProvider {

	/**
	 * Amount of thirst satisfaction provided per bucket of the fluid consumed.
	 *
	 * @see terty.thirst.mod.ThirstHandlerCapability#thirstSatisfaction
	 */
	protected int satisfactionProvided;
	/**
	 * Amount of saturation provided per bucket of the fluid consumed.
	 *
	 * @see terty.thirst.mod.ThirstHandlerCapability#thirstSaturation
	 */
	protected float saturationProvided;

	public DrinkableFluidStats(int satisfactionProvided, float saturationProvided) {
		this.satisfactionProvided = satisfactionProvided;
		this.saturationProvided = saturationProvided;
	}

	/**
	 * @return {@link DrinkableFluidStats#satisfactionProvided}.
	 */
	public int getSatisfaction() {
		return (satisfactionProvided);
	}

	/**
	 * @return {@link DrinkableFluidStats#saturationProvided}.
	 */
	public float getSaturation() {
		return (saturationProvided);
	}
}
