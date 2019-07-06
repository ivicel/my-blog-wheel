<#import "/spring.ftl" as spring/>

<@override name="css">
    <style type="text/css">
    header.intro-header{
        position: relative;
        /* todo: background of each post */
        background-image: url('<@spring.url "/static/images/header.jpg"/>');
    }

    header.intro-header .header-mask{
        width: 100%;
        height: 100%;
        position: absolute;
        background: rgba(0,0,0, 0.3);
    }
    /* place left on bigger screen */
    @media all and (min-width: 800px) {
        .anchorjs-link{
            position: absolute;
            left: -0.75em;
            font-size: 1.1em;
            margin-top : -0.1em;
        }
    }
</style>
</@override>

<@override name="content">
<!-- Post Header -->

<header class="intro-header" >
    <div class="header-mask"></div>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="post-heading">
                    <div class="tags">
                        <#list post.tags as tag>
                        <a class="tag" href="#" title="${tag.name}">${tag.name}</a>
                        </#list>
                    </div>
                    <h1>${post.title}</h1>
                    <#if post.subtitle??>
                    <h2 class="subheading">${post.subtitle}</h2>
                    </#if>
                    <p class="post-meta">
                        Posted by
                        <#if post.author??>
                            <#if post.author.nickName??>
                                ${post.author.nickName}
                            <#else>
                                ${post.author.username}
                            </#if>
                        </#if> on <#if post.createdDate??>${post.createdDate?string['yyyy-MM-dd']}</#if>
                    </p>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Post Content -->
<div class="container">
    <article>
        <div class="row">
            <!-- Post Container -->
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 post-container">
                ${post.bodyHtml}

                <hr style="visibility: hidden;">
                <#--<ul class="pager">-->
                    <#--{#% if page.previous.url %}-->
                    <#--<li class="previous">-->
                        <#--<a href="{{ page.previous.url | prepend: site.baseurl | replace: '//', '/' }}" msg-toggle="tooltip" msg-placement="top" title="{{page.previous.title}}">-->
                        <#--Previous<br>-->
                        <#--<span>{{page.previous.title}}</span>-->
                        <#--</a>-->
                    <#--</li>-->
                    <#--{% endif %}-->
                    <#--{% if page.next.url %}-->
                    <#--<li class="next">-->
                        <#--<a href="{{ page.next.url | prepend: site.baseurl | replace: '//', '/' }}" msg-toggle="tooltip" msg-placement="top" title="{{page.next.title}}">-->
                        <#--Next<br>-->
                        <#--<span>{{page.next.title}}</span>-->
                        <#--</a>-->
                    <#--</li>-->
                    <#--{% endif %#}-->
                <#--</ul>-->
                <hr style="visibility: hidden;">

                <#if BlogOptions.disqus_site_name??>
                <div class="comment">
                    <div id="disqus_thread" class="disqus-thread"></div>
                </div>
                </#if>
            </div>  
            <div class="col-lg-2 col-lg-offset-0 visible-lg-block sidebar-container catalog-container">
                <div class="side-catalog">
                    <hr class="hidden-sm hidden-xs">
                    <h5>
                        <a class="catalog-toggle" href="#">CATALOG</a>
                    </h5>
                    <ul class="catalog-body"></ul>
                </div>
            </div>
        </div>
    </article>
</div>
</@override>

<@override name="scripts">
<script src="//cdnjs.cloudflare.com/ajax/libs/anchor-js/1.1.1/anchor.min.js"></script>
<script src="<@spring.url '/static/js/jquery.nav.js'/>"></script>

<script type="text/javascript">
    anchors.options = {
        visible: 'always',
        placement: 'right',
        icon: '#'
    };
    anchors.add().remove('.intro-header h1').remove('.subheading').remove('.sidebar-container h5');
    function generateCatalog (selector) {
        var P = $('div.post-container'),a,n,t,l,i,c;
        a = P.find('h1,h2,h3,h4,h5,h6');
        a.each(function () {
            n = $(this).prop('tagName').toLowerCase();
            i = "#"+$(this).prop('id');
            t = $(this).text();
            c = $('<a href="'+i+'" rel="nofollow">'+t+'</a>');
            l = $('<li class="'+n+'_nav"></li>').append(c);
            $(selector).append(l);
        });
        return true;
    }

    generateCatalog(".catalog-body");

    // toggle side catalog
    $(".catalog-toggle").click((function(e){
        e.preventDefault();
        $('.side-catalog').toggleClass("fold")
    }));

    <#if BlogOptions.disqus_site_name??>
    var disqus_config = function () {
        this.page.url = '${BlogOption.site_url}/{{ article.url }}';
        this.page.identifier = '{{ article.title }}';
        this.page.title = '{{ article.title }}';
    };

    (function() {
        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
        dsq.src = '//${BlogOptions.disqus_site_name}.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
    })();
    
    </#if>
</script>
<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
</@override>

<@extends name="base.ftl"/>