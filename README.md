# CSC 120 Final Project: D&D Combat Tracker (Primarily for DMs)

D&D combat is a lot to keep track of. DMing a large encounter can be a major challenge. This project intends to make a tracker to help. D&D is based around making rolls and checking against difficulties. Tests are made by rolling 20-sided dice (d20s for short) and adding modifiers. The difficulties in question are called DCs. Tests succeed if they equal or exceed the given DC.

Here is a simple stat block, holding the information needed to run combat against a basic enemy, a bandit.

***Bandit***

Small or Medium Humanoid, Neutral

*AC* 12         *Initiative* +1 (11)

*HP* 11 (2d8 + 2)       *Speed* 30 ft.

        *Str     Dex     Con     Int     Wis     Cha*

        11       12      12      10      10      10

Bonus:  +0       +1      +1      +0      +0      +0

Save:   +0       +1      +1      +0      +0      +0

*Gear* Leather Armor, Light Crossbow, Scimitar      *Senses* Passive Perception 10

*Languages* Common, Thieves' cant                   *CR* 1/8 (XP 25; PB +2)

***Actions***

Scimitar. Melee Attack Roll: +3, reach 5 ft. Hit: 4 (1d6 + 1) Slashing damage.

Light Crossbow. Ranged Attack Roll: +3, range 80/320 ft. Hit: 5 (1d8 + 1) Piercing damage.

D&D uses turn-based combat. The most important pieces of information here are the AC, initiative, and HP of the monster. The rest we will consider modeling in time, but we'll start with these three. The initiative determines order in combat. Each creature fighting rolls a 20-sided die (1d20) and adds a modifier, their initiative modifier. You then go in initiative order, where the 'initiative' is the modified roll. The AC is the difficulty of hitting the target with an attack. To strike a target, a character rolls a 20-sided die (1d20) and adds a modifier. If that value equals or exceeds the target's AC, they got hit. After hitting a target, remove HP equal to the damage dealt, which might be modified by defensive ablities. If HP falls to 0, the target is dead or dying. 

# Part 1: The Basics
Here is the most basic Psudocode for our project:
**Input**: A list of 'monsters' each of which has a stat block, AC, inititive, and HP, and a list of 'players' each of which just has a name.
1. Roll initiative for each monster (1d20+initiative) and save.
2. Prompt for initiative rolls from each player, and save.
3. Sort all (players and monsters together) by initative
4. Create an 'initive count' tracker. Start at 20 OR the largest initative rolled.
5. If there is a creature at the current initative:
    a. If the creature is a player: Prompt the player to take their turn
    b. If the creature is a monster: Print the stat block
    c. Prompt for input
        i.   If input is 'end', go to 6.
        ii.  If input is 'attack' print a numbered list of creatures with their ACs and ask which got attacked. Then ask if they got hit. If they did, prompt for damage and remove that damage from the target's HP. Otherwise, return to c.
        iii. If the input is anything else, print 'unclear' and tell the user the availiable options
6. Decrease initiative by 1.
7. If initiative is less than the smallest initiative rolled: Return to 4.

The basic object here is going to be called an InitiativeTracker. This object will take a list of monsters and a list of charac