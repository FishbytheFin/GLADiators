package me.fishbythefin.gladiators.weapons;

import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum GladiatorsItemTier implements Tier {
    HILTLESS(0, -1, 1.0F, -1.0F, 10, () ->
    {
        return Ingredient.of(RegistryHandler.HILTLESS_HORROR.get());
    }),
    TOY_HAMMER(0, -1, 1.0F, -1.0F, 69, () ->
    {
        return Ingredient.of(RegistryHandler.TOY_HAMMER.get());
    }),
    SPRAY_N_PRAY(0, -1, 1.0f, -1.0f, 420, () ->
    {return Ingredient.of(RegistryHandler.SPRAY_N_PRAY.get());});

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    GladiatorsItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
