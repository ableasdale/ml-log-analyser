<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MarkLogic ErrorLog Analyser - ${title}</title>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css" />

<#if title = "Dashboard and Overview">
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.2.0/codemirror.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.2.0/codemirror.min.css" />
<script>
    // expanders
    $(document).on('click', '.panel-heading span.clickable', function(e){
        var $this = $(this);
        if(!$this.hasClass('panel-collapsed')) {
            $this.parents('.panel').find('.panel-body').slideUp();
            $this.addClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        } else {
            $this.parents('.panel').find('.panel-body').slideDown();
            $this.removeClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
        }
    });
    $(function () {
        CodeMirror.fromTextArea(errorlog, {
            lineNumbers: true,
            enableSearchTools: true,
            readOnly: true,
            /* height: '400px', doesn't do anything useful */
            styleActiveLine: true
        });
    });
</script>
</#if>

<#if title = "Upload ErrorLog">
<style type="text/css"><#include "../vendor/dropzone.css"></style>
<script><#include "../vendor/dropzone.min.js"></script>
<script>
        $(function () {
            // "myAwesomeDropzone" is the camelized version of the HTML element's ID
            Dropzone.options.dropzone = {
                maxFilesize: 4096 // MB
            };
            $("dropzone").dropzone({ url: "/upload", maxFilesize: 4 });
        });
</script>
</#if>




<!-- TODO - DELETE BELOW -->

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

    /* expander css*/

    .clickable{
        cursor: pointer;
    }

    /* end expander css */


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