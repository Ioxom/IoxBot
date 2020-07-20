const config = require('./config.json');
const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const client = new Client ();
const prefix = ('-');
//we're storing variables in the config now, makes this file very clean
//logs in the bot
client.login (config.token);

//sends a message in console when the bot successfully starts up
client.once ('ready', () => {
	console.log ('bot initialized successfully');
	//sets the bot's activity to "E"
	client.user.setActivity ('E', {
		//sets the type of activity, types are https://discord.js.org/#/docs/main/stable/typedef/ActivityType
		//note that for the streaming type you need to specify the URL
		type: 'PLAYING'
	  });
});

//this is where new commands are added
client.on ('message', msg => {
	//prefixless commands
	//checks if the message is "E"
	if (msg.content === 'E') {
		//checks if the author of the message is a bot to stop loops
		if (msg.author.bot) return;
		//sends "E" in the same channel as the message
		msg.channel.send ('E');
		return;
	}
	//checks if the message begins with "no u"
	if (msg.content.startsWith ('no u')) {
		if (msg.author.bot) return;
		msg.channel.send ('no u');
		return;
	}
	if (msg.content.startsWith ('IoxBot')) {
		const whomstAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807045874286622/whomst.jpg');
		if (msg.author.bot) return;
		msg.channel.send (whomstAttachment);
		return;
	}
	if (msg.content.startsWith ('rip')) {
		const ripAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058159140954/rip.jpg');
		if (msg.author.bot) return;
		msg.channel.send (ripAttachment);
		return;
	}
	if (msg.content.startsWith ('hello IoxBot')) {
		if (msg.author.bot) return;
		//sends "hello" accompanied by the author of the command message, plus angle brackets and an @ so it's a ping
		msg.channel.send ('Hello ' + '<@'  + msg.author + '>' );
		return;
	}
	//checks if the author of the message is a bot or doesn't have the prefix, in both cases it cancels the command
	if (msg.author.bot || !msg.content.startsWith (prefix) ) return;
	//splits the arguments at every space
	var args = msg.content.substring (prefix.length) .split (" ");
	switch (args[0]) {
		case 'belt', 'Belt':
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment('https://cdn.discordapp.com/attachments/719955731821887602/733807048956837928/belt.jpg');
			//send some text to the channel that the command was sent to
			msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!');
			//send the attachment made a couple lines ago
			msg.channel.send (beltAttachment);
			//makes sure that the next command isn't executed immediately after this one
			return;
		case 'uno_reverse_card', 'unoreversecard':
			const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807057811275797/uno_u.jpg');
			msg.channel.send (unoAttachment);
			return;
		case 'what\'s_a_tortoise', 'whatsatortoise':
			msg.channel.send ('As we all know, an underdeveloped sad excuse for a turtle.');
			return;
		case 'are_they_groovin?', 'aretheygroovin?':
			const groovinAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058654068756/groovin.jpg');
			msg.channel.send (groovinAttachment);
			return;
		case 'BotInfo', 'botinfo':
			const infoEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
				.setTitle ('General Information')
				.addFields (
					{ name: 'Creation Date', value: '`June 17th, 2020`', inline: true },
					{ name: 'Gender', value: '`Female♀`', inline: true },
					{ name: 'Version', value: '`' + (config.version) + '`', inline: true },
					{ name: 'GitHub', value: '`Ioxom/IoxBot`', inline: true },
					{ name: 'Creator', value: '`Ioxom`', inline: true },
					{ name: 'Prefix', value: '`-`', inline: true },
				);
			msg.channel.send (infoEmbed);
			return;
		case 'Info', 'info', 'i':
			//creates a second switch for arguments after the original "BotInfo" command
			switch (args[1]) {
				case 'creation_date', 'creationdate':
					//creates an embed, which is then edited by the .set code
					const dateEmbed = new MessageEmbed ()
						//adds a field at the top of the embed which shows "IoxBot", with a small image dictated by the link
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						//set the title of the embed
						.setTitle ('Created on June 17th, 2020')
						// Set the color of the embed's side thingy, in this case green
						.setColor (0x00FF00)
						// Set the main content of the embed
						.setDescription ('Last update was ' + (config.lastUpdate));
						// Send the embed to the same channel as the command
					msg.channel.send (dateEmbed);
					return;
				case 'version':
					const versionEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle (config.version)
						.setColor (0x00FF00);
					msg.channel.send (versionEmbed);
					return;
				case 'author':
					const authorEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('IoxBot has been programmed by Ioxom')
						.setColor (0x00FF00)
						.setDescription ('Ioxom is cool give him your cash money');
					msg.channel.send (authorEmbed);
					return;
				case 'commands':
					const commandsEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('IoxBot\'s commands are :')
						.setColor (0x00FF00)
						//adds fields, which are different sections of the embed
						.addFields (
							{ name: 'Normal Commands', value: (config.commands)},
							{ name: 'Prefixless Commands', value: (config.prefixlessCommands)}
						)
					msg.channel.send (commandsEmbed);
					return;
				case 'GitHub', 'github':
					const githubEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('Source code For IoxBot can be found here')
    					.setColor (0x00FF00)
    					.setURL ("https://github.com/Ioxom/Ioxbot");
					msg.channel.send (githubEmbed);
					return;
			}
	}
})