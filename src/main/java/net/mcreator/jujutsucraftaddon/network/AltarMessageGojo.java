package net.mcreator.jujutsucraftaddon.network;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyStartTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraftaddon.JujutsucraftaddonMod;
import net.mcreator.jujutsucraftaddon.procedures.RemoveCE;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AltarMessageGojo {
    int page;

    // Constructor
    public AltarMessageGojo(int page) {
        this.page = page;
    }

    // Decoder
    public AltarMessageGojo(FriendlyByteBuf buffer) {
        this.page = buffer.readInt();
    }

    // Encoder
    public static void buffer(AltarMessageGojo message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.page);
    }

    // Handler
    public static void handler(AltarMessageGojo message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), message.page);
        });
        context.setPacketHandled(true);
    }

    // Action logic based on type and pressed time
    public static void pressAction(Player entity, int page) {
        Level world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        // Check if the chunk is loaded
        if (!world.hasChunkAt(entity.blockPosition()))
            return;


        // Ações feitas, cada page = um index, lembrando que começa por 0, não por 1
        if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2 == 2 && entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower >= 500) {
            // Execute actions based on the type
            if (page == 0) {
                entity.getPersistentData().putDouble("skill", -99);
                entity.getPersistentData().putBoolean("PRESS_Z", true);
                entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 3, false, false));
            } else if (page == 1) {
                entity.getPersistentData().putDouble("skill", -98);
                entity.getPersistentData().putBoolean("PRESS_Z", true);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = (Component.translatable("jujutsu.technique.attack3").getString());
                    capability.syncPlayerVariables(entity);
                });
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
            } else if (page == 2) {
                entity.getPersistentData().putBoolean("PRESS_Z", true);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = (Component.translatable("jujutsu.technique.attack5").getString());
                    capability.syncPlayerVariables(entity);
                });
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);

            } else if (page == 3) {
                entity.getPersistentData().putDouble("skill", 205);
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = (Component.translatable("jujutsu.technique.infinity").getString());
                    capability.syncPlayerVariables(entity);
                });

            } else if (page == 4) {
                entity.getPersistentData().putDouble("skill", 206);
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = (Component.translatable("jujutsu.technique.blue").getString());
                    capability.syncPlayerVariables(entity);
                });

            } else if (page == 5) {
                entity.getPersistentData().putDouble("skill", 207);
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = (Component.translatable("jujutsu.technique.red").getString());
                    capability.syncPlayerVariables(entity);
                });

            } else if (page == 6) {
                entity.getPersistentData().putDouble("skill", 215);
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                    capability.PlayerSelectCurseTechniqueName = "jujutsu.technique.purple";
                    capability.syncPlayerVariables(entity);
                });
                KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
            } } else if (page == 7) {
            entity.getPersistentData().putDouble("skill", 220);
            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.PlayerSelectCurseTechniqueName = "jujutsu.technique.unlimited_void";
                capability.syncPlayerVariables(entity);
            });
            KeyStartTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);


        }
        RemoveCE.execute(entity, world);
    }
    // Registrando o Packet
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsucraftaddonMod.addNetworkMessage(AltarMessageGojo.class, AltarMessageGojo::buffer, AltarMessageGojo::new, AltarMessageGojo::handler);
    }
}
