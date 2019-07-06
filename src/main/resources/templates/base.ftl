<#import "/spring.ftl" as spring/>

<#setting date_format="yyyy-MM-dd"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <#if BlogOptions.google_site_verfication??>
            <meta name="google-site-verification" content="${BlogOptions.google_site_verfication}"/>
        </#if>
        <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover"/>

        <title><@block name="title">base page</@block></title>

        <!-- Web App Manifest -->
        <link rel="manifest" href="<@spring.url '/static/pwa/manifest.json'/>" />

        <!-- Favicon -->
        <link rel="shortcut icon" href="<@spring.url '/static/images/favicon.ico'/>"/>
        <!-- Canonical URL -->
        <link rel="canonical" href="${BlogOptions.site_url!""}" />
        <link rel="stylesheet" href="<@spring.url '/static/css/bootstrap.min.css'/>" />

        <!-- Custom CSS -->
        <link rel="stylesheet" href="<@spring.url '/static/css/hux-blog.min.css'/>" />
        <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="<@spring.url '/static/css/prism.css'/>" />
        <link rel="stylesheet" href="<@spring.url '/static/css/ivicel.css'/>" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        
        <@block name="css"></@block>
    </head>

    <!-- hack iOS CSS :active style -->
    <body ontouchstart="">
        <#include 'partials/_nav.ftl'/>
        
        <@block name="content"/>
        
        <#include 'partials/_footer.ftl'/>

        <#include "partials/_scripts.ftl"/>
        <@block name="scripts"></@block>
    </body>
</html>
