## MarkLogic ErrorLog Analyser - v0.2a
[![Build Status](https://travis-ci.org/ableasdale/ml-log-analyser.svg?branch=master)](https://travis-ci.org/ableasdale/ml-log-analyser)
[![Dependency Status](https://www.versioneye.com/user/projects/56d70caad71695003886c31e/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56d70caad71695003886c31e)

Allows a MarkLogic team to monitor and summarise multiple ErrorLog files to aid in the analysis of a large number of events (over multiple nodes) very quickly.  

The application will summarise restarts, all known exceptions thrown, trace events and Warning and Critical level error messages.

* Allows search across multiple log files for key terms
* Supports individual ErrorLog files as large as 4GB in size
* Provides a summary of all restarts found and will extract lines before and after the restarts for easy viewing
* Allows for Drag and Drop upload for multiple ErrorLog files
* Generates a report as text and aggregates exception messages across all uploaded ErrorLog files
* Also reports on and summarises all occurrences of diagnostic trace events also in the ErrorLog

### To build, test and run:

```bash
./gradlew run
```

Then go to [http://localhost:9977](http://localhost:9977)

### To run "permanently" as a background task on a given host

```bash
nohup ./gradlew run &
```

### Configuration

Edit the Consts values in:
`src/main/java/com/marklogic/analyser/util/Consts.java`

### Screenshot
![Alt text](/src/main/resources/images/screenshot.png?raw=true "MarkLogic ErrorLog Analyser")
