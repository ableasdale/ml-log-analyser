<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="row">
        <h2>MarkLogic ErrorLog Analyser <small>Search</small></h2>
    <#include "navigation.ftl">
    </div>

    <div class="row">
        <h3>Search: <small>${term}</small></h3>

        <#assign resultset = searchResults?keys>
        <#list resultset as key>
            <#assign itemlist = searchResults[key]>
            <div class="form-group">
                <label for="${key}">${key}: (${itemlist?size} line[s])</label>
<textarea class="form-control" rows="5" id="${key}"><#list itemlist as item>${item}
</#list></textarea>
            </div>
        </#list>
    </div>


</div>
<#include "footer.ftl">
</body>
</html>