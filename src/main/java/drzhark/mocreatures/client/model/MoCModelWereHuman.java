package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWereHuman extends BipedModel<MoCEntityWerewolf> {

    public MoCModelWereHuman() {
        //TODO 4.1 FIX
        super(0.0F, 0.0F, 64, 32);
    }
}
