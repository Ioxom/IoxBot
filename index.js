//json requires
const config = require ('./configuration/config.json');
if (config.lang === 'en_ca') var lang = require ('./configuration/lang/en_ca.json');
const updates = require('./configuration/update.json');
//requires for xp system
const fs = require("fs");
const lineReader = require('line-reader');
//initializes djs
const { Client, MessageAttachment, MessageEmbed } = require ('discord.js');
const client = new Client ();
//we're storing variables in the config/updates.json now, makes this file very clean
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
		return (Math.floor(Math.random() * (max - min)) + min);
	}

	//function for logging used commands in the console
	//creates the function with a parameter for what command was used
	function logUsedCommand (commandUsed) {
		//checks if logCommandUses in the config is true
		//prints the id of the user and then "used [command]"
		if (config.logCommandUses) {
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
		return ((integer1) + (colour) + (integer2))
	}

	//function to generate a random hex colour for use in embeds
	function generateRandomColour () {
		const numbers = '0123456789';
		//add all the characters together, giving numbers a 2/3 chance to be the result and letters 1/3
		var characters = ('ABCDEFGHIJKLMNOPQRSTUVWXYZ' + (numbers + numbers + numbers + numbers + numbers + numbers) );
		function selectchars() {
			return (characters[(Math.ceil (Math.random () * 87))]);
		}
		//first character can only be a number to prevent invalid colours
		return (numbers[(Math.ceil(Math.random() * 10))] + selectchars() + selectchars() + selectchars() + selectchars() + selectchars());
	}

	//prefixless commands
	//checks if enablePrefixlessCommands in the config is true, in which case prefixless commands are available for use
	if (config.enablePrefixlessCommands) {
		var args = msg.content.substring("".length).split (" ");
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
			if (msg.content === 'E') msg.react('🇪');
		}
	}

	var args = msg.content.substring ((config.prefix).length) .split (" ");
	switch (args[0]) {

		//experimental commands
		case 'E': case 'e': case 'experimental':
		if (config.enableExperimentalCommands) {
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
						var deleteLog = '[deleted?: false]';
						switch (args[3]) {
						default: switch (args[4]) {
							case 'delete':
								msg.delete({ timeout: 100 });
								deleteLog = '[deleted?: true]';
						}
							//error messages
							if ((!args[2]) && (!args[3])) {
								msg.channel.send ('missing third and fourth argument. use -e help for the proper command usage');
							}
							//sets the message to send based on the final argument of the command
							var messageToSend = (args[3]);
							//repeats sending the message for the amount of times defined in amountToLoop
							for (var loopCounter = 0; loopCounter < amountToLoop; loopCounter++) {
								msg.channel.send (messageToSend);
							}
							logUsedCommand ('-experimental loop ' + '[repeats: ' + (amountToLoop) + ']' + ' [message: ' + (messageToSend) + '] ' + deleteLog);
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
							{ name: '`loop`', value: '-e loop [repeat count] [message] [delete(optional)]', inline: false },
							{ name: '`colouredembed`', value: 'sends a randomly coloured embed', inline: false}
						);
					msg.channel.send (helpEmbed);
					logUsedCommand('-experimental help');
					break;
			}
			break;
		}

		//normal commands
		case lang.belt.trigger.A: case lang.belt.trigger.B:
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
		case lang.uno.trigger.A: case lang.uno.trigger.B: case lang.uno.trigger.separated.C:
			//uses the "default" case to allow -uno reverse card (spaces)
			switch (args[1]) {
			default: case lang.uno.trigger.separated.B:
				switch (args[2]) {
				default: case lang.uno.trigger.separated.C:
					const unoAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/719955731821887602/733807057811275797/uno_u.jpg');
					msg.channel.send (unoAttachment);
					logUsedCommand (lang.uno.log);
				}
			}
			break;
		case lang.tortoise.trigger.A: case lang.tortoise.trigger.B: case lang.tortoise.trigger.separated.A1: case lang.tortoise.trigger.separated.A2:
			switch (args[1]) {
			default: case lang.tortoise.trigger.separated.B:
				switch (args[2]) {
				default: case lang.tortoise.trigger.separated.C:
					msg.channel.send (lang.tortoise.message);
					logUsedCommand (lang.tortoise.log);
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
				}
			}
			break;
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
		case lang.coinflip.trigger.A: case lang.coinflip.trigger.B:
			//declares "messages so that it isn't local to the switch"
			var messages;
			//random number between 1 and 2
			switch (Math.ceil(Math.random() * 2)) {
				//checks if the random number is one/two
				//then sets variables to the corresponding heads/tails messages/images
				case '1':
					messages = ['https://cdn.discordapp.com/attachments/728781398911221795/739249818081296384/coin_heads.jpg', lang.coinflip.messageHeads];
					break;
				case '2':
					messages = ['https://cdn.discordapp.com/attachments/728781398911221795/739249795469803612/coin_tails.jpg', lang.coinflip.messageTails]
					break;
				//error message
				default:
					console.log (lang.coinflip.error);
			}
			//sends messages[0] in an embed along with an image of the coin side
			const coinEmbed = new MessageEmbed ()
				.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
				.setTitle (lang.coinflip.messageTitle)
				.setDescription (messages[1])
				.setColor (generateRandomShade ('FF'))
				.setThumbnail (messages[0]);
			msg.channel.send (coinEmbed);
			logUsedCommand (lang.coinflip.log);
			break;
		case 'i': case 'info':
			//creates a second switch for arguments after the original "-info" command
			switch (args[1]) {
				case lang["info:creationdate"].trigger.A: case lang["info:creationdate"].trigger.B: case lang["info:creationdate"].trigger.separated.A:
					switch (args[2]) {
					default: case lang["info:creationdate"].trigger.separated.B:
						//creates an embed, which is then edited by the .set code
						const dateEmbed = new MessageEmbed ()
							//adds a field at the top of the embed which shows "IoxBot", with a small image dictated by the link
							.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
							//set the title of the embed
							.setTitle (lang["info:creationdate"].title)
							// Set the color of the embed, in this case a random shade of green created by our function
							.setColor (generateRandomShade ('FF'))
							// Set the main content of the embed
							.setDescription (lang["info:creationdate"].description + (config.lastUpdate));
							// Send the embed to the same channel as the command
						msg.channel.send (dateEmbed);
						logUsedCommand (lang["info:creationdate"].log);
						break;
					}
				case lang["info:version"].trigger.A: case lang["info:version"].trigger.B:
					const versionEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (updates.version)
						.setColor (generateRandomShade ('FF'));
					msg.channel.send (versionEmbed);
					logUsedCommand (lang["info:version"].log + ' [' + (updates.version) + ']');
					break;
				case lang["info:author"].trigger.A: case lang["info:author"].trigger.B:
					const authorEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (lang["info:author"].title)
						.setColor (generateRandomShade ('FF'))
						.setDescription (lang["info:author"].description)
					msg.channel.send (authorEmbed);
					logUsedCommand (lang["info:author"].log);
					break;
				case lang["info:commands"].trigger.A: case lang["info:commands"].trigger.B:
					const commandsEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (lang["info:commands"].title)
						.setColor (generateRandomShade ('FF'))
						//adds fields, which are different sections of the embed
						.addFields (
							{ name: lang["info:commands"].fields.field1, value: (updates.commands), inline: false },
							{ name: lang["info:commands"].fields.field2, value: (updates.prefixlessCommands), inline: false }
						);
					msg.channel.send (commandsEmbed);
					logUsedCommand (lang["info:commands"].log);
					break;
				case lang["info:github"].trigger.A: case lang["info:github"].trigger.B:
				case lang["info:github"].trigger.C: case lang["info:github"].trigger.D:
					const githubEmbed = new MessageEmbed ()
						.setAuthor ('IoxBot', 'https://cdn.discordapp.com/attachments/618926084750180363/742202185454190692/ioxbot_profile_photo.png')
						.setTitle (lang["info:github"].title)
    					.setColor (generateRandomShade ('FF'))
    					.setURL ('https://github.com/Ioxom/IoxBot');
					msg.channel.send (githubEmbed);
					logUsedCommand (lang["info:github"].log);
					break;
			}
	}
});

//xp system
if (config.enableXPsystem) {
	client.on ('message', msg => {
		var args = msg.content;
		switch (args) {
			default:
				fs.readFile('stats.txt', 'utf8', function(err, fulldata) {
					var isComplete = false;
					if (err) throw(err);
					var i = -1;
					lineReader.eachLine('stats.txt', function(data) {
						//in "data"(array), stores the user and their score (user: data[0]) (score: data[1]);
						data = data.split(' - ');
						i++;
						console.log (i, ' ', data[0]);
						if (data[0] == msg.author.id) {
							var dataArray = fulldata.split('\n');
							console.log(dataArray);
							delete dataArray[i];
							console.log(dataArray);
							//would remove empty sections of the array if it worked
							function fixArray() {
								var fixedArray = [];
								for (let h = (Object.keys(dataArray).length); h > 0; h--) {
									if (dataArray[h] == '' || undefined) return;
									console.log('pushing')
									fixedArray.push(dataArray[h])
									console.log('pushed: ' + dataArray[h])
								}
								console.log(fixedArray)
								return fixedArray;
							}
							dataArray = fixArray();
							var array = ['e', 'e', 'e']
							console.log(dataArray)
							console.log(array)
							console.log ('e' + array.join('\n'))
							console.log ('joined array: ' + dataArray.join('\n'));
							dataArray = dataArray.join('\n')
							//adds the user's old score to a stupidly complicated equation that's about (characters in their message) / 4
							var score = parseInt(data[1]) + (Math.round((args.length * 13 / (25 / 0.987) + 0.43 / 0.89 - 0.19) / 2.75));
							console.log('score: ' + score + ' old score: ', data[1], ' character count: ', args.length)
							var newData = '\n' + msg.author + ' - ' + score;
							//writes the score to stats.txt, giving them a newline if they have no existing score
							fs.writeFile ('stats.txt', (dataArray), 'utf8', function(err) {
								if (err) return err;
								console.log ('wrote new data successfully.');
								fs.appendFile('stats.txt', (newData), 'utf8', function(err) {
									if (err) throw err;
									console.log('appended successfully');
								})
							})
							//stops new data being created
							isComplete = true;
							//stops lineReader from continuing to read the file
							return false;
						}
					})
					if (isComplete == false) {
						fs.appendFile('stats.txt', ('\n' + msg.author.id + ' - ' + (Math.round((args.length * 13 / (25 / 0.987) + 0.43 / 0.89 - 0.19) / 2.75))), 'utf8', function(err) {
							if(err) throw(err);
							console.log('added new user sucessfully');
						})
					}
				})
				break;
		}
	})
}