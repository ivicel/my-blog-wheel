<template>
    <div>
        <textarea></textarea>
    </div>
</template>

<script>
import SimpleMDE from "simplemde";

export default {
    data() {
        return {
            editor: null
        };
    },
    props: {
        value: String
    },
    mounted() {
        this.editor = new SimpleMDE({
            element: this.$el.firstElementChild,
            autoDownloadFontAwesome: false,
            initialValue: this.value,
            spellChecker: false,
            indentWithTabs: false
        });

        this.editor.codemirror.on("change", () => {
            this.$emit("input", this.editor.value());
        });

        const wrapper = this.editor.codemirror.getWrapperElement();
        const preview = document.createElement("div");
        wrapper.nextSibling.className += " my-preview";
        preview.clasName = "editor-preview my-preview";
        wrapper.appendChild(preview);
    },
    methods: {
        renderHtml() {
            return this.editor.markdown(this.editor.value());
        }
    }
};
</script>

<style scoped>
@import "../../node_modules/simplemde/dist/simplemde.min.css";
@import "../../node_modules/@fortawesome/fontawesome-free/css/all.min.css";
@import "../../node_modules/@fortawesome/fontawesome-free/css/v4-shims.min.css";
</style>
