# How to utilize the D&D combat tracker and file manipulation tools.
## Building Encounters
## Writing Monster Files
A .txt file of a particular format can be used to store monster information. When you download this program, there will be a folder called *MonsterFlies*. Whatever you do ***do not change the name of this folder.*** In addition to passing the information piece-by-piece, you can create a Monster by writing
```
Monster myMonster = Monster("file_name");
```
When creating these files, choose file_name to be the name of the monster, using no capitalization and underscores instead of spaces. For instance, "bandit_captain.txt" is a good file name, while "myMonster.txt" is much worse.

This file must have the following format. Whitespace is whatever is convienient. Not all the features neet to be present. However, the first two lines *must* have the given format. Feel free to do different things after, but those lines are required.
```
<Name>
AC <AC>     HP <Max HP>   Initative <Initative Bonus>
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

Here is an example:
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