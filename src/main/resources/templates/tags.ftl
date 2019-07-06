<#import "/spring.ftl" as spring/>

<#assign num = 0/>
<#list tags as tag>
    <#assign num = num + tag.associatedPostCount/>
</#list>
<a class="tag-button tag-button--all" msg-encode="<@spring.url '/tags'/>">
    Show All
    <sup>${num}</sup>
</a>


<#list tags as tag>
    <a msg-sort="${tag.associatedPostCount}"
       msg-encode="<@spring.url '/tag/${tag.name}'/>"
       class="tag-button"
       title="${tag.name}" rel="${tag.associatedPostCount}">
        ${tag.name}
        <sup>${tag.associatedPostCount}</sup>
    </a>
</#list>

<script type="text/javascript">
    $('.tag-button').on('click', function(e) {
        e.preventDefault();
        let $this = $(this);
        $.get($this.msg('encode'), function(msg) {
            $('#placeholder').html(msg);
        });
    });
</script>