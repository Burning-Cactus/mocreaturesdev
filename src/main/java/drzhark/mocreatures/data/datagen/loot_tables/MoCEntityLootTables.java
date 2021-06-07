package drzhark.mocreatures.data.datagen.loot_tables;

import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class MoCEntityLootTables extends EntityLootTables { //TODO: Complex loot tables
    private final Set<EntityType<?>> lootTables = new HashSet<>();

    @Override
    @MethodsReturnNonnullByDefault
    protected Iterable<EntityType<?>> getKnownEntities() {
        return lootTables;
    }

    @Override
    protected void add(@Nonnull EntityType<?> type, @Nonnull LootTable.Builder table) {
        super.add(type, table);
        lootTables.add(type);
    }

    @Override
    protected void addTables() {
        //Creatures
        //Bird
        //Bear
        LootTable.Builder boarTable = LootTable.lootTable();
        this.addItemDrop(boarTable, MoCItems.ANIMALHIDE, 0.0F, 1.0F);
        this.addItemDrop(boarTable, Items.PORKCHOP, 0.0F, 2.0F);
        this.add(MoCEntities.BOAR, boarTable);
        this.singleItemConstantDrop(MoCEntities.CROCODILE, MoCItems.HIDECROC);
        this.singleItemConstantDrop(MoCEntities.DUCK, Items.FEATHER);
        this.singleItemConstantDrop(MoCEntities.DEER, MoCItems.FUR);
        this.singleItemConstantDrop(MoCEntities.ELEPHANT, MoCItems.ANIMALHIDE);
        //Ent
        this.singleItemConstantDrop(MoCEntities.FOX, MoCItems.FUR);
        this.singleItemConstantDrop(MoCEntities.GOAT, Items.LEATHER);
        //Grizzly Bear (NONE)
        //Kitty (NONE)
        //Komodo Dragon (TBD)
        this.singleItemConstantDrop(MoCEntities.LEOGER, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.LEOPARD, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.LIARD, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.LION, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.LIGER, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.LITHER, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.MANTICORE_PET, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.MOLE, MoCItems.FUR);
        this.singleItemConstantDrop(MoCEntities.MOUSE, Items.WHEAT_SEEDS);
        //Ostrich (TBD)
        //Panda bear
        this.singleItemConstantDrop(MoCEntities.PANTHARD, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.PANTHER, MoCItems.BIGCATCLAW);
        this.singleItemConstantDrop(MoCEntities.PANTHGER, MoCItems.BIGCATCLAW);
        //Pet scorpion
        //Polar bear (NONE)
        this.singleItemConstantDrop(MoCEntities.RACCOON, MoCItems.FUR);
        //Snake (TODO)
        this.singleItemConstantDrop(MoCEntities.TIGER, MoCItems.BIGCATCLAW);
        //Turtle
        //Turkey
        //Wildhorse TODO
        //Wyvern TODO
        //Mobs
        this.singleItemConstantDrop(MoCEntities.CAVE_OGRE, Items.DIAMOND);
        this.singleItemConstantDrop(MoCEntities.FLAME_WRAITH, Items.REDSTONE);
        //Fire Ogre TODO
        this.singleItemConstantDrop(MoCEntities.GREEN_OGRE, Blocks.OBSIDIAN);
        //Big Golem TODO
        //Horse Mob TODO
        //Hell Rat TODO
        //Manticore TODO
        //Minigolem NONE
        this.singleItemConstantDrop(MoCEntities.RAT, MoCItems.RAT_RAW);
        //Silver Skeleton TODO
        //Scorpion TODO
        //Werewolf TODO
        this.singleItemConstantDrop(MoCEntities.WRAITH, Items.GUNPOWDER);
        this.singleItemConstantDrop(MoCEntities.WWOLF, MoCItems.FUR);
        //Aquatic
        //Anchovy
        //Angelfish
        //Angler
        //Bass
        //Clownfish
        //Cod
        this.singleItemConstantDrop(MoCEntities.DOLPHIN, Items.COD);
        //Fishy
        //Goldfish
        //Hippotang
        //Jellyfish TODO
        //Manderin
        //Piranha
        //Salmon
        //Mantaray
        //Shark
        //Stingray
        //Ambients
        //Ant
        //Bee
        //Butterfly
        this.singleItemConstantDrop(MoCEntities.CRAB, MoCItems.CRAB_RAW);
        //Cricket
        //Dragonfly
        //Firefly
        //Fly
        this.singleItemConstantDrop(MoCEntities.MAGGOT, Items.SLIME_BALL);
        this.singleItemConstantDrop(MoCEntities.SNAIL, Items.SLIME_BALL);
        //Roach
        //Others
        //Egg
        //Kitty Bed
        //Litterbox
        //TRock
    }

    private LootTable.Builder addItemDrop(LootTable.Builder table, IItemProvider provider, float min, float max) {
        return table.withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(provider)
                        .apply(SetCount.setCount(RandomValueRange.between(min, max)
                        ))));
    }

    private void singleItemConstantDrop(EntityType<?> entity, IItemProvider item) {
        this.add(entity, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(item))
                        .apply(SetCount.setCount(ConstantRange.exactly(1)
                        ))));
    }
}
