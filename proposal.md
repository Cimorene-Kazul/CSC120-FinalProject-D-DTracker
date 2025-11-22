# Final Project Proposal
## Q1 Overview
*Briefly describe the big idea behind your project (i.e. what are you making / doing)? If you're choosing the default text-adventure game, briefly describe the premise of your game here.*

This project will create a tool for running combat in D&D. It will track order, health, and other stats, reducing the number of windows and switching a DM needs to work with. It should hopefully have a dice roller and an ability to save monsters as files and read such files built in. 

## Q2 Teammates
*If you're working with a team but having trouble getting Gradescope to let you add them to this submission, list all members of your team below:*

Eliana Lippa and Rachel Davison will work together on this project.

## Q3 Thinking Ahead
*What are the (major) building blocks your project will need to be successful? Indicate which components you know how to build already, and which ones you still need to figure out.*

The whole system will be based around an object called an InitiativeTracker that will track order and take commands. It will also need objects for monsters and players, as well as things like actions that they can take. The monsters, players, and actions should be easy. It's gonna be getting the initiative tracker to work that will be a pain, due to the difficulties of interpreting command line response. Our code will also have a file reader and writer, hopefully, to allow us to save stat blocks. If we get that far, adding in code to scrape stat blocks off the web will likely happen. If we get that far, adding a tool to build encounters using saved files would be really cool.

## Q4 Roadblocks
*What potential roadblocks do you imagine you might encounter? What's your plan for getting around them?*

We will need to figure out how to take commands effectively in the command line, to allow the DM to interact with the code. We will also have to figure out how to get limited-use elements to work properly, and the whole file-reading and web-page-scraping thing will be obnoxious. An encounter builder would also have similar command-line issues.

## Q5 Team Dynamics
*How does your team plan to divide up the work needed to complete this project?*

Eliana will clearly define what the code will need to do. She will also do most of the testing. In the process of defining the things the code needs to do, she will likely create object concepts and requirements. We will then split up the objects and methods we need to implement. If we get stuck, we might swap out what we are working on. One really nice thing about OOP is that it is easy to divide into pieces.