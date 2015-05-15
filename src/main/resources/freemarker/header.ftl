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

    $('#confirm-delete').on('show.bs.modal');

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
                maxFilesize: 4096, // MB
                parallelUploads: 16
            };
            $("dropzone").dropzone({ url: "/upload", maxFilesize: 4 });
        });
</script>
</#if>




<!-- TODO - DELETE BELOW -->

<#if title = "Seaddrch">
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

    .even-spaced {  margin-top: 2em; margin-bottom: 2em; }
    .bottom-spaced { margin-bottom:4em; }
    .spacer-right {margin-right:1em;}

    /* Sticky footer styles
    -------------------------------------------------- */
    html {
        position: relative;
        min-height: 100%;
    }

    .footer {
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    .footer .container {
        text-align:center;
        margin-top:4em;
    }


    /* expander css*/

    .clickable{
        cursor: pointer;
    }

    /* end expander css */

</style>
</head>