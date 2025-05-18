package tunafish2k.tunaextension.callback;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ChatComponentText;
import scala.Int;
import tunafish2k.tunaextension.persistent.PersistentDatas;

public class CommandCallback extends CommandBase {
    @Override
    public String getCommandName() {
        return "callback";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/callback restart | /callback port <port>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("u must use subcommand `port <port>` or `restart`!");
        }
        if ("port".equals(args[0])) {
            if (args.length < 2) {
                throw new CommandException("argument <port> is required!");
            }
            try {
                Integer port = parseInt(args[1]);
                PersistentDatas.callback.port = port;
                sender.addChatMessage(new ChatComponentText(String.format("Successfully set port to %d", port)));
            } catch (NumberInvalidException e) {
                throw new CommandException("argument <port> must be a vliad number!");
            }

            return;
        }
        if ("restart".equals(args[0])) {
            CallbackServer.INSTANCE.start();
            sender.addChatMessage(new ChatComponentText("restarting..."));
            return;
        }
        throw new CommandException("u must use subcommand `port <port>` or `restart`!");
    }
}
