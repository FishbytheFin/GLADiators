package me.fishbythefin.gladiators.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class DamnedEffect extends MobEffect {
    public DamnedEffect() {
        //Purple color
        //RGB to int: http://www.shodor.org/~efarrow/trunk/html/rgbint.html
        super(MobEffectCategory.HARMFUL, 4524416);//6293888
    }
}
