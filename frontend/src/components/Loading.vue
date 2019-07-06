<template>
    <div>
        <v-dialog v-model="active" id="v-dialog" width="300" persistent>
            <v-card dark color="primary">
                <v-card-text class="heading">
                    {{ title }}
                    <v-progress-linear
                        color="white"
                        indeterminate
                        class="mb-0"
                    ></v-progress-linear>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            active: false,
            title: "处理中...."
        };
    },
    methods: {
        show(title) {
            this.active = true;
            if (title) {
                this.title = title;
            }
        },
        dismiss() {
            this.active = false;
            this.$destroy();
        }
    },
    watch: {
        // active(newVal, oldVal) {
        //     if (!newVal && oldVal) {
        //         let dialog = document.getElementById("v-dialog");
        //         if (dialog) {
        //             document.body.removeChild(dialog);
        //         }
        //     }
        // }
    },
    created() {},
    destroyed() {
        document.body.removeChild(this.$el);
    }
};
</script>
<style>
</style>
