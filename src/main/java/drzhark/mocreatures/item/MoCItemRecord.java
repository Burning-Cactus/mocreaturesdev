package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.fixes.JukeboxRecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemRecord extends MusicDiscItem {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation("mocreatures", "shuffling");

    public MoCItemRecord(int comparatorValue, SoundEvent soundEvent, Item.Properties builder) {
        super(comparatorValue, soundEvent, builder);
    }
}
