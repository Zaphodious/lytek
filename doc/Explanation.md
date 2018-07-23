# Anathema's Reason for Being

## The duality of Exalted 3rd Edition

Exalted 3rd Edition presents the application programmer with an interesting challenge. It was designed for human use, and so is interconnected and interdependent with itself in ways that humans find natural but which requires great care by machines to properly understand. Subsystems are easily disregarded in their entirity by players who do not personally interact with them, but application developers must understand all of them in their entirity if their app is to function the way that players expect. The data that represents a game character appears simple to a player, and is quickly teachable, but requires careful consideration from a developer due to the structural effects of each of these "minor" exceptions.

In short, the game is not friendly to application logic, for the same reasons that its wonderful for humans to use.

## The Neccessity and Tragidy of Tools

The lack of good digital tooling for Exalted is a shame. While the game can be enjoyed just fine with nothing more then the book and some paper, there *are* aspects that can be drasically improved by software. Character creation, while simple, is still time consuming for initiate players, and even advanced players and Storytellers would benefit from simple tooling. Visualization of the myrade connections between game elements requires tedious manual labor. Editable PDFs are a poor substitute for right and proper character managers like those that exist for other games. Sophisticaed tooling would enable groups to engage in grander and more epic combat. A database of custom content would enable sharing of ideas without relying on volitile message boards to store information.

Some digital tooling has been made available by volunteer effort, but the complexity inherent in Exalted 3rd Edition gives the hobbyist a great amount of unwelcome work. Unfortunately, this has resulted in the quick death of many otherwise great tools.

## An Elegant Solution

The Anathema Reincarnated Project, through the various Lytek libraries and The Loom of Fate REST API, aims to solve the root of the problem. 

The project provides
+ a clear and consistent data model for representing Exlated 3rd Edition game concepts like Characters, Charms, etc
+ a simple API for manipulating the data
+ an open, langauge and system independent service for storing, retrieving, and validating the data

## The Data Model

The heart and soul of Anathema Reincarnated, defined by the Clojure(Script) implementation of Lytek, is the data model. The two principle goals of the model are to make it possible to work with Exalted data programatically without overly complecting application logic, and to provide a unified format for exchanging Exalted data.

Each game entity (character, rule, etc) is defined as a flat map (json object) of properties, including name and description. For more information, please see src/lytek/spec.cljc.

## The Simple API

As previous discussed, Exalted is a very complex domain. A general-purpose API for working with Exalted data should therefore be as simple as possible, and be available in as many languages as possible, to prevent adding more cognative load/developer stress and thus more dead projects. Simplicity comes at a cost, however, and this project is no exception.

The Anathema Reincarnated API is a simple REST API, using simple password authentication for user state managment, and adhering to a simple CRUD model for dealing with data. The tradeoff here is obvious- one must have internet access to use it. This is an acceptable price, considering the amount of flexability and choice this gives developers who use the system.

## The Loom of Fate

In addition to offering an API for manipulating Exalted 3rd Edition data, the Anathema Reincarnated project makes available a registry for storing user's game data. User registion is simple, handled by generating passwords for new users via email. Any application that uses the service simply needs to acquire the password in order to manage their owned game data. Users are able to use new apps as they appear without worrying about binding their data to the service, and developers can make new apps without solving the same data-related issues every single time.