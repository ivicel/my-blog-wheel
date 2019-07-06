<template>
    <v-dialog v-model="showMe" max-width="290">
        <v-card>
            <v-card-title class="headline">{{ headline }}</v-card-title>
            <v-card-text>{{ text }}</v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn ref="negative" @click="negative()" color="info" flat
                    >取消</v-btn
                >
                <v-btn @click="positive()" color="red" flat>确认</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    data() {
        return {
            showMe: false
        };
    },
    props: {
        headline: {
            type: String,
            default: "Are you sure?"
        },
        persistent: {
            Type: Boolean,
            default: true
        },
        text: {
            type: String,
            default: "do this will be...."
        }
    },
    methods: {
        show() {
            this.showMe = true;
        },
        dismiss() {
            this.showMe = false;
        },
        negative() {
            this.$emit("negative");
        },
        positive() {
            this.$emit("positive");
        }
    }
};
</script>

<style></style>
