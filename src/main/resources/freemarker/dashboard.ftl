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
        <textarea id="errorlog">
<#list errorlog.getErrorLogTxt() as item>${item}
</#list>
        </textarea>
    </div>

</div>
<#include "footer.ftl">
</body>
</html>