const config = require('./config.json');
const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const client = new Client ();
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
	var args = msg.content.substring ((config.literallyNothing).length) .split (" ");
	switch (args[0]) {
		//checks if the message is "E"
		case 'E':
			//checks if the author of the message is a bot to stop loops
			if (msg.author.bot) return;
			//sends "E" in the same channel as the message
			msg.channel.send (config.E);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used E');
			}
			return;
		case 'IoxBot':
			const whomstAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807045874286622/whomst.jpg');
			if (msg.author.bot) return;
			msg.channel.send (whomstAttachment);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used IoxBot');
			}
			return;
		case 'rip':
			const ripAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058159140954/rip.jpg');
			if (msg.author.bot) return;
			msg.channel.send (ripAttachment);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used rip');
			}
			return;
		case 'hello':
			switch (args[1]) {
				case 'IoxBot':
					if (msg.author.bot) return;
					//sends "hello" accompanied by the author of the command message, plus angle brackets and an @ so it's a ping
					msg.channel.send ('Hello ' + '<@'  + msg.author + '>' );
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used hello IoxBot');
					}
					return;
			}
		case 'no':
			switch (args[1]) {
				case 'u':
					if (msg.author.bot) return;
					msg.channel.send ('no u');
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used no u');
					}
					return;
			}
	}

	//checks if the author of the message is a bot or doesn't have the prefix, in both cases it cancels the command
	if (msg.author.bot || !msg.content.startsWith (config.prefix) ) return;
	//splits the arguments at every space
	var args = msg.content.substring ((config.prefix).length) .split (" ");
	switch (args[0]) {
		case 'belt', 'Belt', 'belt':
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807048956837928/belt.jpg');
			//send some text to the channel that the command was sent to
			msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!');
			//send the attachment made a couple lines ago
			msg.channel.send (beltAttachment);
			//checks if logCommandUses in the config is true
			if (config.logCommandUses === 'true') {
				//prints the id of the user and "used belt"
				console.log (msg.author + ' used -belt');
			}
			//makes sure that the next command isn't executed immediately after this one
			return;
		case 'uno_reverse_card', 'unoreversecard', 'uno_reverse_card':
			const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807057811275797/uno_u.jpg');
			msg.channel.send (unoAttachment);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used -uno reverse card');
			}
			return;
		case 'what\'s_a_tortoise', 'whatsatortoise':
			msg.channel.send ('As we all know, an underdeveloped sad excuse for a turtle.');
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used -tortoise');
			}
			return;
		case 'are_they_groovin?', 'aretheygroovin?', 'are_they_groovin':
			const groovinAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058654068756/groovin.jpg');
			msg.channel.send (groovinAttachment);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used -groovin\'');
			}
			return;
		case 'BotInfo', 'botinfo', 'BotInfo':
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
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used -BotInfo');
			}
			return;
		case 'die':
			const veryfineattachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/618926084750180363/735560345854148619/crab_shoot_ioxbot.jpg');
			msg.channel.send (veryfineattachment);
			if (config.logCommandUses === 'true') {
				console.log (msg.author + ' used -die');
			}
			return;
		case 'i', 'info':
			//creates a second switch for arguments after the original "-info" command
			switch (args[1]) {
				case 'creation_date', 'creationdate', 'creation_date':
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
					if (config.logCommandUses === 'true') {
						console.log (msg.author + 'used info: creation date');
					}
					return;
				case 'version', 'version':
					const versionEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle (config.version)
						.setColor (0x00FF00);
					msg.channel.send (versionEmbed);
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used info: version');
					}
					return;
				case 'author', 'author':
					const authorEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('IoxBot has been programmed by Ioxom')
						.setColor (0x00FF00)
						.setDescription ('Ioxom is cool give him your cash money')
						.setFooter ('you don\'t want to know what happens if you don\'t');
					msg.channel.send (authorEmbed);
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used info: author');
					}
					return;
				case 'commands', 'commands':
					const commandsEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('IoxBot\'s commands are :')
						.setColor (0x00FF00)
						//adds fields, which are different sections of the embed
						.addFields (
							{ name: 'Normal Commands', value: (config.commands), inline: false },
							{ name: 'Prefixless Commands', value: (config.prefixlessCommands), inline: false }
						);
					msg.channel.send (commandsEmbed);
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used info: commands');
					}
					return;
				case 'GitHub', 'github', 'GitHub':
					const githubEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/733759142820315256/I.png')
						.setTitle ('Source code For IoxBot can be found here')
    					.setColor (0x00FF00)
    					.setURL (config.githubURL);
					msg.channel.send (githubEmbed);
					if (config.logCommandUses === 'true') {
						console.log (msg.author + ' used info: commands');
					}
					return;
			}
	}
})