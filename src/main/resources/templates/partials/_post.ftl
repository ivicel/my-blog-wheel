<#import "/spring.ftl" as spring/>

<#macro postFragment posts>
<#list posts as post>
    <div class="post-preview">
        <a href="<@spring.url '/post/${post.id}'/>">
            <h2 class="post-title">${post.title}</h2>
            <#if post.subtitle??>
            <h3 class="post-subtitle">
                ${post.subtitle}
            </h3>
            {</#if>
            <div class="post-content-preview">
                <#if post.summary??>
                    ${post.summary}
                <#else>
                    ${post.bodyHtml?replace('<.*?\\/?>|<\\/.*?>', '', 'ri')[0..*300]}
                </#if>
            </div>
        </a>
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
    <hr>
</#list>
</#macro>