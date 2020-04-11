package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityHellRat extends MoCEntityRat {

    private int textCounter;

    public MoCEntityHellRat(EntityType<? extends MoCEntityHellRat> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public void selectType() {
        setType(4);
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.rand.nextInt(2) == 0) {
            this.textCounter++;
        }
        if (this.textCounter < 10) {
            this.textCounter = 10;
        }
        if (this.textCounter > 29) {
            this.textCounter = 10;
        }
        String textNumber = "" + this.textCounter;
        textNumber = textNumber.substring(0, 1);
        return MoCreatures.getTexture("hellrat" + textNumber + ".png");
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (flag) {
            return Item.getItemFromBlock(Blocks.FIRE);
        }
        return Items.REDSTONE;
    }
}
