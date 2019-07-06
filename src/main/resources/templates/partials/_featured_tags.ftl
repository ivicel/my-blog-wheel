<#import "/spring.ftl" as spring/>

<section>
    <hr class="hidden-sm hidden-xs">

    <h5><a href="<@spring.url '/archive/'/>">FEATURED TAGS</a></h5>
    <div class="tags">
        <#if tags??>
            <#list tags as tag>
                <a href="<@spring.url '/tag/${tag.name}'/>"
                   title="${tag.name}" rel="${tag.name}">${tag.name}</a>
            </#list>
        </#if>
    </div>
</section>