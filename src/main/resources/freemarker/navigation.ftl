<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav">
        <#if title = "Dashboard and Overview">
        <li class="active"><#else>
        <li></#if><a href="/">Dashboard</a></li>

        <!--
        <#if title = "Upload PStack file">
        <li class="active"><#else>
        <li></#if><a href="/upload">Upload</a></li>
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

    <form class="navbar-form pull-right" action="/thread" method="post">
        <div class="input-group">
            <input type="text" name="id" class="form-control" placeholder="Follow Thread (by id)"">
            <span class="input-group-btn">
                <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon glyphicon-road"></span>
                </button>
            </span>
        </div>
    </form>
</div>
