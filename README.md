# IoxBot
A discord bot made by yours truly. This bot has no servers, so you'd have to run it yourself to add an edit to your server. There's some weird stuff going on with this repository as I've just made it public through github desktop but ignore that.
# Commands
-belt | Abuse your friends!

-Info | Information about the bot. Includes author, GitHub, commands, creation_date and version. Alternatively you can use -botinfo to get a limited info page.

-uno_reverse_card | Pull a no u on the haters.

-are_they_groovin? | Are they?

-coinflip | Flips a coin.

-experimental | interesting experimental commands. Includes loop and colouredembed. Use -e help to find out more

Also a few prefixless commands which can be disabled in the config, as they can get annoying at times.
# Config
token - enter your token here

logCommandUses - set this to "false" if you do not want information about used commands in the console

prefix - the prefix used for executing commands

enablePrefixlessCommands - enables a few fun commands that don't use the prefix when true

enableExperimentalCommands - enables -e, which includes a few interesting commands

lang - not functional yet but will be useful once there's another translation available
# Recent Updates
2020/08/30 - IoxBot 1.0! Changelog - add lang file, reorganise configuration, clean code, delete -die, allow spaces to be used in commands such as -unoreversecard, edit comments so it almost seems like I know what I'm doing

2020/08/27 - Repurpose E to trigger experimental commands, add -E loop

2020/08/12 - Commands will now work if not capitalized properly

2020/08/06 - All embeds now use a random shade of green instead of the generic 0x00FF00

2020/08/04 - Move command logging to a function to clean code and decrease file size

2020/08/01 - Add coinflip command, the most complicated command yet

2020/07/31 - Move everything possible to the config file

2020/07/27 - Add an option for logging which commands are used in the console

2020/07/19 - Create config.json to store variables, add general botinfo page

2020/07/18 - Add -i as an alternative because typing BotInfo is cringe

2020/07/16 - Add status and more comments 

2020/07/15 - Tweak prefixless commands so they trigger more often, then fix all the fabulous bugs I created

2020/07/11 - Add prefixless commands

2020/07/09 - Make the code a bit easier easier to understand using comments

2020/07/06 - Add new BotInfo command, "creation_date"

2020/07/06 - Convert all BotInfo commands to embeds, various improvements

2020/07/04 - Remove links for images using function MessageAttachment
