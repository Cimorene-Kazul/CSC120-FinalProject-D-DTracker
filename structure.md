# Classes and Methods
## InitativeTracker
Constructor: no inputs OR ArrayList of Entity objects
### Attributes
creatures: ArrayList of Entity objects that are in the fight

initative: HashTable of int keys to ArrayList of Entity objects that are at that initative

### Methods
addCreature: adds an Entity to creatures

rollDice: takes a String formatted to indicate a dice roll and rolls the value

rollInitative: begins combat

## Entity
Constructor: takes in name

### Attributes
name: String

### Methods

## Player
*Subclass of Entity*

Constructor: takes in name

### Attributes
name: String

### Methods