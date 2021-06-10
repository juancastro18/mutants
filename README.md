##Mutants
This application calculate if a person is mutant sending its adn, also get the stats of this process.

###Services

1. Calculate if a person is a mutant.
URL : POST::localhost:8080/mutant
Body: "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]

2. get the stats.
URL : GET::localhost:8080/stats

##How to run it

You can run it from eclipse IDE or from the command line. You can search google for menual on each step. They are generic as posiable.

Download the project using git.
If your using an IDE you need to import the project to your IDE. For example in IntelliJ: File -> New -> Project From Existing source -> gradle.
Download dependencies using gradle. Run gradle build.
Compile the java code.
Run the java application.
Open a browser and go to url: http://localhost:8080/stats
If the server is working you should get a page with the stats.
