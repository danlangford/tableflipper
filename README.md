# tableflipper

a way for Dungeon Masters to collect, build, and roll on all of their favorite tables. the recursive template feature allows table results to mention dice rolls or other table results that all get resolved into one nice easy to read message. 

the `cli` sub module is a command line interface

the `ui` sub module is a JavaFX interface

currently it loads up tables from the 5e SRD. 

interesting Entry points (Main methods) to run:

    dan.langford.tableflipper.cli.TableFlipperCli
    dan.langford.tableflipper.ui.TableFlipperApp
    
you will need Maven 3.6+ and Java 11+

please obtain Java 11 from [AdoptOpenJDK](https://adoptopenjdk.net/) or [sdkman](https://sdkman.io/)