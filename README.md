# CSC 120 Final Project: D&D Combat Tracker (Primarily for DMs)
## D&D Combat - The System

D&D combat is a lot to keep track of. DMing a large encounter can be a major challenge. This project intends to make a tracker to help. D&D is based around making rolls and checking against difficulties. Tests are made by rolling 20-sided dice (d20s for short) and adding modifiers. The difficulties in question are called DCs. Tests succeed if they equal or exceed the given DC.

Here is a simple stat block, holding the information needed to run combat against a basic enemy, a bandit. Each line is labeled with what it means.

```
Bandit                                                                                          > name of monster
Small or Medium Humanoid, Neutral                                                               > type + moral position + size
AC 12                                                                                           > armor class
Initiative +1 (11)                                                                              > initiative bonus with default in parentheses
HP 11 (2d8 + 2)                                                                                 > hit points
Speed 30 ft.                                                                                    > speed in feet per round
        Str     Dex     Con     Int     Wis     Cha                                             
        11       12      12      10      10      10                                             > ability scores
Bonus:  +0       +1      +1      +0      +0      +0                                             > bonuses for abilities
Save:   +0       +1      +1      +0      +0      +0                                             > bonuses for saving throws
Gear: Leather Armor, Light Crossbow, Scimitar                                                   > gear for bandit
Senses: Passive Perception 10                                                                   > special senses + a number indicating awareness of environment
Languages: Common, Thieves' cant                                                                > languages known
CR 1/8 (XP 25; PB +2)                                                                           > number indicating strength

Actions 
Scimitar. Melee Attack Roll: +3, reach 5 ft. Hit: 4 (1d6 + 1) Slashing damage.                  > melee attack with +3 bonus, dealing 1d6+1 damage on a hit
Light Crossbow. Ranged Attack Roll: +3, range 80/320 ft. Hit: 5 (1d8 + 1) Piercing damage.      > ranged attack with +3 bonus, dealing 1d8+5 damage on a hit
```

The way combat works is all entities (players and monsters) roll initiatve by rolling 1d20 and adding their initiative modifer. They then take turns doing things, in decreasing order of initiative (higher initiative goes earlier). For this reason, the term 'roll initiative' has the special meaning of begin combat.

On an entity's turn, they can move their speed in feet (which is most interesting on a grid map), and take 1 action and 1 bonus action. They can also take 1 reaction between (including current turn) each of their turns, which have specific triggers.

The most common actions are attacks. To make an attack, an entity chooses a creature within range (ranged attacks) or reach (melee attacks) and rolls 1d20 and adds their attack modifier. Certain conditions can impose advantage or disadvantage, which forces them to roll twice and take the highest (advantage) or lowest (disadvantage). They then compare the result to the AC of their target. If the attack roll equals or exceeds the target's AC, it hits and deals the given amount of damage. If the roll of the d20 is 1, the attack automatically misses. This is known as a critical miss. If the roll is a 20, this is a critical hit and deals double dice in damage.

Some entities can cast spells or do other abilities with their actions. Much of the time, these abilities will either create attacks or force a saving throw. They will have an associated Difficulty Class (DC) for short and ability. For instance, a fireball requires a Dexterity Saving Throw, or Dex Save for short. The target or targets must then make the given save, by rolling 1d20 and adding their saving throw modifier for the given save. If the save equals or exceeds the DC, it is a success. Otherwise, it fails. Unlike attacks, saves often have effects on a success.

Very powerful entities can also have Lair Actions and Legendary Actions. Lair actions always take place at initiative 20 and only in certain places. Legendary Actions take place at the end of any other creature's turn. They are a good way for powerful entities to overpower multiple foes. In a number of modern stat blocks, Legendary Actions are replaced by having more reactions.

In addition to their actions, bonus actions, reactions, and legendary actions, entities also can have traits. These can include abilities to absorb certain types of damage, penalties causing weaknesses, or even immunity to low-level magic. The most common trait however, is legendary resistance. This ability has a number of uses per day and can be used to turn a failed saving throw into a success.

Most monsters die when reduced to 0 HP, although some have a more complex dying process that is the same used by player characters.

### Sample Encounter
Here is a very simple fight. Two of the bandit creatures described above are fighting a wizard. The first bandit rolls a natural 20, for an initiative of 21, while the second bandit rolls a 15. The wizard rolls a 10. The first bandit then attacks with their crossbow, rolling a natural 1 and missing. The second rolls a 10+3 = 13, which is the AC of the wizard. The wizard is hit for 1d8+1 damage, which turns out to be 2. They remove those 2 damage from their 20 HP. Then, they throw a fireball at the bandits. The bandits each need to roll a dex save against their DC of 13. The first rolls 14+1 = 15 for a success, while the second rolls 10+1 = 11 for a failure. The spell deals 8d6 fire damage on a success and half that on a failure, which turns out to be 28 total, which kills both bandits by reducing their HP to 0.

## Using Our Code
Our code will be based around an object called an EncounterBuilder. While 'builder' is often a word indicating a specific task in CS, in the D&D world, a tool designed to help a dungeon master create an encounter is almost always called an encounter builder, hence the use of the term here. This object will manipulate Encounter objects, which actually hold the information required to run encounters. To set up an encounter builder, the following code is all that is needed.
```
EncounterBuilder myEncounterBuilder = new EncounterBuilder();
myEncounterBuilder.buildEncounter();
```
This is exactly what happens in our Main.java file, so you could also just run that.

### Encounter Builder Commands
Running the .buildEncounter() method starts up JEB - the Java Encounter Builder. JEB has a bunch of commands, which allow you to build an encounter object. When run, it will prompt you to ask for a command. The 'help' command will bring up the list. To run a command, enter it in the command line. It will then prompt for the information needed.

**add player** - This command adds a player-controled character to the encounter. The player character is indicated by character name and player name, and during combat will prompt for the player-rolled initiative rather than rolling automatically.

**add monster** - This command adds a single entity defined by a stat block to the encounter. The stat block will be in the MonsterFiles folder, and must have a particular format, which will be described later. This is not a default format typically fetched off the web, but is easiest to code and reasonably easy to turn a standard off-the-web stat block into.

**add multiple monsters** - This command adds a given number of monsters to the encounter, which is like repeating the *add monster* command a certain number of times. This is good for a fight with 5 goblins or 10 guards, to avoid excessibve typing, and keeps the monsters seperate.

**add monster with note** - This command does the same thing as *add monster* but also allows the user to add a note to make it easier to keep track of things in large encounters. This is great for when there are multiple factions that the DM needs to track, or for keeping track of monster location or number. A note could be *1* to indicate goblin 1, *ugly* if your labels are more discriptive, *left* if the entities start in a row, or *Kevin's faction* if the entity is associated with a particular faction (this example being one run by the noble Kevin).

**add placeholder** - This command adds a placeholder at a particular initiative, for a particular event.

**add unit** - This command adds an entity composed of some number of entites defined by a stat block to the encounter. Units of this sort follow rules I created for my personal campaign, and are nonstandard. These entities might have something special added because of their nature as a composite or might just take damage together to ease running large encounters. They have a size, indicating the number of entities composing the unit, and have HP equal to the base HP for the composing monster multiplied by the size. As they take damage, the size decreases. Unit size must be a positive integer.

**add unit with note** - This command does the same thing as *add unit* but also allows the user to add a note to make it easier to keep track of things in large encounters, the same way *add monster with note* does.

**remove player** - This command removes a player-controled character from the encounter, and can take as the name either the player name or the character name.

**remove monster** - This command removes a monster or unit from the encounter, since units function exactly like monsters, only with some modified rules. The name must be the proper name, printed when the monster is added, not the file name used to fetch the monster. *If someone in the future wants to improve this code, allowing more flexible inputs to this command might be a good idea.*

**remove monsters** - This command removes all monsters or units with the same name from the encounter. This is especially useful when you have added multiple monsters and want to remove them all or when you have some monsters of a type with and some without a note, and you have to change something, since you can't tell by monster name which one has the note. *If someone in the future wants to improve this code, allowing notes to be identified when removing monsters might be a good place to start!*

**run encounter** - This command runs the encounter that the encounter builder is currently working on.

**print encounter** - This command prints a list of creatures in the encounter. This will include notes.

**list avaliable monsters** - This command lists the monster names for monsters with stat blocks avaliable to use. These are the names that you need to give when *add monster* or *add unit* asks you to specify the monster you want to add.

**save encounter** - This commander saves the encounter as an encounter file. Such files can be loaded later.

**list saved encounters** - This command lists the file names of encounters that are saved and thus can be loaded. The commande *load encounter* requires that the encounter it prompts for get one of these as the user input.

**load encounter** - This command loads an encounter from a saved file. It will ask which encounter to load, the resulting input must be a file name that *list saved encounters* would print.

**clear encounter** - This command removes all entities from the current encounter, reseting it to a new encounter.

**help** - This command prints the list of commands that the encounter builder will accept.

**close** - This command quits this builder. 

### Running Encounters with this Tool
When the encounter builder is told to *run encounter*, it will stop modifying the encounter and instead run it. It will roll initiative for all the creatures, add lair action placeholders if lair actions are present, and prompt the players for the initiatives they have rolled.

Once initiative is rolled, the tool will go through the initiative order over and over. If the current initiative is that of a unit or monster, it prints a turn prompt for the creature including the stat block, health, and any notes it has. The health indicated on the stat block is the HP maximum, not the current health. If the creature is a unit, that health is the individual HP of the individuals composing it. If the current initiative is a placeholder, like for lair actions, it will print a short statement about what the placeholder is for. And if the current initiative is that of a player, it will prompt the player to take their turn. Regardless of turn prompt, it will then accept a variety of possible commands to help the dungeon master run the encounter and track things, detailed below.

**summary** - This command prints a concise summary of characters in combat, with their indicies, in initiative order. The index of a character is used in many commands to indicate what creature a command affects, so it is useful to keep track of. If you forgot to number the monsters and are running an encounter with a map, they also make good labels in a pinch.

**end turn** - This command makes the encounter go on to the next turn. It will print the turn prompt for the next monster, as well as change the index that is the current initiative, which might matter for some commands.

**close** - This command quits out of the whole program. Once an encounter is closed, the code must be re-run to use it again. You cannot run multiple encounters the same time you run the program. *It might be a good plan to change this in the future, though.*

**roll** <*dice formula*> - This command rolls and prints the result of <*dice formula*>, which is frmatted in the standard format for writing dice rolls. This format is composed of the sum and difference of formulas written like *n*d*m* as well as normal integers. The *n* indicates a number of dice to roll and add, while the *m* indicates the size of the dice. As an example, 'roll 3d6+5' rolls 3 6-sided dice, adds the results, and then adds 5, while 'roll 1d20-2' rolls a 20-sided die and subtracts 2 from the result.

**damage** <*index*> <*amt*> - This command makes the creature at index <*index*> take <*amount*> damage. It will then print a quick summary of what happened. Players and placeholders cannot be damaged in this way. For instance, 'damage 0 14' will have the creature at index 0 take 14 damage. If this creature started at 20/31 health, they would now be at 6/31 health and a message stating this would be printed. If this creature was at 2/31 health, they would drop to 0/31. Creatures cannot have health lower than 0.

**heal** <*index*> <*amt*> - This command heals the creature at index <*index*> by <*amount*>. It will then print a quick summary of what happened. Players and placeholders cannot be healed in this way. For instance, 'heal 0 14' will heal index 0 by 14. If this creature started at 20/31 health, they would now be at 31/31 health and a message stating this would be printed. If this creature was at 2/31 health, they would now have 16/31 health.  Creatures cannot have health greater than their 'HP max' built into their stat block.

**take note** <*index*> - This command prompts the user for a note and then adds that note to the notes for the creature at index <*index*>. This creature must be a monster or a unit, placeholders and players do not have note taking capacity. If there is no index specified, the creature that gains the note is the creature whose turn it currently is.

**stats** <*index*> - This command prints the stat block of the creature <*index*>. This creature must be a monster or a unit, placeholders and players do not have stat blocks. *In the future, it might be worthwhile to make this command print the stat block of the creature with the current initiative if no index is specified.*

**save** - This command saves the encounter to a file, after prompting for a name for that file.

## Writing Monster Files
A .txt file of a particular format can be used to store monster information. When you download this program, there will be a folder called *MonsterFlies*. Whatever you do ***do not change the name of this folder.*** This is where the .txt files for monsters live. 

The name of a monster file must be all in lowercase and contain no spaces. Use underscores to replace spaces. I highly recommend chosing a name similar to that of the monster or unit whose stat block the file contains.

The first line of the monster file must be the monster name, while the second must contain the armor class, health, and initiative bonus of the monster, preferably in that order. All three values must be integers that are proceeded by a label then whitespace, and must have at least a single space (not a tab) after them, before the next word (if they are at the end of the line, this can be skipped). The armor class is labeled with 'AC', hit point maximum by 'HP', and initiative bonus by 'Initiative'. This is case sensitive. For an example with placeholders for values, see below.

```
<Name>
AC <AC>         HP <Max HP>     Initiative <Initiative Bonus>
...
```
The stat block then just needs to be human-readable after these two lines. However, formatting the whole thing as follows might make life easier for future programmers who want to automate more elements of combat, such as saving throws, reactions, and lair actions. I recommend using tab for multiline traits and abilities, such as spellcasting.
```
<Name>
AC <AC>     HP <Max HP>   Initiative <Initiative Bonus>
<Type>
STR             DEX             CON             INT             WIS             CHA	        	Prof.
<STR bonus>     <DEX bonus>     <CON bonus>     <INT bonus>     <WIS bonus>     <CHA bonus>     <Proficiency bonus>
Saves: <list of saves in the format TYPE bonus, all other saves are just +normal bonus>
Skills: <List of skills with additional bonus in the form <bonus SKILL>>
<Speeds in the format n ft TYPE>
Languages: <list of known languages>
Senses: <list of unusual senses>           Passive Perception <Passive Perception>

Traits
<Trait Name> (N/day) - <Trait Text>
<Trait Name> - <Trait Text>

Actions
<Action Name> (N/day) - <Action Text>
<Action Name> (Recharge <recharge range>) - <Action Text>
<Action Name> - <Action Text>

Bonus Actions
<Bonus Action Name> (N/day) - <Bonus Action Text>
<Bonus Action Name> - <Bonus Action Text>


Reactions (N/round)          < Note that (N/round) is only needed if there are more than 1
<Descriptor text>
<Reaction Name> (N/day) - <Reaction Text>
<Reaction Name> - <Reaction Text>

Legendary Actions (N/round)
<Descriptor text>
<Action Name> - <Action text>
<Action Name> (Costs N) - <Action text>


Lair Actions
<Descriptor Text>
<Lair Action Name> - <Lair Action Text>
```
### Examples
Here are a few example stat blocks.
```
Ancient Green Dragon
AC 21       HP 402 (23d20 + 161)     Initiative +15 (25)
Gargantuan Dragon (Chromatic), Lawful Evil
STR  DEX  CON  INT  WIS  CHA  Prof.
+8   +1   +7   +5   +3   +6   +7
Saves:  DEX +8, WIS +10
Speed 40 ft., Fly 80 ft., Swim 40 ft.

Skills Deception +13, Perception +17, Persuasion +13, Stealth +8
Immunities Poison; Poisoned
Senses Blindsight 60 ft., Darkvision 120 ft., Passive Perception 27
Languages Common, Draconic
Traits

Amphibious. The dragon can breathe air and water.
Legendary Resistance (4/Day). If the dragon fails a saving throw, it can choose to succeed instead.

Actions
Multiattack - The dragon makes three Rend attacks. It can replace one attack with a use of Spellcasting to cast Mind Spike (level 5 version).
Rend - Melee Attack Roll: +15, reach 15 ft. Hit: 17 (2d8 + 8) Slashing damage plus 10 (3d6) Poison damage
Poison Breath (Recharge 5–6) - Constitution Saving Throw: DC 22, each creature in a 90-foot Cone. Failure: 77 (22d6) Poison damage. Success: Half damage.

Spellcasting. The dragon casts one of the following spells, requiring no Material components and using Charisma as the spellcasting ability (spell save DC 21):
    At will: Detect Magic, Mind Spike (level 5 version)
    1/day each: Geas, Modify Memory

Legendary Actions (3/round)
Immediately after another creature's turn, The dragon can expend a use to take one of the following actions. The dragon regains all expended uses at the start of each of its turns.
Mind Invasion - The dragon uses Spellcasting to cast Mind Spike (level 5 version).
Noxious Miasma - Constitution Saving Throw: DC 21, each creature in a 30-foot-radius Sphere centered on a point the dragon can see within 90 feet. Failure: 17 (5d6) Poison damage, and the target takes a -2 penalty to AC until the end of its next turn. Failure or Success: The dragon can't take this action again until the start of its next turn.
Pounce - The dragon moves up to half its Speed, and it makes one Rend attack.
```
I fetched the stat block for the ancient green dragon off the web and then modified the formatting to accept the structure that I described earlier.
```
Commander Darkstar
AC 18        HP  68        Initiative +3
STR	DEX	CON	INT	WIS	CHA		Prof.
-3	 +3	+0	+5	 +2	 +5		 +5
Saves: STR -1, DEX +5, CON +2, INT +7, WIS +9, CHA +12
Speed 30ft.
Proficiencies: Arcana +10, Deception +10, Insight +7, Perception +7, Religion +10					
120 ft Darkvision (magical darkness too)        Passive Perception 17, Passive Investigation 15, Passive Insight 17
Resistances: Psychic damage		
Immunities: Magical Sleep

Traits
Charm Resistance - Darkstar has advantage on saves against being charmed.
Staff of Power - Darkstar wields a Staff of Power. This item has 20 charges and regains 2d8+4 daily at dawn. He can expend charges to cast spells. (He leaves his Staff of Charming at home).
Dark One’s Own Luck (5/day) - Five times per long rest, after seeing a save or ability check, but before seeing the outcome, add 1d10 to the result.
Hurl through Hell (1/day) - Once per long rest, when you hit on an attack roll, the target must make a DC 18 WIS save or be transported off the battlefield until the end of your next turn. A transported target takes 8d10 Psychic damage if it is not a Fiend.
Eldritch Strike - Darkstar can expend a spell slot to deal 6d8 extra force damage when he hits a creature with his pact weapon and knock it prone if it is Huge or smaller.
Spellcasting - Darkstar is a level 13 warlock, has spell save DC 18 and spell attack +12. Darkstar regains used spell slots on a short rest.
    At Will: eldritch blast, chill touch, mage hand, minor illusion, invisibility(self only, in shadow), levitate(self only), arcane eye
    5th level (3 slots): Banishment, Burning Hands, Command, Comprehend Languages, Contact Other Plane, Counterspell, Dimension Door, Dispel Magic, Dream, Fire Shield, Fireball, Fly, Geas, Hold Monster, Insect Plague, Mirror Image, Misty Step, Scorching Ray, Scrying, Stinking Cloud, Suggestion, Wall of Fire
    1/day each: Misty Step, Sending, Eyebite, Forcecage
    1 Charge: Magic Missile, Ray of Enfeeblement
    2 Charges: Flaming Sphere, Knock, Invisibility, Web
    3 Charges: Dispel Magic
    4 Charges: Ice Storm, Wall of Fire
    5 Charges: Wall of Force, Cone of Cold, Fireball (5th level), Lightning Bolt (5th level)
    6 Charges: Globe of Invulnerability

Actions
Multiattack - Darkstar may make 3 attacks or cast a spell.
Pact Blade - +10 to hit, 1d8+5 radiant (+10 dmg and +10 hp on a crit)
```
I invented the stat block for commander Darkstar and then wrote it in a format that is like the one I described earlier.
```
Greater Brightblade
AC 16 		HP 95           Initiative +3
Individual Humanoid (human wizard)	 		Speed 35ft
STR	DEX	CON	INT	WIS	CHA		Prof.
-1	 +3	+0	+5	 +1	 +0		 +5

Spellcasting - The Brightblade may cast the following wizard spells with spell attack modifier +10 and spell save DC 18.
At will: Detect Magic, Light, Mage Armor (included in AC), Mage Hand, Prestidigitation
4/day each: Shield
2/day each: Fireball, Ice Storm, Fog Cloud, Rime’s Binding Ice, Hunger of Hadar
1/day each: Cone of Cold, Move Earth, Arcane Eye


The Brightblade may make up to 3 attacks or use Spellcasting.
Mage Bolt - +10 to hit, Ranged Spell Attack range 90ft, 3d8+5 force damage

Counterspell (4/day) - the Brightblade casts counterspell in response to that spell’s trigger.
```
I invented the stat block for the greater brightblade and tweaked it minimally from what I have in my notes to work with the code.
```
Bandit
AC 12           HP 11 (2d8 + 2)          Initiative +1 (11)
Speed 30 ft.
        Str     Dex     Con     Int     Wis     Cha
SCORE   11      12      12      10      10      10
MOD     +0      +1      +1      +0      +0      +0
SAVE    +0      +1      +1      +0      +0      +0
Gear Leather Armor, Light Crossbow, Scimitar
Senses Passive Perception 10
Languages Common, Thieves' cant
CR 1/8 (XP 25; PB +2)

Actions
Scimitar. Melee Attack Roll: +3, reach 5 ft. Hit: 4 (1d6 + 1) Slashing damage.
Light Crossbow. Ranged Attack Roll: +3, range 80/320 ft. Hit: 5 (1d8 + 1) Piercing damage.
```
I copied the stat block for the bandit off the web, and then did a touch of formatting to make it possible for a human to understand, before I tweaked it minimally from what I have in my notes to work with the code.

## Writing Encounter Files
A .txt file of a particular format is used to store encounter information. When you download this program, there will be a folder called *Encounters*. Whatever you do ***do not change the name of this folder.*** This is where the .txt files for saved encounters live.  The files that encounters are saved in are possible to write by hand, but I highly discourage you from writing them. They are not prettily formatted. But if you really want to turn your encounter into a file by hand, I won't stop you. Here I'll describe what you need to do if you do need to write such a file.

Encounter file names are not restricted like monster file names, but I highly recommend using lowercase with underscores instead of spaces.

The first line of an encounter that has not been run is *"INACTIVE".* Encounters that are in progress list entities in the order of initiative *from the initiative that is the first that will happen when the encounter starts again* and just list the entities composing them.

Each entity in the encounter has a single line in the encounter file.

Monsters are represented by
```
MONSTER <<<SPACING MARKER>>> <file of origin> <<<SPACING MARKER>>> <current HP> <<<SPACING MARKER>>> <origin notes> <<<SPACING MARKER>>> <primary notes>
```
The line starts with 'MONSTER', then contains (in the given order), the name of the file the monster comes from (but not the rest of the path or the ending), the current health of the monster (a positive integer), any notes that would be associated with the monster at creation, and then all the notes that would have been added during the fight. Place <<<*SPACING MARKER*>>> between these elements (ignore the italics; they are here to make the angle brackets render properly). Similarly, units are represented by
```
UNIT <<<SPACING MARKER>>> <file of origin> <<<SPACING MARKER>>> <current HP> <<<SPACING MARKER>>> <size> <<<SPACING MARKER>>> <origin notes> <<<SPACING MARKER>>> <primary notes>
```
Similarly, the line starts with 'UNIT', then contains (in the given order), the name of the file the monsters composing the unit come from (but not the rest of the path or the ending), the current health of the unit (a positive integer), the current size of the unit (also a positive integer), any notes that would be associated with the unit at creation, and then all the notes that would have been added during the fight. Again, place <<<*SPACING MARKER*>>> between these elements. Placeholders and players are simpler. For a placeholder (add lair actions only if combat has started), write
```
PLACEHOLDER <<<SPACING MARKER>>> <initiative> <<<SPACING MARKER>>> <name>
```
The line starts with 'PLACEHOLDER', then has the initiative the placeholder appears at (an integer), and the name of the event which has the placeholder. Between these elements, write '<<<*SPACING MARKER*>>>'. For a player, write
```
PLAYER <<<SPACING MARKER>>> <name of character> <<<SPACING MARKER>>> <name of player>
```
The line starts with 'PLAYER', then has the name of the player character, followed by the name of the player themself. Between these elements, write '<<<*SPACING MARKER*>>>'.

For all four types of entities, if any of the elements of the line are not present, just leave nothing between the spacers. **Do not remove some of the placeholders just because there is nothing that is supposed to be between them.** All portions cannot contain tab, the new line character, or '<<<*SPACING MARKER*>>>'.

### Examples
```
INACTIVE
PLAYER <<<SPACING MARKER>>>Psyin<<<SPACING MARKER>>> Redacted
MONSTER <<<SPACING MARKER>>>en'ke'sheti<<<SPACING MARKER>>>122<<<SPACING MARKER>>><<<SPACING MARKER>>>
MONSTER <<<SPACING MARKER>>>high_magedeath<<<SPACING MARKER>>>210<<<SPACING MARKER>>><<<SPACING MARKER>>>
UNIT <<<SPACING MARKER>>>senior_magedeath<<<SPACING MARKER>>>372<<<SPACING MARKER>>>3<<<SPACING MARKER>>>  <<<SPACING MARKER>>>
```
This is a test encounter with one of my players (name redacted for privacy), an NPC (nonplayer character/character run by the dungeon master) called En'ke'sheti, a single high magedeath monster, and a unit composed of 3 senior magedeath monsters. This encounter has yet to be run.
```
MONSTER <<<SPACING MARKER>>> hesher <<<SPACING MARKER>>> 114 <<<SPACING MARKER>>><<<SPACING MARKER>>>     
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 210  <<<SPACING MARKER>>><<<SPACING MARKER>>>   is marker 2
MONSTER <<<SPACING MARKER>>> verisheet <<<SPACING MARKER>>>  74    <<<SPACING MARKER>>><<<SPACING MARKER>>>  
MONSTER <<<SPACING MARKER>>> commander_poltolsk <<<SPACING MARKER>>> 64    <<<SPACING MARKER>>><<<SPACING MARKER>>>  
PLAYER <<<SPACING MARKER>>> Moth  <<<SPACING MARKER>>>  Redacted 
MONSTER <<<SPACING MARKER>>> kilask <<<SPACING MARKER>>> 84  <<<SPACING MARKER>>><<<SPACING MARKER>>>      
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 144 <<<SPACING MARKER>>><<<SPACING MARKER>>>  has used antimagic field; is marker 7
MONSTER <<<SPACING MARKER>>> utala <<<SPACING MARKER>>>  83     <<<SPACING MARKER>>><<<SPACING MARKER>>>  
MONSTER <<<SPACING MARKER>>> subcommander_res'seth <<<SPACING MARKER>>>  78    <<<SPACING MARKER>>><<<SPACING MARKER>>>
PLAYER <<<SPACING MARKER>>> Psyin <<<SPACING MARKER>>>  Redacted 
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 163 <<<SPACING MARKER>>><<<SPACING MARKER>>>  is marker 11
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 210  <<<SPACING MARKER>>><<<SPACING MARKER>>>  under suggestion, is marker 12
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 196  <<<SPACING MARKER>>><<<SPACING MARKER>>> is marker 13
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 210 <<<SPACING MARKER>>><<<SPACING MARKER>>>  is marker 14
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 210 <<<SPACING MARKER>>><<<SPACING MARKER>>>  has used antimagic field, is marker 15
MONSTER <<<SPACING MARKER>>> jasilisk  <<<SPACING MARKER>>>  64 <<<SPACING MARKER>>><<<SPACING MARKER>>>      
UNIT <<<SPACING MARKER>>> senior_magedeath  <<<SPACING MARKER>>>  317 <<<SPACING MARKER>>>  3 <<<SPACING MARKER>>><<<SPACING MARKER>>>    
PLAYER <<<SPACING MARKER>>> Emia  <<<SPACING MARKER>>>  Redacted 
MONSTER <<<SPACING MARKER>>> levadra <<<SPACING MARKER>>> 68 <<<SPACING MARKER>>><<<SPACING MARKER>>>    
UNIT <<<SPACING MARKER>>> magedeath_fanatic  <<<SPACING MARKER>>> 4400  <<<SPACING MARKER>>>  100     <<<SPACING MARKER>>><<<SPACING MARKER>>>    
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 210 <<<SPACING MARKER>>><<<SPACING MARKER>>>  is marker 21
UNIT <<<SPACING MARKER>>> senior_student <<<SPACING MARKER>>> 309 <<<SPACING MARKER>>> 6  <<<SPACING MARKER>>><<<SPACING MARKER>>>    
MONSTER <<<SPACING MARKER>>> milnor <<<SPACING MARKER>>> 124  <<<SPACING MARKER>>><<<SPACING MARKER>>>    
MONSTER <<<SPACING MARKER>>> high_magedeath <<<SPACING MARKER>>> 203 <<<SPACING MARKER>>><<<SPACING MARKER>>>  has used antimagic, has marker 0
```
This is a large encounter my players are currently in (all names redacted for privacy). It is in progress, and in initiative order contains:
1. The NPC Hesher at 114 HP without notes.
2. A high magedeath at 210 HP with the active note that it has marker 2.
3. The NPC Verisheet at 74 HP without notes.
4. The NPC Commander Poltolsk at 64 HP without notes.
5. The player character Moth.
6. The NPC Kilask at 84 HP without notes.
7. A high magedeath at 144 HP with the active note that it has used the spell antimagic field and has marker 7.
8. The NPC Utala at 83 HP with no notes.
9. The NPC Subcommmander Res'seth at 78 HP with no notes.
10. The player character Psyin.
11. A high magedeath at 163 HP with the active note that it has marker 11.
12. A high magedeath at 210 HP with the active note that it is under the effect of a suggestion spell and has marker 12.
13. A high magedeath at 196 HP with the active note that it has marker 13.
14. A high magedeath at 210 HP with the active note that it has marker 14.
15. A high magedeath at 210 HP with the active note that it has used the spell antimagic field and has marker 15.
16. The NPC Jasilisk at 64 HP with no notes.
17. A unit of 3 senior magedeaths at 317 HP (total) without notes.
18. The player character Emia.
19. The NPC Levadra at 68 HP without notes.
20. A unit of 100 magedeath fanatics at 4400 HP (total) without notes.
21. A high magedeath at 210 HP with the active note that it has marker 21.
22. A unit of 6 senior students at 309 HP (total) without notes.
23. The NPC Milnor at 124 HP, without notes.
24. A high magedeath at 203 HP with the active note that it has used the spell antimagic field and has marker 0.

## Appendix: Unit Rules
The 'unit' entities that can be added through certain commands are specific to my campaign. I created their rules myself (this is called homebrewing). In my own campaign notes, here are the rules I wrote for them:

Units use standard stat blocks, however they take damage differently from others. Units behave like individuals on a turn basis, aside from how they interact with AOE spells, other individuals, and the presence of an additional element to track their size. Units take damage from AOE spells equal to up to their individual HP per square meter/yard of intersection. Individuals can enter the space of units. Units are based around size. This is a trait that changes as HP does, with max HP being (individual HP)*size and size being HP/(individual HP) rounding up. Unit attacks require targets to make a strange roll to take half damage. Rolls 5 or more greater than the DC result in taking no damage, even if the attack says it can be halved. This roll has a bonus equal to (AC of target)-10, incorporating all means of defense but allowing use of AOE save mechanics. Advantage on the attack is disadvantage on the save and visa versa. Damage is based around the amount of border the attacker and defender share.

All (or almost) of my unit stat blocks have the following trait as well:
```
Unit - Most values are given in terms of unit Size, which for the <name of unit> is <size range>. As HP decreases, Size decreases to HP/<individual HP>, rounded up. The unit occupies an area equal to Size square meters. It can cast and concentrate on Size spells at once and has Size reactions.
```