<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="row">
        <h2>MarkLogic ErrorLog Analyser <small>Dashboard</small></h2>
        <#include "navigation.ftl">
        <#if errorlog??><h3>Current file: <small>${errorlog.getName()} (${errorlog.getErrorLogTxt()?size} line(s))</small></h3>
        <#else>
        <h3>No ErrorLog / Messages files are available <small>Use the upload form to start</small></h3>
        </#if>
    </div>

<#if errorlog??>
    <div class="row">
        <h3>Summary of occurrences:</h3>
        <dl class="dl-horizontal">
            <dt>Total restarts</dt>
            <dd>${errorlog.getTotalRestarts()}</dd>
        <#assign el = errorlog.getOtherMessages()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <dt><a href="/search/${elkey}" title="Search all Logs for ${elkey} messages">${elkey}</a></dt>
            <dd>${el[elkey]?size}</dd>
        </#list>

        <#assign el = errorlog.getOccurrenceMap()>
        <#assign elkeys = el?keys>
            <#list elkeys as elkey>
                <dt><a href="/search/${elkey}" title="Search all Logs for ${elkey} messages">${elkey}</a></dt>
                <dd>${el[elkey]?size}</dd>
            </#list>
        </dl>
    </div>

    <div class="panel panel-danger">
        <div class="panel-heading">
            <strong>Restarts, Audit Events and higher level ErrorLog messages</strong> <small>All <em>Warning</em> and <em>Critical</em> ErrorLog level messages and restarts</small>
            <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
        </div>
        <div class="panel-body">
        <#assign el = errorlog.getOtherMessages()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <#assign itemlist = el[elkey]>
            <div class="form-group">
                <label for="${elkey}"><a href="/search/${elkey}" title="Search all Logs for ${elkey} messages">${elkey}</a>: (${itemlist?size} line[s])</label>
                <textarea class="form-control" rows="5" id="${elkey}"><#list itemlist as item>${item}
        </#list></textarea>
            </div>
        </#list>

            <div class="form-group">
                <label for="restart-txt">Log entries surrounding MarkLogic restarts</label>
                <textarea class="form-control" rows="5" id="restart-txt"><#list errorlog.getErrorLogRestartTxt() as item>${item}
</#list></textarea>
            </div>

            <div class="form-group">
                <label for="restart-txt">Log entries surrounding MarkLogic fragment messages</label>
                <textarea class="form-control" rows="5" id="restart-txt"><#list errorlog.getFragmentMessages() as item>${item}
</#list></textarea>
            </div>

        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">
            <strong>Occurrences of important Exception messages</strong> <small>Known exception messages detected and thrown by the Server</small>
            <!-- TODO - date ranges for occurrences -->
            <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
        </div>
        <div class="panel-body">

        <#assign el = errorlog.getOccurrenceMap()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <#assign itemlist = el[elkey]>
            <div class="form-group">
                <label for="${elkey}"><a href="/search/${elkey}" title="Search all Logs for ${elkey} messages">${elkey}</a>: (${itemlist?size} occurrence(s))</label>
                <textarea class="form-control" rows="5" id="${elkey}"><#list itemlist as item>${item}
</#list></textarea>
            </div>
        </#list>


        </div>
    </div>

    <div class="panel panel-success">
        <div class="panel-heading">
            <strong>Trace Events</strong> <small>All user specified diagnostic trace events grouped by event type (id)</small>
            <span class="pull-right clickable"><i class="glyphicon glyphicon-chevron-up"></i></span>
        </div>
        <div class="panel-body">
        <#assign el = errorlog.getTraceEventMessages()>
        <#assign elkeys = el?keys>
        <#list elkeys as elkey>
            <#assign itemlist = el[elkey]>
            <div class="form-group">
                <label for="${elkey}"><a href="/search/${elkey}" title="Search all Logs for ${elkey} messages">${elkey}</a>: (${itemlist?size} line[s])</label>
                <textarea class="form-control" rows="5" id="${elkey}"><#list itemlist as item>${item}
</#list></textarea>
            </div>
        </#list>
        </div>
    </div>


    <h3>ErrorLog below <small>(Up to ${lines} lines)</small></h3>
    <div class="row bottom-spaced">
        <div class="form-group">
            <textarea class="form-control" rows="5" id="errorlog">
<#list errorlog.getErrorLogHead() as item>${item}
</#list>
        </textarea>
        </div>
    </div>
</#if>


</div>
<#include "footer.ftl">
</body>
</html>