const Discord = require ('Discord.js') ;
const client = new Discord.Client () ;
const token = 'NzIyODM1MjkwNjQ0ODA3NzEx.Xuo3vQ.ZFGQOKOxVdE0VosQMcDjHc4d8Ww' ;
const prefix = '-';
var channel = '719955731821887602'
var version = '1.0.0';
client.login (token) ;

//sends a message in console when the bot successfully starts up
client.on ('ready', () =>{
    console.log ('bot initialized successfully') ;

})

//this is where new commands are added
client.on ('message', msg =>{
    var args = msg.content.substring (prefix.length) .split (" ");
    switch (args[0]) {
        case 'belt':
            msg.channel.send ('ALRIGHT YOU AINT LISTENIN SO YOURE GETTING THE BELT!') ;
            msg.channel.send ('https://cdn.discordapp.com/attachments/618926084750180363/721009676849905725/belt.jpg')
            break;
        case 'BotInfo':
            switch (args[1]) {
                case 'version':
                    msg.channel.send ('IoxBot version is ' + version);
                    break;
                case 'author':
                    msg.channel.send ('IoxBot, made by Ioxom');
                    break;
                case 'commands':
                    msg.channel.send ('```Commands are BotInfo version, BotInfo author, belt, test```')
                    break;
                case '':
            }
}})
