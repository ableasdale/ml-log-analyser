<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic ErrorLog Analyser <small>Dashboard</small></h2>
        <#include "navigation.ftl">
    </div>

    <h3>Current file: <small>${errorlog.getName()} (${errorlog.getErrorLogTxt()?size} line(s))</small></h3>
    <h3>Restarts and high level ErrorLog messages</h3>
    <div class="sixteen columns">
    <#assign el = errorlog.getOtherMessages()>
    <#assign elkeys = el?keys>
    <#list elkeys as elkey>
        <#assign itemlist = el[elkey]>
        <div class="form-group">
            <label for="${elkey}">${elkey}: (${itemlist?size} line[s])</label>
            <textarea class="form-control" rows="5" id="${elkey}"><#list itemlist as item>${item}
</#list></textarea>
        </div>
    </#list>
    </div>


    <h3>Occurrences of important Exception messages</h3>
    <div class="sixteen columns">
        <#assign el = errorlog.getOccurrenceMap()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <#assign itemlist = el[elkey]>
            <div class="form-group">
            <label for="${elkey}">${elkey}: (${itemlist?size} occurrence(s))</label>
            <textarea class="form-control" rows="5" id="${elkey}"><#list itemlist as item>${item}
</#list></textarea>
            </div>
        </#list>
    </div>

    <h3>ErrorLog below <small>(Up to ${lines} lines)</small></h3>
    <div class="sixteen columns">
        <div class="form-group">
            <textarea class="form-control" rows="5" id="errorlog">
<#list errorlog.getErrorLogHead() as item>${item}
</#list>
        </textarea>
        </div>
    </div>

</div>
<#include "footer.ftl">
</body>
</html>