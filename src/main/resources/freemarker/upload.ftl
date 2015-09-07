<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic ErrorLog Analyser <small>Upload</small></h2>
    <#include "navigation.ftl">
    </div>

    <div class="panel panel-default">
        <div class="panel-heading"><strong>Upload Files</strong> <small>Drag ErrorLog files into box below</small></div>
        <div class="panel-body">

            <h4>Select files from your computer</h4>
            <form enctype="multipart/form-data" action="/upload" method="post" class="dropzone" id="dropzone"></form>

        </div>
    </div>

    <a href="/">
    <button type="button" class="btn btn-success" aria-label="Left Align">
        <span class="glyphicon glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;Back to Dashboard
    </button></a>

</div>
<#include "footer.ftl">
</body>
</html>



