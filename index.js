const { Client, MessageAttachment, MessageEmbed, Message } = require ('discord.js');
const client = new Client ();
const prefix = ('-');
//a couple variables for making updating things easier
const version = '1.4.0';
const commands = ' With prefix : BotInfo version, BotInfo author, BotInfo commands, BotInfo GitHub, BotInfo creation_date, belt, uno_reverse_card, are_they_groovin? No prefix : no u, E, rip and IoxBot';
const lastUpdate = 'July 14th, 2020';
//logs in the bot
client.login ('NzIyODM1MjkwNjQ0ODA3NzEx.XwO7dQ.cP9YmT3glo_bTUcCRKdrjLlNqoE');

//sends a message in console when the bot successfully starts up
client.once ('ready', () => {
	console.log ('bot initialized successfully');
})

//this is where new commands are added
client.on ('message', msg => {
	//prefixless commands
	if (msg.content.startsWith ('E')) {
		if (msg.author.bot) return;
		msg.channel.send ('E');
	}
	if (msg.content.startsWith ('no u')) {
		if (msg.author.bot) return;
		msg.channel.send ('no u');
	}
	if (msg.content.startsWith ('IoxBot')) {
		const whomstAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/294758024877178880/722042809695207444/7x8rp0xnxiv31.png');
		if (msg.author.bot) return;
		msg.channel.send (whomstAttachment);
	}
	if (msg.content.startsWith ('rip')) {
		const ripAttachment = new MessageAttachment ('https://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2013/11/rip-786x305.jpg');
		if (msg.author.bot) return;
		msg.channel.send (ripAttachment);
	}
	if (msg.content.startsWith ('hello IoxBot')) {
		if (msg.author.bot) return;
		msg.channel.send ('Hello ' + '<@'  + msg.author + '>' );
	}
	//checks if the author of the message is a bot or doesn't have the prefix, in both cases it cancels the command
	if (msg.author.bot || !msg.content.startsWith (prefix) ) return;
	//splits the arguments at every space
	var args = msg.content.substring (prefix.length) .split (" ");
	switch (args[0]) {
		case 'belt':
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment('https://cdn.discordapp.com/attachments/618926084750180363/721009676849905725/belt.jpg');
			//send some text to the channel that the command was sent to
			msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!');
			//send the attachment made a couple lines ago
			msg.channel.send (beltAttachment);
			//makes sure that the next command isn't executed immediately after this one
			return;
		case 'uno_reverse_card':
			const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/642003331656581170/717832539896807434/nxeiZ78_ZCkQvMGkZ7fSTmixJ2ey4JCSlOuyKQL6Gos-1.png');
			msg.channel.send (unoAttachment);
			return;
		case 'what\'s_a_tortoise':
			msg.channel.send ('As we all know, an underdeveloped sad excuse for a turtle.');
			return;
		case 'are_they_groovin?':
			const groovinAttachment = new MessageAttachment('https://cdn.discordapp.com/attachments/618926084750180363/717833399825334303/Screen_Shot_2019-07-31_at_12.png');
			msg.channel.send (groovinAttachment);
			return;
		case 'BotInfo':
			switch (args[1]) {
				case 'creation_date':
					const dateEmbed = new MessageEmbed ()
						.setTitle ('Created on June 17th, 2020')
						.setColor (0x00FF00)
						.setDescription ('Last update was ' + (lastUpdate));
					msg.channel.send (dateEmbed);
					return;
				case 'version':
					const versionEmbed = new MessageEmbed ()
						.setTitle (version)
						.setColor (0x00FF00);
					msg.channel.send (versionEmbed);
					return;
				case 'author':
					const authorEmbed = new MessageEmbed ()
						//set the title of the embed
						.setTitle ('IoxBot has been programmed by Ioxom')
						// Set the color of the embed's side thingy, in this case green
						.setColor (0x00FF00)
    					// Set the main content of the embed
						.setDescription ('Ioxom is cool give him your cash money');
					// Send the embed to the same channel as the message
					msg.channel.send (authorEmbed);
					return;
				case 'commands':
					const commandsEmbed = new MessageEmbed ()
						.setTitle ('IoxBot\'s commands are :')
						.setColor (0x00FF00)
						.setDescription (commands);
					msg.channel.send (commandsEmbed);
					return;
				case 'GitHub':
					const githubEmbed = new MessageEmbed()
						.setTitle ('Source code For IoxBot can be found here')
    					.setColor (0x00FF00)
    					.setURL ("https://github.com/Ioxom/Ioxbot");
					msg.channel.send (githubEmbed);
					return;
			}
	}
})