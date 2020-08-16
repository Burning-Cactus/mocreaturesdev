package drzhark.mocreatures.registry;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(MoCConstants.MOD_ID)
public class MoCParticleTypes {
    public static final BasicParticleType STAR_FX = new BasicParticleType(false);
    public static final BasicParticleType UNDEAD_FX = new BasicParticleType(false);
    public static final BasicParticleType VACUUM_FX = new BasicParticleType(false);
    public static final BasicParticleType VANISH_FX = new BasicParticleType(false);

    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().registerAll(
                STAR_FX.setRegistryName("star_fx"),
                UNDEAD_FX.setRegistryName("undead_fx"),
                VACUUM_FX.setRegistryName("vacuum_fx"),
                VANISH_FX.setRegistryName("vanish_fx")
        );
    }
}
