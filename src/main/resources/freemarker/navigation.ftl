<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav">
        <#if title = "Dashboard and Overview">
        <li class="active"><#else>
        <li></#if><a href="/">Dashboard</a></li>

        <#if title = "Upload ErrorLog">
        <li class="active"><#else>
        <li></#if><a href="/upload">Upload ErrorLog(s)</a></li>

        <li><a href="/report">Aggregate Report</a></li>

        <#if term??>
        <#if title = "Search">
            <li class="active"><#else>
            <li></#if><a href="/search/${term}">Search Results</a></li>
        </#if>
    </ul>

    <form class="navbar-form pull-right" role="search" action="/search" method="post">
        <div class="input-group">
            <input type="text" name="term" class="form-control" placeholder="e.g: 'Restarting'">
            <span class="input-group-btn">
                <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon-search"></span>
                </button>
            </span>
        </div>
    </form>

<#if errorlogs??>
<#if errorlogs?size gt 0>
    <div class="btn-group pull-right">
        <button type="button" class="btn btn-primary navbar-btn">Select ErrorLog</button>
        <button type="button" class="btn btn-primary dropdown-toggle navbar-btn" data-toggle="dropdown" aria-expanded="false">
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu" role="menu">
            <#assign elkeys = errorlogs?keys>
            <#list elkeys as elkey>
                <li><a href="/view/${elkey}">${elkey}</a></li>
            </#list>
        </ul>
    </div>
    <div class="btn-group pull-right spacer-right">
            <button type="button" class="btn btn-danger navbar-btn" data-href="/clear" data-toggle="modal" data-target="#confirm-delete">Clear</button>
    </div>
</#if>
</#if>
</div>
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
            </div>

            <div class="modal-body">
                <p>This will clear all currently parsed ErrorLog files</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="/clear" class="btn btn-danger btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>