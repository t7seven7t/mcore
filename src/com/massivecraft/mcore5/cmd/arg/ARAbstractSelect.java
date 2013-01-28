package com.massivecraft.mcore5.cmd.arg;

import java.util.Collection;

import org.bukkit.command.CommandSender;

import com.massivecraft.mcore5.util.Txt;

public abstract class ARAbstractSelect<T> implements ArgReader<T>
{
	public abstract String typename();
	public abstract T select(String str, CommandSender sender);
	public abstract Collection<String> altNames(CommandSender sender);
	public boolean canList(CommandSender sender) { return true; }
	
	@Override
	public ArgResult<T> read(String str, CommandSender sender)
	{
		ArgResult<T> result = new ArgResult<T>(this.select(str, sender));
		
		if (!result.hasResult())
		{
			result.getErrors().add("<b>No "+this.typename()+" matches \"<h>"+str+"<b>\".");
			if (this.canList(sender))
			{
				Collection<String> names = this.altNames(sender);
				if (names.size() == 0)
				{
					result.getErrors().add("<i>Note: There is no "+this.typename()+" available.");
				}
				else
				{
					result.getErrors().add("<i>Use "+Txt.implodeCommaAndDot(names, "<h>%s", "<i>, ", " <i>or ", "<i>."));
				}
			}
		}
		
		return result;
	}
}
