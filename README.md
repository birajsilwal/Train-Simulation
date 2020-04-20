# SmartRail

## Chris Shelton & Biraj Silwal

### About
SmartRail is a simulation of light rail network built using JavaFx, JAVA concurrency, and JAVA thread.

### Problem Description

SmartRail  is  a  visionary  system  concept  that  allows  trains  to interact  directly  with  the tracks and switches that a part of a light rail network. 
Each train is an independent agent, which selects a destination (normally based on a schedule, policy, or conductor input),discovers the route to the destination, and secures the route against possible collisions (by repositioning  switches  and  setting  lights). 
 
Trains function independently of each other and cannot communicate with other trains.A train is aware of the track on which it is located and, similarly, the track is aware of the identity  of  the  train  present  at  that  location.   Two  trains  cannot  be  located  on  the  same track–a route consists of multiple tracks that connect with each other.

For modularity and flexibility reasons, each component of the light rail system is aware only of its immediate neighbors in the system.

### Simplifying Assumptions

* The static components are stations, switches, and track segments. (We’re leaving out signal lights.) The configuration is loaded on from a file using the format specified below.
* There are no rail crossings.
* Trains enter and exit the system at stations only.
* The conductor (that is, the user) selects the destination for a train.
* Trains change direction only when at a station.
* A train heading right/left can only travel to cities to the right/left.
* Trains move at constant speeds.

### Program GUI

* Program initially reads configuration file to set up simulation. 
* User selects destination station for a train (initial starting station is top left station).
* Display  should  have  track  segments,  stations,  switches,  and  trains  clearly  displayed and  updated  as  the  simulation  progresses
* When user selects a station, green color gets painted around selected station. 

### Implementation Details

* Each component (train, tracks, stations, etc.)  is an active object running on its own thread.
* Each  component can only talk to its immediate neighbors. (You  should not call a method on a neighboring object that calls a method on the next object that calls a method on the next object,  and so on.)  Pass some sort of message to the neighbor instead and let the neighbor handle the message on its own thread.
* Used concurrent data structures and synchronization to avoid deadlock and race conditions.  Suggestion:  give every object a BlockingQueue of messages as an “inbox” and just have everything for that object coordinated through processing the messages one at a time.

### Docs

* Object-Oriented Design file is inside the Docs folder
* Other resources like config files and images are inside Resources folder.

### Known Bugs

* Stationary elements (stations, switches,track) do not indicate if they are in a locked or free state.
* user cannot run two trains at the same time. 



