# How to utilize the D&D combat tracker and file manipulation tools.
## Building Encounters

An *InitativeTracker* object represents a single encounter. It contains and tracks all of the creatures relevent to the encounter. These creatures are either *Monsters*, DM-controlled entities with stats tracked, or *Players*, entities that are not controled by the user of the software, and instead simply prompt for turn. You have two possibilities for how to set one up.

```
Creature[] creatures = {<place Creatures here separated by commas>};
InitativeTracker myInitativeTracker = InitativeTrac
```

##