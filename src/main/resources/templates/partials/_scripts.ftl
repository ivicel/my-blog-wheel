<script src="<@spring.url '/static/js/jquery.min.'/>js"></script>
<script src="<@spring.url '/static/js/bootstrap.min.js'/>"></script>
<script src="<@spring.url '/static/js/animatescroll.min.'/>js"></script>
<script src="<@spring.url '/static/js/prism.js'/>"></script>
<script src="<@spring.url '/static/js/hux-blog.min.'/>js"></script>
<script src="<@spring.url '/static/js/snackbar.js'/>"></script>
<!--fastClick.js -->
<script src="//cdnjs.cloudflare.com/ajax/libs/fastclick/1.0.6/fastclick.min.js"></script>
<script type="text/x-mathjax-config">
    MathJax.Hub.Config({
        TeX: {
        equationNumbers: {
            autoNumber: "AMS"
        }
        },
        tex2jax: {
        inlineMath: [ ['$','$'] ],
        displayMath: [ ['$$','$$'] ],
        processEscapes: true,
        }
    });
</script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
</script>
<!-- Google Analytics -->
<#if BlogOptions.google_analytics??>
<script>
    let _gaId = "${BlogOptions.google_analytics}";
    let _gaDomain = "${BlogOptions.site_url}";

    // Originial
    (function(i, s, o, g, r, a, m) {
        i["GoogleAnalyticsObject"] = r;
        (i[r] =
            i[r] ||
            function() {
                (i[r].q = i[r].q || []).push(arguments);
            }),
            (i[r].l = 1 * new Date());
        (a = s.createElement(o)), (m = s.getElementsByTagName(o)[0]);
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m);
    })(
        window,
        document,
        "script",
        "//www.google-analytics.com/analytics.js",
        "ga"
    );

    ga("create", _gaId, _gaDomain);
    ga("send", "pageview");
</script>
</#if>