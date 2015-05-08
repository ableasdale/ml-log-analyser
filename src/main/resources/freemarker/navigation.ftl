<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav">
        <#if title = "Dashboard and Overview">
        <li class="active"><#else>
        <li></#if><a href="/">Dashboard</a></li>

        <#if title = "Upload ErrorLog">
        <li class="active"><#else>
        <li></#if><a href="/upload">Upload ErrorLog(s)</a></li>

            <!--
        <#if title = "PStack Collections">
        <li class="active"><#else>
        <li></#if><a href="/collections">Collections</a></li>
        <#if title = "Unique Threads">
        <li class="active"><#else>
        <li></#if><a href="/unique">Unique Threads</a></li>
        <#if threadid??>
            <#if title = "Follow Thread">
            <li class="active"><#else>
            <li></#if><a href="/follow/${threadid}">Follow Thread</a></li>
        </#if>
        <#if id??>
            <#if title = "Detail View">
            <li class="active"><#else>
            <li></#if><a href="/view/${id}">Detail</a></li>
            <#if title = "Summary View">
            <li class="active"><#else>
            <li></#if><a href="/summary/${id}">Summary</a></li>
        </#if>
        <#if searchterm??>
            <#if title = "Search">
            <li class="active"><#else>
            <li></#if><a href="/search/${searchterm}">Search</a></li>
        </#if> -->
    </ul>

    <form class="navbar-form pull-right" role="search" action="/search" method="post">
        <div class="input-group">
            <input type="text" name="term" class="form-control" placeholder="e.g: 'xdmp::BalancedPositionTree::put">
            <span class="input-group-btn">
                <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon-search"></span>
                </button>
            </span>
        </div>
    </form>

    <div class="btn-group pull-right">
        <button type="button" class="btn btn-primary navbar-btn">Select ErrorLog</button>
        <button type="button" class="btn btn-primary dropdown-toggle navbar-btn" data-toggle="dropdown" aria-expanded="false">
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu" role="menu">
        <#if errorlogs??>
            <#assign elkeys = errorlogs?keys>
            <#list elkeys as elkey>
                <li><a href="/view/${elkey}">${elkey}</a></li>
            </#list>
        </#if>
        </ul>
    </div>

</div>
