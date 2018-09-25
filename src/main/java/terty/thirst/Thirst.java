package terty.thirst;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod(modid = "thirst", useMetadata = true)
@EventBusSubscriber
public class Thirst {

	private static ResourceLocation THIRST_RESOURCE_LOCATION = null;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY != null) {
			THIRST_RESOURCE_LOCATION = new ResourceLocation("player_thirst_tank");
		}
	}

	@SubscribeEvent
	public static void attachThirstCapability(AttachCapabilitiesEvent<Entity> event) {
		if (CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY != null && event.getObject() instanceof EntityPlayer)
			event.addCapability(THIRST_RESOURCE_LOCATION, new ICapabilityProvider() {
				private FluidTank thirstTank = new FluidTank(1000) {
					private FluidStack allowedFluid = new FluidStack(FluidRegistry.WATER, 0);

					@Override
					public boolean canFillFluidType(FluidStack fluid) {
						return fluid.isFluidEqual(allowedFluid) && super.canFillFluidType(fluid);
					}
				};

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
					return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
				}

				@Nullable
				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
					if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
						return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(thirstTank);
					}
					return null;
				}
			});
	}

}
