<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic ErrorLog Analyser <small>Dashboard</small></h2>
        <#include "navigation.ftl">
    </div>

    <div class="well">

    <#if errorlogkeylist??>
        <#assign elkeys = errorlogkeylist?keys>
        <#list elkeys as elkey>
            <p>${elkey}</p>
        </#list>

    </#if>

        <p>Current file: ${errorlog.getName()}</p>
    </div>

    <div class="sixteen columns">
        <h3>Output:</h3>
        <textarea>
            <#list errorlog.getErrorLogTxt() as item>${item}</#list>
        </textarea>
    </div>




</div>
<#include "footer.ftl">
</body>
</html>