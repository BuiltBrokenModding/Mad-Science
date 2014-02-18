package madscience.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class ExampleServerCommand implements ICommand
{
    private List aliases;

    public ExampleServerCommand()
    {
        this.aliases = new ArrayList();
        this.aliases.add("example");
        this.aliases.add("ex");
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
    {
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        return true;
    }

    @Override
    public int compareTo(Object arg0)
    {
        return 0;
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public String getCommandName()
    {
        return "example";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "example <text>";
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i)
    {
        return false;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        if (astring.length == 0)
        {
            new ChatMessageComponent();
            // Command arguments incorrect.
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Example Returned: Invalid arguments"));
            return;
        }

        new ChatMessageComponent();
        // Show player an example of how the command is expected to be
        // formatted.
        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Example Returned: " + astring[0]));
    }

}
