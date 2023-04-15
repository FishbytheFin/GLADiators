package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SplinterItem extends Item {

    public static HashMap<UUID, ArrayList<BlockState>> splinteredBlocks = new HashMap<>();
    public static HashMap<UUID, ArrayList<BlockPos>> splinteredBlockPos = new HashMap<>();
    public static HashMap<UUID, Long> splinteredTimer = new HashMap<>();

    public SplinterItem() {
        super(new Item.Properties().stacksTo(1));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            splinterTheEarth(player, (ServerLevel) level);

            player.getCooldowns().addCooldown(this, 400); //Adds a cooldown of 20 seconds
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.splinter").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }

    private void splinterTheEarth(Player player, ServerLevel level) {
        UUID playerUUID = player.getUUID();

        ArrayList<BlockState> blockStates = new ArrayList<>();
        ArrayList<BlockPos> blockPoss = new ArrayList<>();

        for (int l = 0; l < 3; l++) {

            Vec3 offsetVector = calculateViewVector(player.getXRot(), player.getYRot() - 33.3f + (l * 33.3f)).multiply(9.0, 0.0, 9.0);
            int yOffset = 0; //Keeps track of the current y level offset of the trail

            BlockPos startPos = player.getOnPos();
            //BlockPos endPos = player.getOnPos().offset(offsetVector.x, 0, offsetVector.z);

            //Collects the blocks needed for the splinter
            for (int i = 0; i < 10; i++) {
                BlockPos currentBlockPos = startPos.offset(offsetVector.x * (i / 10.0), yOffset, offsetVector.z * (i / 10.0));
                if (level.getBlockState(currentBlockPos).getBlock().equals(Blocks.AIR)) {
                    if (!level.getBlockState(currentBlockPos.offset(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                        //Block above isn't air
                        yOffset++;
                        currentBlockPos = currentBlockPos.offset(0, 1, 0);

                    } else if (!level.getBlockState(currentBlockPos.offset(0, -1, 0)).getBlock().equals(Blocks.AIR)) {
                        //Block below isn't air
                        yOffset--;
                        currentBlockPos = currentBlockPos.offset(0, -1, 0);
                    }
                }

                if (!blockPoss.contains(currentBlockPos)) {
                    blockPoss.add(currentBlockPos);
                    blockStates.add(level.getBlockState(currentBlockPos));
                }

                level.setBlockAndUpdate(currentBlockPos, Blocks.WHITE_STAINED_GLASS.defaultBlockState());
            }
        }

        splinteredBlocks.put(playerUUID, blockStates);
        splinteredBlockPos.put(playerUUID, blockPoss);
        splinteredTimer.put(playerUUID, System.currentTimeMillis() + 1500L);
    }

    private Vec3 calculateViewVector(float xRot, float yRot) {
        float f = xRot * ((float)Math.PI / 180F);
        float f1 = -yRot * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
}
