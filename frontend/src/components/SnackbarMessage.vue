<template>
    <v-snackbar
        v-model="active"
        :top="y === 'top'"
        :bottom="y === 'bottom'"
        :left="x === 'left'"
        :right="x === 'right'"
        :color="color"
        :timeout="timeout"
        :multi-line="mode === 'multi-line'"
        :vertical="mode === 'vertical'"
    >
        {{ msg }}
        <v-btn dark flat @click="active = false">关闭</v-btn>
    </v-snackbar>
</template>

<script>
export default {
    props: {
        msg: { type: String, default: "错误" },
        color: { type: String, default: "error" },
        x: { type: String, default: "" },
        y: { type: String, default: "top" },
        timeout: { type: Number, default: 3000 },
        mode: { type: String, default: "vertical" }
    },
    data() {
        return {
            active: false
        };
    },
    watch: {
        active(oldVal, newVal) {
            if (!oldVal && newVal) {
                this.$el.addEventListener("transitionend", this.destroyElement);
            }
        }
    },
    destroyed() {
        document.body.removeChild(this.$el);
    },
    methods: {
        open() {
            this.active = true;
        },
        close() {
            this.active = false;
        },
        destroyElement() {
            this.$el.removeEventListener("transitionend", this.destroyElement);
            this.$destroy();
        }
    }
};
</script>

<style></style>
