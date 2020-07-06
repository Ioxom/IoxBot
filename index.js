const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const client = new Client ();
const prefix = ('-');
//a couple variables for making updating things easier
const version = '1.1.0';
const commands = 'BotInfo version, BotInfo author, BotInfo commands, BotInfo GitHub, belt, uno_reverse_card, are_they_groovin?';
//logs in the bot
client.login ('this won\'t be on the github anymore');

//sends a message in console when the bot successfully starts up
client.once ('ready', () => {
	console.log ('bot initialized successfully');
})

//this is where new commands are added
client.on ('message', msg => {
	if (msg.author.bot || !msg.content.startsWith (prefix) ) return;
	var args = msg.content.substring (prefix.length) .split (" ") ;

	switch (args[0]) {
		case 'belt':
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment('https://cdn.discordapp.com/attachments/618926084750180363/721009676849905725/belt.jpg');
			msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!')
			msg.channel.send (beltAttachment);
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
						// Set the color of the embed's side thingy
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
    					.setDescription ('https://github.com/Ioxom/Ioxbot');
					msg.channel.send (githubEmbed);
					return;
			}

	}
})