<#import "/spring.ftl" as spring/>

<div class="mini-post-list js-result">
<#list tag.posts as post>
    <#assign currentDate = ''/>
    <#assign first = true/>
    <section>
        <#if currentDate != post.createdDate?string("yyyy")>
        <#assign currentDate = post.createdDate?string("yyyy")/>

        <#assign first = false/>
        <a msg-sort="0036" msg-encode="<@spring.url '${post.url!}'/>" class="tag-button"
            title="${tag.name}" rel="1">
            <span class="fa listing-seperator">
                <span class="tag-text">${currentDate}</span>
            </span>
        </a>
        <#else>
            <#assign first = true/>
        </#if>>
        <div class="post-preview item" msg-tags="{{ tag }}">
            <a msg-sort="0036" msg-encode="{{ url }}" class="tag-button" title="{{ tag }}" rel="1"></a>
            <a href="<@spring.url '${post.url!}'/>">
                <h2 class="post-title">${post.title}</h2>
                <#if post.subtitle??>
                    <h3 class="post-subtitle">${post.subtitle}</h3>
                </#if>
            </a>
            <hr>
        </div>
    </section>
</#list>
</div>


