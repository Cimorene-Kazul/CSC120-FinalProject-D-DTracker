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
Our code will be based around an object called an EncounterBuilder. While 'builder' is often a word indicating a specific task in CS, in the D&D world, a tool designed to help a dungeon master create an encounter is almost always called an encounter builder, hence the use of the term here. This object will manipulate Encounter objects, which actually hold the information required to run encounters. To set up an encounter builder, run
```
EncounterBuilder myEncounterBuilder = new EncounterBuilder();
myEncounterBuilder.buildEncounter();
```
This is exactly what happens in our Main.java file, so you could also just run that.

### Encounter Builder Commands
Running the .buildEncounter() method starts up JEB - the Java Encounter Builder. JEB has a bunch of commands, which allow you to build an encounter object. When run, it will prompt you to ask for a command. The 'help' command will bring up the list.

**add monster** - This command adds a single entity defined by a stat block to the encounter. The stat block will be in the MonsterFiles folder, and must have a particular format. 

**add multiple monsters** - adds a given number of monsters to the encounter. Good for a fight with 5 goblins or 10 guards, to avoid repeats.
            add monster with note - does the same thing as add monster but gives the option to add a note to make it easier to keep track of things in large encounters.
            remove monster - removes a monster or unit from the encounter
            remove monsters - removes all monsters or units with the same name from the encounter.
            add player - adds a player-controled character to the encounter
            remove player - removes a player-controled character from the encounter
            add unit - adds an entity composed of some number of entites defined by a stat block to the encounter. These entities might have something special added because of their nature as a composite or might just take damage together to ease running large encounters.
            add unit with note - does the same thing as add unit but gives the option to add a note to make it easier to keep track of things in large encounters.
            print encounter - prints a list of creatures in the encounter, with basic information.
            list avaliable monsters - lists the monsters by name with stat blocks avaliable to use.
            save encounter - saves the encounter as an encounter file.
            list saved encounters - lists encounter file names that are saved.
            load encounter - loads an encounter from a saved file.
            clear encounter - removes all entities from the current encounter.
            help - prints this list of commands.
            close - quits this builder.

## Writing Monster Files
Hopefully, if this whole thing gets done, our code should also have the ability to read text or csv files containing a single stat block and convert them into a Monster object. The code should be able to pull from the stat block all the information it needs, with the stat block having a specific formatting that has not been decided yet. 

In addition to this, we can try and learn to scrap webpages in java to create such files out of a large, known, database of D&D monsters.
