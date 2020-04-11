package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityButterfly extends MoCEntityInsect {

    public MoCEntityButterfly(EntityType<? extends MoCEntityButterfly> type, World world) {
        super(type, world);
    }

    private int fCounter;

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (getIsFlying() && this.rand.nextInt(200) == 0) {
                setIsFlying(false);
            }
        }
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(10) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bfagalaisurticae.png");
            case 2:
                return MoCreatures.getTexture("bfargyreushyperbius.png");
            case 3:
                return MoCreatures.getTexture("bfathymanefte.png");
            case 4:
                return MoCreatures.getTexture("bfcatopsiliapomona.png");
            case 5:
                return MoCreatures.getTexture("bfmorphopeleides.png");
            case 6:
                return MoCreatures.getTexture("bfvanessaatalanta.png");
            case 7:
                return MoCreatures.getTexture("bfpierisrapae.png");
            case 8:
                return MoCreatures.getTexture("mothcamptogrammabilineata.png");
            case 9:
                return MoCreatures.getTexture("mothidiaaemula.png");
            case 10:
                return MoCreatures.getTexture("moththyatirabatis.png");
            default:
                return MoCreatures.getTexture("bfpierisrapae.png");
        }
    }

    public float tFloat() {
        if (!getIsFlying()) {
            return 0F;
        }
        if (++this.fCounter > 1000) {
            this.fCounter = 0;
        }

        return MathHelper.cos((this.fCounter * 0.1F)) * 0.2F;
    }

    @Override
    public float getSizeFactor() {
        if (getSubType() < 8) {
            return 0.7F;
        }
        return 1.0F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() == Item.getItemFromBlock(Blocks.POPPY) || stack.getItem() == Item
                        .getItemFromBlock(Blocks.DANDELION));
    }

    @Override
    public boolean isAttractedToLight() {
        return getSubType() > 7;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.13F;
        }
        return 0.10F;
    }
}
