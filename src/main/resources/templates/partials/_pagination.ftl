<#import "/spring.ftl" as spring/>


<!-- Pager -->
<#macro pagination page>
<ul class="pager">
    <#if page.hasPrevious()>
    <li class="previous">
        <#if page.number == 1>
            <a href="<@spring.url '/'/>">&larr; Newer Posts</a>
        <#else>
            <a href="<@spring.url '/?page=${page.number}'/>">&larr; Newer Posts</a>
        </#if>
    </li>
    </#if>

    <#if page.hasNext()>
    <li class="next">
        <a href="<@spring.url '/?page=${page.number + 2}'/>">Older Posts &rarr;</a>
    </li>
    </#if>
</ul>
</#macro>