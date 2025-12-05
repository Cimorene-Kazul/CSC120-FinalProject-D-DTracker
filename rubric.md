# CSC120: Object-Oriented Programming
## Final Project Checklist

Listed below are various aspects of the assignment.  When you turn in your work, please indicate the status of each item

- **YES** : indicates that the item is fully complete
- **NO** : indicates that the item is not attempted
- **PART** : indicates that the item is attempted but not fully complete


## Basic Functionality (5 pts)

_____ 1 pts: Initiative tracker successfully rolls initiative for monsters randomly, takes input player initiatives, and then goes through turns in decreasing initiative order. On player turns, it prompts the player by name to take their turn. On monster turns, it prints the monster's stat block. Creatures are indexed in initiative order.

_____ 0.5 pts: The command 'damage *index* *value*' damages the creature at index *index* by amount *value* and prints a status message.

_____ 0.5 pts: The command 'heal *index* *value*' heals the creature at index *index* by amount *value* (but it can only go up to maximum health) and prints a status message.

_____ 1 pts: The command 'end turn' successfully changes the current turn to the turn of the next character in initiative.

_____ 1 pts: The command line successfully works as a dice roller that can also handle addition and multiplication, by writing 'roll *computation*'.

_____ 1 pts: The command 'summary' prints a list of creatures in initiative order, with their Armor Classes, current health, and indicies

## More Elaborate Functionality (5 pts)

_____ 1 pts: At initiative count 20, if and only if there are monsters with lair actions availiable, a prompt is printed with the monster's name

_____ 1 pts: A clear readme documenting how to write a file that can be translated into a Monster object, along with how to use the encounter builder, scraping tool, and file reader, is included in the submission.

_____ 1 pts: A program is included to convert a specially-formated .txt file into a Monster object.

_____ 2 pts: An encounter builder program is included that allows the user to use clearly-described text interactions to add monsters to an initiative and then run the encounter.

      _____ 1 pts: The program can be used to utilize command-line alone to build an encounter.

      _____ 1 pts: The program is easy to use without needing to read documentation.

## Back-End Design (10 pts)

_____ 2 pts: Classes(s) are **effective, efficient** at supporting the desired operations and program behavior.

_____ 2 pts: Design justification includes a discussion of at least one (reasonable) **alternative design** that could have been used, and the reasons why you decided against this alternative.

_____ 2 pts: The project makes effective use of **Java built-in classes** whenever they are appropriate.

_____ 2 pts: The project's design is **extensible** (i.e. someone else could pick up where you left off, adding on or modifying the game without requiring a total rewrite).

_____ 2 pts: Submission includes an **architecture diagram** describing the relationships between all classes.


## General Items (10 pts):
_____ 2 pts: Program compiles without errors or warnings.

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