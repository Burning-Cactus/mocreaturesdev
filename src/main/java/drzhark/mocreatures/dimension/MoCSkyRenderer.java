package drzhark.mocreatures.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

public class MoCSkyRenderer implements IRenderHandler {


    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(int ticks, float partialTicks, ClientWorld world, Minecraft mc) {

    }
}
