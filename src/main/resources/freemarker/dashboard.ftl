<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic ErrorLog Analyser <small>Dashboard</small></h2>
        <#include "navigation.ftl">
    </div>

    <h3>Current file: <small>${errorlog.getName()}</small></h3>

    <div class="sixteen columns">


    <dl>
        <#assign el = errorlog.getOccurrenceMap()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <dt><a href="#">${elkey}</a></dt>
            <dd>${el[elkey]}</dd>
        </#list>
    </dl>





        <textarea id="errorlog">
<#list errorlog.getErrorLogHead() as item>${item}
</#list>
        </textarea>
    </div>

</div>
<#include "footer.ftl">
</body>
</html>