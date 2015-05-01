<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic ErrorLog Analyser <small>Upload</small></h2>
    <#include "navigation.ftl">
    </div>

    <!-- h1>MarkLogic PStack Tools : Upload PStack Movie</h1>
    <h2>Choose a PStack Movie file to process:</h2 -->


    <div class="panel panel-default">
        <div class="panel-heading"><strong>Upload Files</strong> <small>Bootstrap files upload</small></div>
        <div class="panel-body">

            <!-- Standar Form -->
            <h4>Select files from your computer</h4>
            <form action="/upload" method="post" enctype="multipart/form-data" id="js-upload-form">
                <div class="form-inline">
                    <div class="form-group">
                        <input type="file" name="files[]" id="js-upload-files" multiple>
                    </div>
                    <button type="submit" class="btn btn-sm btn-primary" id="js-upload-submit">Upload files</button>
                </div>
            </form>

            <!-- Drop Zone -->
            <h4>Or drag and drop files below</h4>
            <div class="upload-drop-zone" id="drop-zone">
                Just drag and drop files here
            </div>

            <!-- Progress Bar -->
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                    <span class="sr-only">60% Complete</span>
                </div>
            </div>

            <!-- UploadResource Finished -->
            <div class="js-upload-finished">
                <h3>Processed files</h3>
                <div class="list-group">
                    <a href="#" class="list-group-item list-group-item-success"><span class="badge alert-success pull-right">Success</span>image-01.jpg</a>
                    <a href="#" class="list-group-item list-group-item-success"><span class="badge alert-success pull-right">Success</span>image-02.jpg</a>
                </div>
            </div>
        </div>
    </div>

</div>
<#include "footer.ftl">
</body>
</html>



