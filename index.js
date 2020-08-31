//requires
const config = require ('./configuration/config.json');
if (config.lang === 'en_ca') var lang = require ('./configuration/lang/en_ca.json');
const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const updates = require('./configuration/update.json');
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
	//generates a random number between the minimum and maximum defined earlier
	function getrndInteger (min, max) {
		return (Math.floor (Math.random () * (max - min) ) + min);
	}

	//function for logging used commands in the console
	//creates the function with a parameter for what command was used
	function logUsedCommand (commandUsed) {
		//checks if logCommandUses in the config is true
		//prints the id of the user and then "used [command]"
		if (config.logCommandUses === 'true') {
			console.log ((msg.author.tag) + ' used ' + (commandUsed));
		}
	}

	//funtion for generating a random shade of the selected colour for embeds
	//creates the function
	function generateRandomShade (colour) {
		//generates two random numbers between 0 and 75
		var integer1 = (getrndInteger (0, 75));
		var integer2 = (getrndInteger (0, 75));
		//adds a zero to the end of integer1/integer2 if either are below 10, preventing the result from being the wrong colour
		if (integer1 < 10) {
			integer1 = (integer1) + ('0');
		} else if (integer2 < 10) {
			integer2 = (integer2) + ('0');
		}
		//adds the values to the selected colour to create a colour hex code
		shadeResult = ((integer1) + (colour) + (integer2))
		return shadeResult;
	}

	//function to generate a random hex colour for use in embeds
	function generateRandomColour () {
		const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		const numbers = '0123456789';
		//add all the characters together, giving numbers a 2/3 chance to be the result and letters 1/3
		var characters = (letters + numbers + numbers + numbers + numbers + numbers + numbers);
		function selectchars() {
			return (characters[(Math.ceil (Math.random () * 87))]);
		}
		//first character can only be a number to prevent invalid colours
		var result = (numbers[(Math.ceil (Math.random () * 10))] + selectchars() + selectchars() + selectchars() + selectchars() + selectchars());
		return result;
	}

	//prefixless commands
	//checks if enablePrefixlessCommands in the config is true, in which case prefixless commands are available for use
	if (config.enablePrefixlessCommands === 'true') {
		var args = msg.content.substring ("".length) .split (" ");
		switch (args[0]) {
			//checks if the message starts with "IoxBot" in any capitalization, hooray for case spam
			case 'IoxBot': case 'Ioxbot': case 'ioxbot': case 'ioxBot':
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
			case 'hello': case 'Hello':
				switch (args[1]) {
					case 'IoxBot': case 'Ioxbot': case 'ioxbot': case 'ioxBot':
						if (msg.author.bot) return;
						//sends "hello" accompanied by the id of the author of the command message, plus angle brackets and an @ so it's a ping
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
	}

	//normal commands
	//splits the arguments at every space
	var args = msg.content.substring ((config.prefix).length) .split (" ");
	switch (args[0]) {

		//experimental commands
		case 'E': case 'e': case 'experimental':
		if (config.enableExperimentalCommands === 'true') {
			switch (args[1]) {
				case 'colouredembed':
					const embed = new MessageEmbed () 
						.setColor ('0x' + generateRandomColour())
						.setTitle ('this is a test');
					msg.channel.send (embed);
					logUsedCommand ('-experimental colouredembed');
					break;
				case 'loop':
					switch (args[2]) {
					default:
						//sets the number of times to loop to the third part of the sent message (must be a number)
						var amountToLoop = (args[2]);
						switch (args[3]) {
						default:
							//error messages
							if (isNaN(args[2]) && Array.isArray(args[2])) {
								msg.channel.send ('invalid third argument found. use -e help for the proper command usage');
							} else if ((!args[2]) && (!args[3])) {
								msg.channel.send ('missing third and fourth argument. use -e help for the proper command usage');
							}
							//sets the message to send based on the final argument of the command
							var messageToSend = (args[3]);
							//repeats sending the message for the amount of times defined in amountToLoop
							for (var loopCounter = 0; loopCounter < amountToLoop; loopCounter++) {
								msg.channel.send (messageToSend);
							}
							logUsedCommand ('-experimental loop ' + '[repeats: ' + (amountToLoop) + ']' + ' [message: ' + (messageToSend) + ']');
							break;
						}
					}
					break;
				case 'help': case 'Help':
					const helpEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setColor (generateRandomShade('FF'))
						.setTitle ('Using Experimental Commands')
						.addFields (
							{ name: '`loop`', value: '-e loop [repeat count] [message]', inline: false },
							{ name: '`colouredembed`', value: 'sends a randomly coloured embed', inline: false}
						);
					msg.channel.send (helpEmbed);
					logUsedCommand('-experimental help');
					break;
			}
			break;
		}

		//normal commands
		case lang.belt.trigger.one: case lang.belt.trigger.two:
			// Create the belt attachment using MessageAttachment
			const beltAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807048956837928/belt.jpg');
			//send some text to the channel that the command was sent to
			msg.channel.send (lang.belt.message);
			//send the attachment made a couple lines ago
			msg.channel.send (beltAttachment);
			//logs the used command with the function created earlier
			logUsedCommand (lang.belt.log);
			//makes sure that the next command isn't executed immediately after this one
			break;
		case lang.uno.trigger.one: case lang.uno.trigger.two: case lang.uno.trigger.separated.one:
			//uses the "default" case to allow -uno reverse card (spaces)
			switch (args[1]) {
			default: case lang.uno.trigger.separated.two:
				switch (args[2]) {
				default: case lang.uno.trigger.separated.two:
					const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807057811275797/uno_u.jpg');
					msg.channel.send (unoAttachment);
					logUsedCommand (lang.uno.log);
					break;
				}
			}
			break;
		case lang.tortoise.trigger.one: case lang.tortoise.trigger.two: case lang.tortoise.trigger.separated.oneA: case lang.tortoise.trigger.separated.oneB:
			switch (args[1]) {
			default: case lang.tortoise.trigger.separated.two:
				switch (args[2]) {
				default: case lang.tortoise.trigger.separated.three:
					msg.channel.send (lang.tortoise.message);
					logUsedCommand (lang.tortoise.log);
					break;
				}
			}
			break;
		case lang.groovin.trigger.A: case lang.groovin.trigger.B: case lang.groovin.trigger.separated.A:
			switch (args[1]) {
			default: case lang.groovin.trigger.separated.B:
				switch (args[2]) {
				default: case lang.groovin.trigger.separated.C1: case lang.groovin.trigger.separated.C2:
				case lang.groovin.trigger.separated.C3: case lang.groovin.trigger.separated.C4:
					const groovinAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807058654068756/groovin.jpg');
					msg.channel.send (groovinAttachment);
					logUsedCommand (lang.groovin.log);
					break;
				}
			}
		case lang.botinfo.trigger.A: case lang.botinfo.trigger.B: case lang.botinfo.trigger.C:
			const infoEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
				.setTitle (lang.botinfo.title)
				.addFields (
					{ name: lang.botinfo.fields.Creation_date.name, value: lang.botinfo.fields.Creation_date.value, inline: true },
					{ name: lang.botinfo.fields.Gender.name, value: lang.botinfo.fields.Gender.value, inline: true },
					{ name: lang.botinfo.fields.Version.name, value: '`' + (updates.version) + '`', inline: true },
					{ name: 'GitHub', value: '`Ioxom/IoxBot`', inline: true },
					{ name: lang.botinfo.fields.Creator.name, value: lang.botinfo.fields.Creator.value, inline: true },
					{ name: lang.botinfo.fields.Prefix.name, value: '`' + (config.prefix) + '`', inline: true },
				);
			msg.channel.send (infoEmbed);
			logUsedCommand (lang.botinfo.log);
			break;
		case 'die':
		case 'Die':
			const veryfineattachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/618926084750180363/735560345854148619/crab_shoot_ioxbot.jpg');
			msg.channel.send (veryfineattachment);
			logUsedCommand ('die');
			break;
		case lang.coinflip.trigger.A: case lang.coinflip.trigger.B:
			//generates a random number between 1 and 2 and stores it in "random"
			var random = (Math.ceil (Math.random () * 2));
			//declares two variables to be used later
			var messageToSend;
			var coinFace;
			switch (random) {
				//checks if the random number is one/two
				//then sets variables to the corresponding heads/tails messages/images
				case '1':
					messageToSend = lang.coinflip.messageHeads;
					coinFace = 'https://cdn.discordapp.com/attachments/728781398911221795/739249818081296384/coin_heads.jpg';
					break;
				case '2':
					messageToSend = lang.coinflip.messageTails;
					coinFace = 'https://cdn.discordapp.com/attachments/728781398911221795/739249795469803612/coin_tails.jpg';
					break;
				//error message
				default:
					console.log (lang.coinflip.error);
			}
			//sends "messageToSend" in an embed along with an image of the coin side
			const coinEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
				.setTitle (lang.coinflip.messageTitle)
				.setDescription (messageToSend)
				.setColor (generateRandomShade ('FF'))
				.setThumbnail (coinFace);
			msg.channel.send (coinEmbed);
			logUsedCommand (lang.coinflip.log);
			break;
		case 'i':
		case 'info':
			//creates a second switch for arguments after the original "-info" command
			switch (args[1]) {
				case 'creation_date': case 'creationdate': case 'creation':
					switch (args[2]) {
					default: case 'date':
						//creates an embed, which is then edited by the .set code
						const dateEmbed = new MessageEmbed ()
							//adds a field at the top of the embed which shows "IoxBot", with a small image dictated by the link
							.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
							//set the title of the embed
							.setTitle ('Created on June 17th, 2020')
							// Set the color of the embed, in this case green
							.setColor (generateRandomShade ('FF'))
							// Set the main content of the embed
							.setDescription ('Last update was ' + (config.lastUpdate));
							// Send the embed to the same channel as the command
						msg.channel.send (dateEmbed);
						logUsedCommand ('info : creation date');
						break;
					}
				case 'version': case 'Version':
					const versionEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (config.version)
						.setColor (generateRandomShade ('FF'));
					msg.channel.send (versionEmbed);
					logUsedCommand ('info : version');
					break;
				case 'author': case 'Author':
					const authorEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('IoxBot has been programmed by Ioxom')
						.setColor (generateRandomShade ('FF'))
						.setDescription ('Ioxom is cool give him your cash money')
						.setFooter ('you don\'t want to know what happens if you don\'t');
					msg.channel.send (authorEmbed);
					logUsedCommand ('info : author');
					break;
				case 'commands': case 'Commands':
					const commandsEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('IoxBot\'s commands are :')
						.setColor (generateRandomShade ('FF'))
						//adds fields, which are different sections of the embed
						.addFields (
							{ name: 'Normal Commands', value: (updates.commands), inline: false },
							{ name: 'Prefixless Commands', value: (updates.prefixlessCommands), inline: false }
						);
					msg.channel.send (commandsEmbed);
					logUsedCommand ('info : commands');
					break;
				case 'GitHub': case 'Github': case 'github': case 'gitHub':
					const githubEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle ('Source code For IoxBot can be found here')
    					.setColor (generateRandomShade ('FF'))
    					.setURL ('https://github.com/Ioxom/IoxBot');
					msg.channel.send (githubEmbed);
					logUsedCommand ('info : github');
					break;
			}
	}
})