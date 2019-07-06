<#import "/spring.ftl" as spring/>

<section class="visible-md visible-lg">
    <hr>
    <h5><a href="<@spring.url '/about'/>">ABOUT ME</a></h5>
    <div class="short-about">
        <#--<#if (user.avatar)??>-->
    <img src="<@spring.url '/static/images/avatar.png'/>" />
        <#--</#if>-->

        <#if BlogOptions.sitebar_about??>
        <p>${BlogOptions.sitebar_about}</p>
        </#if>
        <!-- SNS Link -->
        <#include "_sns_links.ftl"/>
    </div>
</section>