## MarkLogic ErrorLog Analyser

Allows drag and drop of multiple ErrorLog files to aid in the analysis of a large number of events (over multiple nodes) very quickly

### To build and run

1. `mvn clean install`
2. `mvn exec:java -Dexec.mainClass="com.marklogic.analyser.Server"`

Then go to http://localhost:9997

### Configuration

Edit the Consts values in:
`src/main/java/com/marklogic/analyser/util/Consts.java`