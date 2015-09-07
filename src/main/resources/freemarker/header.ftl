<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MarkLogic ErrorLog Analyser - ${title}</title>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" />

<#if title = "Dashboard and Overview">
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.6.0/codemirror.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.6.0/codemirror.min.css" />
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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/dropzone/4.0.1/min/dropzone.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/4.0.1/min/dropzone.min.js"></script>
<style type="text/css"><#include "../vendor/dropzone.css"></style>
<script>
        $(function () {
            Dropzone.options.dropzone = {
                maxFilesize: 4096, // MB
                parallelUploads: 16
            };
            $("dropzone").dropzone({ url: "/upload", maxFilesize: 4 });
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