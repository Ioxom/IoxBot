const config = require('./config.json');
const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const client = new Client ();
//we're storing variables in the config now, makes this file very clean
//logs in the bot using the token from the config file
client.login (config.token);

//sends a message in console when the bot successfully starts up
client.once ('ready', () => {
	console.log ('bot initialized successfully');
	//sets the bot's activity to "E"
	client.user.setActivity ('E', {
		//sets the type of activity, types are https://discord.js.org/#/docs/main/stable/typedef/ActivityType
		//note that for the streaming type you need to specify the URL
		type: ('PLAYING')
	  });
});

//this is where new commands are added
client.on ('message', msg => {

	//functions

	//function for generating random numbers within parameters
	//creates the function with parameters for the maximum value and the minimum value
	function getrndInteger (min, max) {
		//generates a random number between the parameters using the power of math
		var result = (Math.floor (Math.random () * (max - min) ) + min);
		return result;
	}

	//function for logging used commands in the console
	//creates the function with a parameter for what command was used
	function logUsedCommand (commandUsed) {
		//checks if logCommandUses in the config is true
		if (config.logCommandUses === 'true') {
			//prints the id of the user and then "used [command]"
			console.log ((msg.author.tag) + ' used ' + (commandUsed));
		}
	}

	//funtion for generating a random shade of the selected colour for embeds
	//creates the function
	function generateRandomShade (colour) {
		//generates two random numbers between 0 and 75
		var integer1 = (getrndInteger (0, 75));
		var integer2 = (getrndInteger (0, 75));
		//adds a zero to the end of the number if it's below 10, preventing the result from being the wrong colour
		if (integer1 < 10) {
			integer1 = (integer1) + ('0');
		}
		//does the same for integer2
		if (integer2 < 10) {
			integer2 = (integer2) + ('0');
		}
		//adds the values to the selected colour to create a colour hex code
		shadeResult = ((integer1) + (colour) + (integer2))
		//sends the result to the line where the funtion was called
		return shadeResult;
	}

	//prefixless commands
	//checks if the message is "E"
	if (msg.content === ('E')) {
		//checks if the author of the message is a bot to stop loops
		if (msg.author.bot) return;
		//sends "E" in the same channel as the message
		msg.channel.send ('E');
		//uses the logUsedCommand funtion to print "[userid] used E"
		logUsedCommand ('E');
		break;
	}
	var args = msg.content.substring ((config.literallyNothing).length) .split (" ");
	switch (args[0]) {
		//checks if the message starts with "IoxBot"
		case 'IoxBot':
			const whomstAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807045874286622/whomst.jpg');
			if (msg.author.bot) return;
			msg.channel.send (whomstAttachment);	
			logUsedCommand ('IoxBot');
			break;
		case 'rip':
			const ripAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058159140954/rip.jpg');
			if (msg.author.bot) return;
			msg.channel.send (ripAttachment);
			logUsedCommand ('rip');
			break;
		case 'hello':
			switch (args[1]) {
				case 'IoxBot':
					if (msg.author.bot) return;
					//sends "hello" accompanied by the author of the command message, plus angle brackets and an @ so it's a ping
					msg.channel.send (('Hello ') + ('<@')  + (msg.author) + ('>'));
					logUsedCommand ('hello IoxBot');
					break;
			}
		case 'no':
			switch (args[1]) {
				case 'u':
					if (msg.author.bot) return;
					msg.channel.send ('no u');
					logUsedCommand ('no u');
					break;
			}
	}

	//normal commands
	//checks if the author of the message is a bot or doesn't have the prefix, in both cases it cancels the command
	if (msg.author.bot || !msg.content.startsWith (config.prefix)) return;
	//splits the arguments at every space
	var args = msg.content.substring ((config.prefix).length) .split (" ");
	switch (args[0]) {
		case 'belt':
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807048956837928/belt.jpg');
			//send some text to the channel that the command was sent to
			msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!');
			//send the attachment made a couple lines ago
			msg.channel.send (beltAttachment);
			//logs the used command with the function created earlier
			logUsedCommand ('belt');
			//makes sure that the next command isn't executed immediately after this one
			break;
		case 'uno_reverse_card', 'unoreversecard':
			const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807057811275797/uno_u.jpg');
			msg.channel.send (unoAttachment);
			logUsedCommand ('uno reverse card');
			break;
		case 'what\'s_a_tortoise', 'whatsatortoise':
			msg.channel.send ('As we all know, an underdeveloped sad excuse for a turtle.');
			logUsedCommand ('what\'s a tortoise');
			break;
		case 'are_they_groovin?', 'aretheygroovin?', 'are_they_groovin':
			const groovinAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058654068756/groovin.jpg');
			msg.channel.send (groovinAttachment);
			logUsedCommand ('are they groovin\'?');
			break;
		case 'BotInfo', 'botinfo':
			const infoEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
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
			logUsedCommand ('BotInfo');
			break;
		case 'die', 'Die':
			const veryfineattachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/618926084750180363/735560345854148619/crab_shoot_ioxbot.jpg');
			msg.channel.send (veryfineattachment);
			logUsedCommand ('die');
			break;
		case 'coinflip':
			//generates a random number between 1 and 2 and stores it in "random"
			var random = (Math.ceil (Math.random () * 2));
			//declares a variable, "messageToSend", which will be used to store the message text
			var messageToSend;
			//declares a variable, "coinFace", which will be used to store an image link for the corresponding side of the coin
			var coinFace;
			switch (random) {
			//checks if the random number is one
			case '1':
				//changes messageToSend to "Your coin landed on heads!"
				messageToSend = 'Your coin landed on heads!';
				//sets coinFace to a picture of a coin on heads
				coinFace = 'https://cdn.discordapp.com/attachments/728781398911221795/739249818081296384/coin_heads.jpg';
				//break because switches are a bit jank in that they'll execute the next case if you don't break or return
				break;
			case '2':
				//changes messageToSend to "Your coin landed on tails!"
				messageToSend = 'Your coin landed on tails!';
				//sets coinFace to a picture of a coin on tails
				coinFace = 'https://cdn.discordapp.com/attachments/728781398911221795/739249795469803612/coin_tails.jpg';
				break;
			}
			//sends "messageToSend" in an embed with some added things
			const coinEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
				.setTitle ('You flipped a coin!')
				.setDescription (messageToSend)
				.setColor (generateRandomShade ('FF'))
				.setThumbnail (coinFace);
			msg.channel.send (coinEmbed);
			logUsedCommand ('coinflip');
			break;
		case 'i', 'info':
			//creates a second switch for arguments after the original "-info" command
			switch (args[1]) {
				case 'creation_date', 'creationdate':
					//creates an embed, which is then edited by the .set code
					const dateEmbed = new MessageEmbed ()
						//adds a field at the top of the embed which shows "IoxBot", with a small image dictated by the link
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						//set the title of the embed
						.setTitle ('Created on June 17th, 2020')
						// Set the color of the embed's side thingy, in this case green
						.setColor (generateRandomShade ('FF'))
						// Set the main content of the embed
						.setDescription ('Last update was ' + (config.lastUpdate));
						// Send the embed to the same channel as the command
					msg.channel.send (dateEmbed);
					logUsedCommand ('info : creation date');
					break;
				case 'version', 'Version':
					const versionEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (config.version)
						.setColor (generateRandomShade ('FF'));
					msg.channel.send (versionEmbed);
					logUsedCommand ('info : version');
					break;
				case 'author', 'Author':
					const authorEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('IoxBot has been programmed by Ioxom')
						.setColor (generateRandomShade ('FF'))
						.setDescription ('Ioxom is cool give him your cash money')
						.setFooter ('you don\'t want to know what happens if you don\'t');
					msg.channel.send (authorEmbed);
					logUsedCommand ('info : author');
					break;
				case 'commands', 'Commands':
					const commandsEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('IoxBot\'s commands are :')
						.setColor (generateRandomShade ('FF'))
						//adds fields, which are different sections of the embed
						.addFields (
							{ name: 'Normal Commands', value: (config.commands), inline: false },
							{ name: 'Prefixless Commands', value: (config.prefixlessCommands), inline: false }
						);
					msg.channel.send (commandsEmbed);
					logUsedCommand ('info : commands');
					break;
				case 'GitHub', 'github', 'Github':
					const githubEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('Source code For IoxBot can be found here')
    					.setColor (generateRandomShade ('FF'))
    					.setURL (config.githubURL);
					msg.channel.send (githubEmbed);
					logUsedCommand ('info : github');
					break;
			}
	}
})