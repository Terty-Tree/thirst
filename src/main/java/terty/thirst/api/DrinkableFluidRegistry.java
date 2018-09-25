package terty.thirst.api;

import com.google.common.base.Preconditions;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public enum DrinkableFluidRegistry {

	INSTANCE;

	private Map<Fluid, IDrinkableFluidStatProvider> drinkableFluids = new HashMap<>();

	/**
	 * Attempts to register the given fluid to the given statProvider.
	 * <p>
	 * A fluid can be registered only once, attempting to register it more than once will throw
	 * an exception.
	 *
	 * @param fluid fluid to add to registry.
	 * @param statProvider {@link IDrinkableFluidStatProvider} to associate with the specified fluid.
	 * @throws NullPointerException if any of the provided arguments are null.
	 * @throws IllegalStateException if the specified fluid is already registered.
	 */
	public void register(@Nonnull Fluid fluid, @Nonnull IDrinkableFluidStatProvider statProvider)
			throws NullPointerException, IllegalStateException {
		Preconditions.checkNotNull(fluid);
		Preconditions.checkNotNull(statProvider);

		if (drinkableFluids.containsKey(fluid)) {
			throw new IllegalStateException("Attempted to register " + fluid.toString()
					+ " but that fluid is already registered.");
		}

		drinkableFluids.put(fluid, statProvider);
	}

	public boolean isRegistered(@Nonnull Fluid fluid) {
		Preconditions.checkNotNull(fluid);

		return (drinkableFluids.containsKey(fluid));
	}

	/**
	 * Gets the {@link IDrinkableFluidStatProvider} associated with the specified fluid.
	 * <p>
	 * Will return null if the fluid is not registered.
	 *
	 * @param fluid fluid whose associated {@link IDrinkableFluidStatProvider} to get.
	 * @return the {@link IDrinkableFluidStatProvider} associated the the specified fluid.
	 */
	@Nullable
	public IDrinkableFluidStatProvider getFluidStatProvider(@Nonnull Fluid fluid) {
		Preconditions.checkNotNull(fluid);

		return (drinkableFluids.get(fluid));
	}

}
