const { Client, MessageAttachment } = require('discord.js');
const client = new Client();
const prefix = ('-');
const token = ('NzIyODM1MjkwNjQ0ODA3NzEx.Xuo3vQ.ZFGQOKOxVdE0VosQMcDjHc4d8Ww');
//a couple variables for making updating things easier
const version = '1.0.0';
var commands = 'BotInfo version, BotInfo author, BotInfo commands, BotInfo GitHub, belt, uno_reverse_card, are_they_groovin? (in progress)'
client.login ('NzIyODM1MjkwNjQ0ODA3NzEx.Xuo3vQ.ZFGQOKOxVdE0VosQMcDjHc4d8Ww') ;

//sends a message in console when the bot successfully starts up
client.once ('ready', () =>{
	console.log ('bot initialized successfully') ;
})

client.on ('message', msg => {
	if (msg.content = '-belt') {
	  // Create the belt attachment using MessageAttachment
	  const beltAttachment = new MessageAttachment ('https://cdn.discordapp.com/attachments/618926084750180363/721009676849905725/belt.jpg');
	  msg.channel.send ('ALRIGHT YOU AIN\'T LISTENIN\' SO YOU\'RE GETTING THE BELT!')
	  msg.channel.send (beltAttachment);
	}
	if (msg.content = '-are_they_groovin')
	var groovin = (Math.floor (Math.random() * 10 ))
	console.log (groovin)
		if (groovin = 1) {
			const groovinAttachment1 = new MessageAttachment('https://cdn.discordapp.com/attachments/618926084750180363/717833399825334303/Screen_Shot_2019-07-31_at_12.png');
			msg.channel.send ('groovinAttachment1')
			return;
		}
		if (groovin = 2,3,4,5,6,7,8,9,10) {
			const groovinAttachment2 = new MessageAttachment('https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQnpJBB55my5LQ-7rf3LVTqMdAYwVPJ2VwnD8dbgq22qEA4DiiC&usqp=CAU');
			msg.channel.send (groovinAttachment2)
			return;
		}
});

client.on ('message', msg => {
	if (msg.content = '-are_they_groovin')
	var groovin = (Math.floor (Math.random() * 10 ))
	console.log (groovin)
		if (groovin = 1) {
			const groovinAttachment1 = new MessageAttachment('https://cdn.discordapp.com/attachments/618926084750180363/717833399825334303/Screen_Shot_2019-07-31_at_12.png');
			msg.channel.send (groovinAttachment1)
			return;
		}
		if (groovin = 2,3,4,5,6,7,8,9,10) {
			const groovinAttachment2 = new MessageAttachment('https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQnpJBB55my5LQ-7rf3LVTqMdAYwVPJ2VwnD8dbgq22qEA4DiiC&usqp=CAU');
			msg.channel.send (groovinAttachment2)
			return;
		}
});

//this is where new commands are added
client.on ('message', msg => {
	var args = msg.content.substring (prefix.length) .split (" ");
	switch (args[0]) {
		case 'uno_reverse_card':
			msg.channel.send ('https://cdn.discordapp.com/attachments/642003331656581170/717832539896807434/nxeiZ78_ZCkQvMGkZ7fSTmixJ2ey4JCSlOuyKQL6Gos-1.png')
			break;
		case 'what\'s_a_tortoise':
			msg.channel.send ('As we all know, an underdeveloped sad excuse for a turtle.')
			break;
		case 'BotInfo':
			switch (args[1]) {
				case 'version':
					msg.channel.send ('```IoxBot version is ' + version + '```');
					return;
				case 'author':
					msg.channel.send ('```IoxBot, made by Ioxom```');
					return;
				case 'commands':
					msg.channel.send ('```Commands are ' + commands + '```')
					return;
				case 'GitHub':
					msg.channel.send ('```https://github.com/Ioxom/IoxBot```')
					return;

			}

	}
<<<<<<< Updated upstream
})
=======
})
>>>>>>> Stashed changes
