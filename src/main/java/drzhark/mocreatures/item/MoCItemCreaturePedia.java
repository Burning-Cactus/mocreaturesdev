package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class MoCItemCreaturePedia extends MoCItem {

    public MoCItemCreaturePedia(String name) {
        super(new Item.Properties().maxStackSize(1));
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public void itemInteractionForEntity2(ItemStack par1ItemStack, LivingEntity entityliving) {
        if (entityliving.world.isRemote) {
            return;
        }

        if (entityliving instanceof MoCEntityHorse) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/horse.png");
            return;
        }

        if (entityliving instanceof MoCEntityTurtle) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/turtle.png");
            return;
        }

        if (entityliving instanceof MoCEntityBunny) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/bunny.png");
            return;
        }

        if (entityliving instanceof MoCEntityDolphin) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/dolphin.png");
            return;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            double dist = 5D;
            double d1 = -1D;
            LivingEntity entityliving = null;
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().expand(dist, dist, dist));
            for (int i = 0; i < list.size(); i++) {
                Entity entity1 = list.get(i);
                if (entity1 == null || !(entity1 instanceof LivingEntity)) {
                    continue;
                }

                if (!(player.canEntityBeSeen(entity1))) {
                    continue;
                }

                double d2 = entity1.getDistanceSq(player.getPosX(), player.getPosY(), player.getPosZ());
                if (((dist < 0.0D) || (d2 < (dist * dist))) && ((d1 == -1D) || (d2 < d1))
                        && ((LivingEntity) entity1).canEntityBeSeen(player)) {
                    d1 = d2;
                    entityliving = (LivingEntity) entity1;
                }
            }

            if (entityliving == null) {
                return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
            }

            if (entityliving instanceof MoCEntityHorse) {
                MoCreatures.showCreaturePedia(player, "/mocreatures/pedia/horse.png");
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }

            if (entityliving instanceof MoCEntityTurtle) {
                MoCreatures.showCreaturePedia(player, "/mocreatures/pedia/turtle.png");
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }

            if (entityliving instanceof MoCEntityBunny) {
                MoCreatures.showCreaturePedia(player, "/mocreatures/pedia/bunny.png");
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }

        return new ActionResult<ItemStack>(ActionResultType.PASS, stack);
    }
}
