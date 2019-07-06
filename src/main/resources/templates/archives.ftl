<#import "/spring.ftl" as spring/>

<@override name="css">
<style type="text/css">
    header.intro-header{
        position: relative;
        background-image: url("<@spring.url '${BlogOptions.hux_archives_cover!BlogOptions.hux_home_cover}'/>");
        }

        header.intro-header .header-mask{
        width: 100%;
        height: 100%;
        position: absolute;
        background: rgba(0,0,0, 0.3);
    }
</style>
</@override>

<@override name="content">
<header class="intro-header" >
    <div class="header-mask"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                    <h1>Archives</h1>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
            <!-- Tags (as filter) -->
            <div id='tag_cloud' class="tags tags-sup js-tags">
            </div>

            <div id="placeholder"></div>
        </div>
    </div>
</div>
</@override>


<@override name="scripts">
<!-- jquery.tagcloud.js -->
<script src="<@spring.url '/static/js/jquery.tagcloud.js'/>"></script>
<script>
    $.fn.tagcloud.defaults = {
        //size: {start: 1, end: 1, unit: 'em'},
        color: {start: '#bbbbee', end: '#2f93b4'},
    };

    $.get('tag/all', function (msg) {
        $('#tag_cloud').html(msg);
    });

    $('#tag_cloud a').tagcloud();



    // $.get($('.tag-button--all').msg('encode'), function(msg) {
    //     $('#placeholder').html(msg);
    // });

</script>
</@override>

<@extends name="base.ftl"/>