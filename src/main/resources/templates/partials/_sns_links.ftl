<#import "/spring.ftl" as spring/>

<ul class="list-inline">
    <#if BlogOptions.socials_rss??>
    <li>
        <a href="<@spring.url '/feed.xml'/>">
        <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa fa-rss fa-stack-1x fa-inverse"></i>
      </span>
        </a>
    </li>
    </#if>
    <#if BlogOptions.socials_twitter??>
    <li>
        <a href="<BlogOptions.socials_twitter }}">
      <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa fa-twitter fa-stack-1x fa-inverse"></i>
      </span>
        </a>
    </li>
    </#if>
    <#if BlogOptions.socials_zhihu??>
    <li>
        <a target="_blank" href="{{ BlogOptions.socials_zhihu }}">
      <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa  fa-stack-1x fa-inverse">知</i>
      </span>
        </a>
    </li>
    </#if>
    <#if BlogOptions.socials_weibo??>
    <li>
        <a target="_blank" href="{{ BlogOptions.socials_weibo }}">
      <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa fa-weibo fa-stack-1x fa-inverse"></i>
      </span>
        </a>
    </li>
    </#if>
    <#if BlogOptions.socials_facebook??>
    <li>
        <a target="_blank" href="{{ BlogOptions.socials_facebook }}">
      <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
      </span>
        </a>
    </li>
    </#if>
    <#if BlogOptions.socials_github??>
    <li>
        <a target="_blank" href="{{ BlogOptions.socials_github }}">
      <span class="fa-stack fa-lg">
        <i class="fa fa-circle fa-stack-2x"></i>
        <i class="fa fa-github fa-stack-1x fa-inverse"></i>
      </span>
        </a>
    </li>
    </#if>
</ul>
