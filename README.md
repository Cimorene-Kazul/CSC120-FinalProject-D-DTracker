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

## Part 0: Using Our Code
Our code will be based around an object called an InitiativeTracker. Our code will be set up so that running
```
InitiativeTracker myEncounter = new InitiativeTracker(<ArrayList of Monsters and Players>);
myEncounter.rollInitiative();
```
will create an encounter with a list of Monsters and Players, then run combat. Another option will be
```
InitiativeTracker myEncounter = new InitiativeTracker();
myEncounter.add(Player myPlayer1);
myEncounter.add(Player myPlayer2);
myEncounter.add(Player myPlayer3);
...
myEncounter.add(Player myMonstern);
myEncounter.rollInitiative();
```
to do the same thing.

## Part 1: The Basics
The most fundamental thing our code will do when run is roll initiative for all the monsters. It will then prompt each player for their rolled initiative value. After this, it will go through the iniative count until it is told to stop. On each initiative, if it is someone's turn, it will either prompt the player to take their turn or print the monster's stat block depending on the situation.

During a turn, our code will wait for the following commands 'end', 'next', 'damage', and 'summary'. 'next' moves to the next creature's turn. 'damage' prompts for a creature, which will take the index of the creature to damage. It should possibly also print the summary. 'summary' will print all the creatures, with their index, then name, and if they are a monster, AC and current HP. 'end' will quit out of the loop, which is going to need to be a while loop.

In addition, it might also be useful for monsters to have a 'lair' boolean, and if there is a 'lair' monster, print a message to do all the lair actions on initiative 20. Similarly, adding a 'heal' command that functions like 'damage' but does the opposite could come in handy.

In addition to however we track the initiative count a player has, player entities should store their names. Similarly, monsters should store, along with their names, a long string containing their stat block, plus their initiative modifier, AC, current HP, and maximum HP. Both should have a boolean indicating if they are player-controlled.

If a command composed of a sum of values that look like ndm and integers is input, it will roll and add the values. mdn means roll m n-sided dice.
We might also want to add the ability to give monsters set initiative, instead of rolling. This will help for certain major encounters.

The intended classes here are: InitiativeTracker, Player, Monster, Entity. Both Player and Monster are subclasses of Entity.

## Part 2: Saves + Interactions
This portion will add the ability for some monsters to have saves and various types of interactions. 

They will be able to roll to save on command, by saying 'save' followed by the type of save and then a list of monster numbers. It will then print the save values, in increasing order of the monsters given.

In addition, Actions and Bonus actions will be formalized. They will be able to take a text value for the text, and some will also have an AttackRoll associated with it that it can roll. On a monster's turn, the command 'action' will print a list of actions and then take the index of the chosen action. It will print the action text, and any associated attack rolls. It will then mark them as having taken their action, which will be reset at the end of their turn. The same thing will happen for bonus actions.

The intended classes here are: d20Test, Interaction, the subclasses of Interaction, Action and BonusAction, and the subclasses of d20Test Save, AttackRoll.

## Part 3: Off-turn Interactions
This portion will impliment reactions and legendary actions, as well as more detail on lair actions.

The intended classes here are: the subclasses of Interaction, LairAction, Reaction and LegendaryAction

## Part 4: Consumables
Many monster stat blocks have n/day or spell slot avaliability. While n/day is obvious and means that there are n of these availiable per encounter (usually), I will explain spell slots here. Each spell of a particular level can be cast by using a slot at or above that level. The slot is then expended.

This portion will add to all the Interaction classes the ability to add limited uses.

## Kudos: Reading Files + Scraping
Hopefully, if this whole thing gets done, our code should also have the ability to read text or csv files containing a single stat block and convert them into a Monster object. The code should be able to pull from the stat block all the information it needs, with the stat block having a specific formatting that has not been decided yet. 

In addition to this, we can try and learn to scrap webpages in java to create such files out of a large, known, database of D&D monsters.
