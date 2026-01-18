package com.kitsuneindustries.deathwatch.command;

import com.kitsuneindustries.deathwatch.Deathwatch;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DeathwatchCommand {

  private static final int OP_LEVEL = 0;
  private static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister
    .create(Registries.COMMAND_ARGUMENT_TYPE, Deathwatch.MODID);

  private DeathwatchCommand() {
  }

  public static void init(IEventBus modBus) {
    COMMAND_ARGUMENT_TYPES.register(modBus);

    NeoForge.EVENT_BUS.addListener(DeathwatchCommand::registerCommands);
  }

  private static void registerCommands(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
    LiteralCommandNode<CommandSourceStack> mainNode = dispatcher.register(
      Commands.literal("deathwatch")
        .requires(cs -> cs.hasPermission(OP_LEVEL))
        .executes(context -> runTestCommand(context)));
  }

  private static int runTestCommand(CommandContext<CommandSourceStack> context) {
    MutableComponent message = MutableComponent.create(PlainTextContents.create("Test command was run!"));
    context.getSource().sendSystemMessage(message);
    return 0;
  }

}
