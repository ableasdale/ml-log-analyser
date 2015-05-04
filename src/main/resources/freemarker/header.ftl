<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MarkLogic ErrorLog Analyser - ${title}</title>

<#if title = "Summary View" || title = "Follow Thread" || title="Search"  || title="Unique Threads">
<style type="text/css"><#include "../vendor/prism/prism.css"></style>
<script><#include "../vendor/prism/prism.js"></script>
</#if>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script><#include "../vendor/dropzone.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css" />
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

<script>
    $(function () {
        $("file-dropzone").dropzone({ url: "/upload" });
    });
</script>



<#if title = "Dashboard and Overview">

<script>
    $(function () {
        $("#overview").tablesorter();
    });
</script>
</#if>
<#if title = "Unique Threads">
<script>
    $(function () {
        $(".expandable").hide();
        $(".expander").click(function(e){
            $(this).next(".expandable").slideToggle("fast", "swing", function(e){
                if( $(this).is(":hidden") ) {
                    // TODO - h4.expander works but can't seem to target the inner span - (.expander .span).. why is this??
                    $(this).prev(".expander").html('Thread / Locations (Click to expand): [<span class="sign">+</span>]');
                } else {
                    $(this).prev(".expander").html('Thread / Locations (Click to expand): [<span class="sign">-</span>]');
                }
            });
        });
    });
</script>
</#if>
<#if title = "Search">
<script><#include "../vendor/chart.min.js"></script>
<script>
    function chart() {
        var ctx = $("#myChart").get(0).getContext("2d");
        var labels = [<#list stacks as stack>"${stack.getOptimisedName()}"<#if stack_has_next>,</#if></#list>];
        var values = [<#list stacks as stack><#assign i = stack.getThreadList()?size>${i}<#if stack_has_next>,</#if></#list>];
        var options = {
            <#if stacks?size &gt; 250>animation: false,</#if>
            scaleShowLabels: false
        }
        var data = {
            labels: labels,
            datasets: [
                {
                    fillColor: "rgba(151,187,205,0.5)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    data: values,

                    mouseover: function (data) {
                        var active_value = values[data.point.dataPointIndex];
                        var active_date = labels[data.point.dataPointIndex];
                        $("#tooltip").html(active_date+'<br/><span class="bigger">'+active_value+'</span>').css("position", "absolute").css("left", data.point.x-55).css("top", data.point.y-10).css("display", 'block');
                    },
                    // TODO - add a click handler at some stage to take you to the summary for that time - below doesn't seem to work!
                    // mousedown: function (data) {console.log("link..");},
                    mouseout: function (data) {
                        $("#tooltip").html("").css("display", "none");
                    }
                }
            ]
        }
        new Chart(ctx).Line(data, options);
        $("#chart p").hide();
    }
    $(function () {
        // TODO - highlight doesn't work nicely with prism - so it's been removed.
        // Maybe find a way to do this for keywords with prismjs at some stage?
        // $("code.language-cpp").highlight("<#--${searchterm}-->");
        // $(".highlight");
        $(".expandable").hide();
        $(".expander").click(function(e){
            $(this).next(".expandable").slideToggle("fast", "swing", function(e){
                if( $(this).is(":hidden") ) { 
                	// TODO - h4.expander works but can't seem to target the inner span - (.expander .span).. why is this??
                	$(this).prev(".expander").html('Thread / Locations (Click to expand): [<span class="sign">+</span>]');                	
                } else {
                	$(this).prev(".expander").html('Thread / Locations (Click to expand): [<span class="sign">-</span>]');
                }
            });
        });
        chart();
    });
</script>
</#if>

<#if title = "Summary View" || title = "Detail View">
<script>
    $(document).keyup(function (e) {
        var currentLocation = parseInt(window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.length));
        var uriBase = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, -1);
        if (e.keyCode === 37) {
            if (currentLocation != 0) {
                window.location.replace(uriBase + (currentLocation - 1));
            }
        }
        if (e.keyCode === 39) {
            if (currentLocation < ${total} -1) {
                window.location.replace(uriBase + (currentLocation + 1));
            }
        }
    });
</script>
</#if>

<style type="text/css">
    /*
     * The MIT License
     * Copyright (c) 2012 Matias Meno <m@tias.me>
     */
@-webkit-keyframes passing-through {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30%, 70% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); }
    100% {
        opacity: 0;
        -webkit-transform: translateY(-40px);
        -moz-transform: translateY(-40px);
        -ms-transform: translateY(-40px);
        -o-transform: translateY(-40px);
        transform: translateY(-40px); } }
@-moz-keyframes passing-through {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30%, 70% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); }
    100% {
        opacity: 0;
        -webkit-transform: translateY(-40px);
        -moz-transform: translateY(-40px);
        -ms-transform: translateY(-40px);
        -o-transform: translateY(-40px);
        transform: translateY(-40px); } }
@keyframes passing-through {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30%, 70% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); }
    100% {
        opacity: 0;
        -webkit-transform: translateY(-40px);
        -moz-transform: translateY(-40px);
        -ms-transform: translateY(-40px);
        -o-transform: translateY(-40px);
        transform: translateY(-40px); } }
@-webkit-keyframes slide-in {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); } }
@-moz-keyframes slide-in {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); } }
@keyframes slide-in {
    0% {
        opacity: 0;
        -webkit-transform: translateY(40px);
        -moz-transform: translateY(40px);
        -ms-transform: translateY(40px);
        -o-transform: translateY(40px);
        transform: translateY(40px); }
    30% {
        opacity: 1;
        -webkit-transform: translateY(0px);
        -moz-transform: translateY(0px);
        -ms-transform: translateY(0px);
        -o-transform: translateY(0px);
        transform: translateY(0px); } }
@-webkit-keyframes pulse {
    0% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); }
    10% {
        -webkit-transform: scale(1.1);
        -moz-transform: scale(1.1);
        -ms-transform: scale(1.1);
        -o-transform: scale(1.1);
        transform: scale(1.1); }
    20% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); } }
@-moz-keyframes pulse {
    0% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); }
    10% {
        -webkit-transform: scale(1.1);
        -moz-transform: scale(1.1);
        -ms-transform: scale(1.1);
        -o-transform: scale(1.1);
        transform: scale(1.1); }
    20% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); } }
@keyframes pulse {
    0% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); }
    10% {
        -webkit-transform: scale(1.1);
        -moz-transform: scale(1.1);
        -ms-transform: scale(1.1);
        -o-transform: scale(1.1);
        transform: scale(1.1); }
    20% {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1); } }
.dropzone, .dropzone * {
    box-sizing: border-box; }

.dropzone {
    min-height: 150px;
    border: 2px solid rgba(0, 0, 0, 0.3);
    background: white;
    padding: 20px 20px; }
.dropzone.dz-clickable {
    cursor: pointer; }
.dropzone.dz-clickable * {
    cursor: default; }
.dropzone.dz-clickable .dz-message, .dropzone.dz-clickable .dz-message * {
    cursor: pointer; }
.dropzone.dz-started .dz-message {
    display: none; }
.dropzone.dz-drag-hover {
    border-style: solid; }
.dropzone.dz-drag-hover .dz-message {
    opacity: 0.5; }
.dropzone .dz-message {
    text-align: center;
    margin: 2em 0; }
.dropzone .dz-preview {
    position: relative;
    display: inline-block;
    vertical-align: top;
    margin: 16px;
    min-height: 100px; }
.dropzone .dz-preview:hover {
    z-index: 1000; }
.dropzone .dz-preview:hover .dz-details {
    opacity: 1; }
.dropzone .dz-preview.dz-file-preview .dz-image {
    border-radius: 20px;
    background: #999;
    background: linear-gradient(to bottom, #eee, #ddd); }
.dropzone .dz-preview.dz-file-preview .dz-details {
    opacity: 1; }
.dropzone .dz-preview.dz-image-preview {
    background: white; }
.dropzone .dz-preview.dz-image-preview .dz-details {
    -webkit-transition: opacity 0.2s linear;
    -moz-transition: opacity 0.2s linear;
    -ms-transition: opacity 0.2s linear;
    -o-transition: opacity 0.2s linear;
    transition: opacity 0.2s linear; }
.dropzone .dz-preview .dz-remove {
    font-size: 14px;
    text-align: center;
    display: block;
    cursor: pointer;
    border: none; }
.dropzone .dz-preview .dz-remove:hover {
    text-decoration: underline; }
.dropzone .dz-preview:hover .dz-details {
    opacity: 1; }
.dropzone .dz-preview .dz-details {
    z-index: 20;
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    font-size: 13px;
    min-width: 100%;
    max-width: 100%;
    padding: 2em 1em;
    text-align: center;
    color: rgba(0, 0, 0, 0.9);
    line-height: 150%; }
.dropzone .dz-preview .dz-details .dz-size {
    margin-bottom: 1em;
    font-size: 16px; }
.dropzone .dz-preview .dz-details .dz-filename {
    white-space: nowrap; }
.dropzone .dz-preview .dz-details .dz-filename:hover span {
    border: 1px solid rgba(200, 200, 200, 0.8);
    background-color: rgba(255, 255, 255, 0.8); }
.dropzone .dz-preview .dz-details .dz-filename:not(:hover) {
    overflow: hidden;
    text-overflow: ellipsis; }
.dropzone .dz-preview .dz-details .dz-filename:not(:hover) span {
    border: 1px solid transparent; }
.dropzone .dz-preview .dz-details .dz-filename span, .dropzone .dz-preview .dz-details .dz-size span {
    background-color: rgba(255, 255, 255, 0.4);
    padding: 0 0.4em;
    border-radius: 3px; }
.dropzone .dz-preview:hover .dz-image img {
    -webkit-transform: scale(1.05, 1.05);
    -moz-transform: scale(1.05, 1.05);
    -ms-transform: scale(1.05, 1.05);
    -o-transform: scale(1.05, 1.05);
    transform: scale(1.05, 1.05);
    -webkit-filter: blur(8px);
    filter: blur(8px); }
.dropzone .dz-preview .dz-image {
    border-radius: 20px;
    overflow: hidden;
    width: 120px;
    height: 120px;
    position: relative;
    display: block;
    z-index: 10; }
.dropzone .dz-preview .dz-image img {
    display: block; }
.dropzone .dz-preview.dz-success .dz-success-mark {
    -webkit-animation: passing-through 3s cubic-bezier(0.77, 0, 0.175, 1);
    -moz-animation: passing-through 3s cubic-bezier(0.77, 0, 0.175, 1);
    -ms-animation: passing-through 3s cubic-bezier(0.77, 0, 0.175, 1);
    -o-animation: passing-through 3s cubic-bezier(0.77, 0, 0.175, 1);
    animation: passing-through 3s cubic-bezier(0.77, 0, 0.175, 1); }
.dropzone .dz-preview.dz-error .dz-error-mark {
    opacity: 1;
    -webkit-animation: slide-in 3s cubic-bezier(0.77, 0, 0.175, 1);
    -moz-animation: slide-in 3s cubic-bezier(0.77, 0, 0.175, 1);
    -ms-animation: slide-in 3s cubic-bezier(0.77, 0, 0.175, 1);
    -o-animation: slide-in 3s cubic-bezier(0.77, 0, 0.175, 1);
    animation: slide-in 3s cubic-bezier(0.77, 0, 0.175, 1); }
.dropzone .dz-preview .dz-success-mark, .dropzone .dz-preview .dz-error-mark {
    pointer-events: none;
    opacity: 0;
    z-index: 500;
    position: absolute;
    display: block;
    top: 50%;
    left: 50%;
    margin-left: -27px;
    margin-top: -27px; }
.dropzone .dz-preview .dz-success-mark svg, .dropzone .dz-preview .dz-error-mark svg {
    display: block;
    width: 54px;
    height: 54px; }
.dropzone .dz-preview.dz-processing .dz-progress {
    opacity: 1;
    -webkit-transition: all 0.2s linear;
    -moz-transition: all 0.2s linear;
    -ms-transition: all 0.2s linear;
    -o-transition: all 0.2s linear;
    transition: all 0.2s linear; }
.dropzone .dz-preview.dz-complete .dz-progress {
    opacity: 0;
    -webkit-transition: opacity 0.4s ease-in;
    -moz-transition: opacity 0.4s ease-in;
    -ms-transition: opacity 0.4s ease-in;
    -o-transition: opacity 0.4s ease-in;
    transition: opacity 0.4s ease-in; }
.dropzone .dz-preview:not(.dz-processing) .dz-progress {
    -webkit-animation: pulse 6s ease infinite;
    -moz-animation: pulse 6s ease infinite;
    -ms-animation: pulse 6s ease infinite;
    -o-animation: pulse 6s ease infinite;
    animation: pulse 6s ease infinite; }
.dropzone .dz-preview .dz-progress {
    opacity: 1;
    z-index: 1000;
    pointer-events: none;
    position: absolute;
    height: 16px;
    left: 50%;
    top: 50%;
    margin-top: -8px;
    width: 80px;
    margin-left: -40px;
    background: rgba(255, 255, 255, 0.9);
    -webkit-transform: scale(1);
    border-radius: 8px;
    overflow: hidden; }
.dropzone .dz-preview .dz-progress .dz-upload {
    background: #333;
    background: linear-gradient(to bottom, #666, #444);
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 0;
    -webkit-transition: width 300ms ease-in-out;
    -moz-transition: width 300ms ease-in-out;
    -ms-transition: width 300ms ease-in-out;
    -o-transition: width 300ms ease-in-out;
    transition: width 300ms ease-in-out; }
.dropzone .dz-preview.dz-error .dz-error-message {
    display: block; }
.dropzone .dz-preview.dz-error:hover .dz-error-message {
    opacity: 1;
    pointer-events: auto; }
.dropzone .dz-preview .dz-error-message {
    pointer-events: none;
    z-index: 1000;
    position: absolute;
    display: block;
    display: none;
    opacity: 0;
    -webkit-transition: opacity 0.3s ease;
    -moz-transition: opacity 0.3s ease;
    -ms-transition: opacity 0.3s ease;
    -o-transition: opacity 0.3s ease;
    transition: opacity 0.3s ease;
    border-radius: 8px;
    font-size: 13px;
    top: 130px;
    left: -10px;
    width: 140px;
    background: #be2626;
    background: linear-gradient(to bottom, #be2626, #a92222);
    padding: 0.5em 1.2em;
    color: white; }
.dropzone .dz-preview .dz-error-message:after {
    content: '';
    position: absolute;
    top: -6px;
    left: 64px;
    width: 0;
    height: 0;
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    border-bottom: 6px solid #be2626; }







    .content a {
        color: #800000;
        text-decoration: none;
    }

    .content a:hover {
        color: red;
        text-decoration: none;
        border-bottom: 1px dotted black;
    }

    .content a:visited {
        text-decoration: none;
        color: #2a5db0;
        border-bottom: 1px dotted black;
    }

    .content h2 a:hover, .content h3 a:hover, .content h4 a:hover {
        text-decoration: none;
    }

    .pure-menu-selected {
        border-bottom: 3px solid orange;
    }

    .term {
        color: #2a5db0;
    }

    .highlight {
        font-weight: bold;
        background: #ffc;
    }

        /* Colouring so we can see problems with a particular thread dump from the dashboard view */
    tr.error {
        color: red;
        border-left: 5px solid red;
    }

        /* Removing the links as the templates for the summaries won't render without data */
    tr.error td a {
        display: none;
    }


    .not-useful {
        border: 1px dashed red;
    }

    .not-useful h4 {
        color: #800000;
        margin-left: 2.5em;
    }

    .not-useful pre {
        background: #ffebeb;
    }

    .useful {
        border: 1px dashed green;
    }

    .useful h4 {
        color: #060;
        margin-left: 2.5em;
    }

    .useful pre {
        background: #e2ffe2;
    }

    #overview td {
        text-align: center;
    }

    .debug, .filename, .sign {
        color: #800000;
    }

    .grn {
        color: #060;
    }

    #exception {
        width: 500px;
        margin: auto;
        padding: 5em 8em;
        border: 30px dotted #800000;
        border-radius: 15em;
    }

    #chart {
        position: relative;
        height: 550px;
        width: 1100px;
        overflow-x:scroll;
    }

    #tooltip {
        opacity:0.7;
        font-weight:bold;
        text-align:center;
        display:none;
        width:8.5em;
        background:black;
        border-radius: 1em;
        color:white;
        padding:0.4em;
    }

    .bigger {font-size:150%;}

    pre.language-cpp {
        font-size:95%;
        overflow-x:scroll;
    }

    code.language-cpp {
        max-width: 3000px;
    }

    #myChart {
        position: absolute;
        top: 50px;
        /* display:none; */
    }

    .search-result {margin-bottom:2em; background:#eee; border:1px dotted #ddd;}
    .search-result h4 {margin-left:2em;}

	footer p {text-align:center;}

</style>
</head>