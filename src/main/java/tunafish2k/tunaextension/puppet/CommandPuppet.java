package tunafish2k.tunaextension.puppet;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ChatComponentText;
import tunafish2k.tunaextension.persistent.PersistentDatas;

import java.util.*;

public class CommandPuppet extends CommandBase {
    PuppetMessageSender messageSender = new PuppetMessageSender();

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "puppet";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "puppet add <key> <host> <port>; puppet remove <key>; puppet say <key> <message>";
    }

    public void throwWrongSubCommand() throws CommandException {
        throw new CommandException("u must use one of the subcommands: `list`, `add`, `remove`, `say`.");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));

        if (args.length < 1) throwWrongSubCommand();
        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("list")) {
            sender.addChatMessage(new ChatComponentText("\u00a7bPuppets:"));
            PersistentDatas.puppet.puppets.forEach((s, puppet) -> {
                sender.addChatMessage(new ChatComponentText(String.format("\u00a7b%s %s:%d", s, puppet.host, puppet.port)));
                sender.addChatMessage(new ChatComponentText(""));
            });
            return;
        }

        if (args.length < 2) throw new CommandException("missing argument <key>!");
        String key = args[1];

        if (subCommand.equals("add")) {
            if (args.length < 4) {
                throw new CommandException("missing argument <host>/<port> or both of them!");
            }
            try {
                String host = args[2];
                Integer port = parseInt(args[3]);
                PersistentDatas.puppet.addPuppet(key, host, port);
            } catch (NumberInvalidException e) {
                throw new CommandException("invalid port!");
            }
            sender.addChatMessage(new ChatComponentText(String.format("\u00a7aSuccessfully added `%s` as a puppet.", key)));
            return;
        }
        if (subCommand.equals("remove")) {
            PersistentDatas.puppet.removePuppet(key);
            sender.addChatMessage(new ChatComponentText(String.format("\u00a7aSuccessfully deleted puppet `%s`", key)));
            return;
        }
        if (subCommand.equals("say")) {
            if (args.length < 3) {
                throw  new CommandException("missing message!");
            }
            Puppet target;
            if ((target = PersistentDatas.puppet.puppets.get(key)) == null) {
                throw new CommandException(String.format("puppet `%s` doesn't exist!"));
            }
            try {
                String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                messageSender.sendMessage(target, message);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return;
        }
        throwWrongSubCommand();
    }
}
