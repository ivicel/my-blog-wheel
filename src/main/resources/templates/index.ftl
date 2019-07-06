<#import "/spring.ftl" as spring/>
<#import "partials/_post.ftl" as _post/>
<#import "partials/_pagination.ftl" as pager/>

<#-- todo: background image-->
<@override name="content">
<header class="intro-header" style="background-image: url('<@spring.url "/static/images/header.jpg"/>')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                    <h1>${BlogOptions.site_name}</h1>
                    <span class="subheading">${BlogOptions.site_description}</span>
                </div>
            </div>
        </div>
    </div>
</header>


<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-1 col-md-8 col-md-offset-1 col-sm-12 
            col-xs-12 postlist-container">
            <@_post.postFragment posts.content />
        </div>
        <!-- Sidebar Container -->
        <div class="col-lg-3 col-lg-offset-0 col-md-3 col-md-offset-0 col-sm-12 col-xs-12
            sidebar-container">
            <!-- Featured Tags -->
            <#include "partials/_featured_tags.ftl"/>

            <!-- Short About -->
            <#include "partials/_short_about.ftl"/>

            <!-- Friends Blog -->
            <#include "partials/_friends.ftl"/>
        </div>
    </div>
    <@pager.pagination posts/>
</div>
</@override>

<@extends name="base.ftl"/>