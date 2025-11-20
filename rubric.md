# CSC120: Object-Oriented Programming
## Final Project Checklist

Listed below are various aspects of the assignment.  When you turn in your work, please indicate the status of each item

- **YES** : indicates that the item is fully complete
- **NO** : indicates that the item is not attempted
- **PART** : indicates that the item is attempted but not fully complete


## Basic Functionality (5 pts)

_____ 1 pts: Initative tracker successfully rolls initative for monsters randomly, takes input player initatives, and then goes through turns in decreasing initative order. On player turns, it prompts the player by name to take their turn. On monster turns, it prints the monster's stat block. Creatures are indexed in initative order.

_____ 0.5 pts: The command 'damage *index* *value*' damages the creature at index *index* by amount *value* and prints a status message.

_____ 0.5 pts: The command 'heal *index* *value*' heals the creature at index *index* by amount *value* (but it can only go up to maximum health) and prints a status message.

_____ 1 pts: The command 'end turn' successfully changes the current turn to the turn of the next character in initative.

_____ 1 pts: The command line successfully works as a dice roller and 4-function calculator, by writing 'roll *computation*'.

_____ 1 pts: The command 'summary' prints a list of creatures in initative order, with their Armor Classes, current health, and indicies


## Built-in Interactions (5 pts)

_____ 1 pts: The command '*type* save *index*' has the creature with index *index* roll a saving throw of type *type* if it has the ability to roll saves. *type* can be STR (strength), DEX (dexterity), CON (constitution), WIS (wisdom), INT (intelligence), or CHA (charisma).

_____ 2 pts: Interactions are implemented properly.  If a character has the ability to take a form of interaction, they can do the following:

     _____ .25 pts: On a creature's turn, the command 'action *name of action*' will print the text of and make any relevent dice rolls associated with the action *name of action*. Attacks roll both the attack roll and the damage roll. Results are printed in a readable manner (ie, "21 to hit, 12 damage on hit"). Creatures can only take 1 action per turn.

     _____ .25 pts: On a creature's turn, the command 'bonus action *name of bonus action*' will print the text of and make any relevent dice rolls associated with the bonus action *name of bonus action*. Attacks roll both the attack roll and the damage roll. Results are printed in a readable manner (ie, "21 to hit, 12 damage on hit"). Creatures can only take 1 bonus action per turn.

     ____ .5 pts: At any time, the command 'reaction *name of reaction* *index*' will print the text of and make any relevent dice rolls associated with the reaction *name of reaction* that is possessed by the creature with index *index*. Attacks roll both the attack roll and the damage roll. Results are printed in a readable manner (ie, "21 to hit, 12 damage on hit"). Creatures can only take 1 reaction per round unless specified otherwise.

     ____ .5 pts: At any time, the command 'legendary action *name of legendary action* *index*' will print the text of and make any relevent dice rolls associated with the legendary action *name of legendary action* that is possessed by the creature with index *index*. Attacks roll both the attack roll and the damage roll. Results are printed in a readable manner (ie, "21 to hit, 12 damage on hit"). Only some creatures have legendary actions.

     ____ .5 pts: At any time, the command 'legendary resistance *index*' will decriment the number of legendary resistances the monster with index *index* has remaining, or print a message if there are none left. Only some creatures have legendary resistances.

____ 0.5 pts: At initative count 20, if and only if there are monsters with lair actions availiable, a prompt with the monsters' names and stats is printed.

____ 0.5 pts: The command 'action summary' details availiable commands.

____ 0.5 pts: Abilities with limited uses are only useable up to those use limits before they print a message saying they are unavailiable.

____ 0.5 pts: Characters with and without interactions are compatible in the same initative tracker.

## Scraping and File Reading (5 pts)

_____ 1 pts: A clear readme documenting how to write a file that can be translated into a Monster object, along with how to use the encounter builder, scraping tool, and file reader, is included in the submission.

_____ 2 pts: A program is included to convert a specially-formated .txt file into a Monster object.
      
      _____ 1 pts: The program can create a basic monster.

      _____ 1 pts: The program can add the interaction components to the monster.

_____ 1 pts: A program is included to scrape 5e.tools and create the specially formated .txt files for the monsters involved.

_____ 1 pts: An encounter builder program is included that allows the user to use clearly-described text interactions to add monsters to an initative and then run the encounter.

      _____ 0.5 pts: The program can be used to utilize command-line alone to build an encounter.

      _____ 0.5 pts: The program is easy to use without needing to read documentation.

## Kudos: Location Tracking (5 pts)

*This section is not required. If we are permitted, we would appreciate it becoming extra credit. It is substantially beyond what we expect to accomplish, but would come in seriously handy. Otherwise, don't score this section.*

_____ 1 pts: A boolean is added to turn on use of coordinates. When on, a Turtle-like tool appears, with a dot tracking the location of each character.

_____ 1 pts: When coordinates are on, the code will prompt for each character to have a location relative to (0,0). They appear in the given locations on the location tracker. Characters are labeled with their index.

_____ 1 pts: On a turn, 'move *2D vector*' will move the character by adding the *2D vector* to their location. 'move *2D vector* *index*' will work with whatever character has index *index*. Character dots update to their new location. Distances are in feet.

_____ 0.5 pts: Characters can only use abilities within the given range, when coordinates are turned on.

_____ 0.5 pts: AOE abilities are drawn on the map for the duration of the turn where they are used.

_____ 0.5 pts: The map has a clear scale that can be read by users and the map is scaled to be appropriate for the distances involved.

_____ 0.5 pts: Creatures cover the convetntional area (5x5 for medium, 10x10 for large etc)

## Back-End Design (10 pts)

_____ 2 pts: Classes(s) are **effective, efficient** at supporting the desired operations and program behavior.

_____ 2 pts: Design justification includes a discussion of at least one (reasonable) **alternative design** that could have been used, and the reasons why you decided against this alternative.

_____ 2 pts: The project makes effective use of **Java built-in classes** whenever they are appropriate.

_____ 2 pts: The project's design is **extensible** (i.e. someone else could pick up where you left off, adding on or modifying the game without requiring a total rewrite).

_____ 2 pts: Submission includes an **architecture diagram** describing the relationships between all classes.


## General Items (12 pts):
_____ 4 pts: Program compiles without errors or warnings.

_____ 2 pts: Executes fully & consistently without crashing (exception/freeze).

_____ 2 pt: Complies with style guidelines (missing items 1 pt each):

      _____ Classes & class members all have Javadoc header comments.

      _____ Clear and consistent indentation of bracketed sections.

      _____ Adheres to Java conventions on naming & capitalization.

      _____ Methods & variables all have clear and accurate names.

      _____ Methods avoid confusing side effects.

_____ 2 pts: Clear documentation for use of all programs given is included in submission.

_____ 1 pt: All required files included with submission (including completed checklist file).

_____ 1 pt: `readme.md` contains your reflection on the project.